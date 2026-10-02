package com.automation.core;

import com.automation.utils.Config;
import com.automation.utils.Log;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.logging.Level;

public final class WebDriverManager {

    private WebDriverManager() {
    }

    public static WebDriver createDriverInstance() {
        String browser = Config.get("browser");
        Log.info("Creating WebDriver. browser=" + browser + ", host=" + Config.get("host"));
        WebDriver webDriver = "grid".equalsIgnoreCase(Config.get("host"))
                ? createRemoteWebDriver(browser)
                : createLocalWebDriver(browser);
        webDriver.manage().timeouts().implicitlyWait(Duration.ZERO);
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Config.getInt("timeout.pageLoad")));
        try {
            webDriver.manage().window().maximize();
        } catch (Exception e) {
            Log.warn("Could not maximize the browser window: " + e.getMessage());
        }
        Log.info("Created WebDriver", webDriver);
        return webDriver;
    }

    private static WebDriver createLocalWebDriver(String browser) {
        if ("firefox".equalsIgnoreCase(browser)) {
            return new FirefoxDriver(firefoxOptions());
        }
        return new ChromeDriver(chromeOptions());
    }

    private static WebDriver createRemoteWebDriver(String browser) {
        try {
            if ("firefox".equalsIgnoreCase(browser)) {
                return new RemoteWebDriver(hubUrl(), firefoxOptions());
            }
            return new RemoteWebDriver(hubUrl(), chromeOptions());
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid hub.url: " + Config.get("hub.url"), e);
        }
    }

    private static ChromeOptions chromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        if (Config.getBoolean("headless")) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1920,1080", "--disable-dev-shm-usage", "--remote-allow-origins=*");
        applyBrowserVersion(options);
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.SEVERE);
        options.setCapability(ChromeOptions.LOGGING_PREFS, logs);
        return options;
    }

    private static FirefoxOptions firefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        if (Config.getBoolean("headless")) {
            options.addArguments("-headless");
        }
        applyBrowserVersion(options);
        return options;
    }

    private static void applyBrowserVersion(ChromeOptions options) {
        String version = Config.get("browser.version");
        if (!version.isEmpty()) {
            options.setBrowserVersion(version);
        }
    }

    private static void applyBrowserVersion(FirefoxOptions options) {
        String version = Config.get("browser.version");
        if (!version.isEmpty()) {
            options.setBrowserVersion(version);
        }
    }

    private static URL hubUrl() throws MalformedURLException {
        String configured = Config.get("hub.url");
        if (configured.isEmpty()) {
            throw new IllegalStateException("host=grid requires hub.url or -DhubUrl");
        }
        if (configured.startsWith("http://") || configured.startsWith("https://")) {
            return URI.create(configured).toURL();
        }
        return URI.create("http://" + configured + ":4444/wd/hub").toURL();
    }
}
