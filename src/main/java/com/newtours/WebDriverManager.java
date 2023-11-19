package com.newtours;

import com.newtours.utils.Log;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class WebDriverManager {
    static String webDriverBasePath= "src/main/resources/";
    public static WebDriver createDriverInstance() {
        long id = Thread.currentThread().getId();
        Log.info("CREATING WEBDRIVER INSTANCE..");
        WebDriver webDriver;
        if (System.getProperty("host") != null && System.getProperty("host").equals("grid")) {
            try {
                webDriver = createRemoteWebDriver();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }

        else {
            webDriver = createLocalWebDriver();
        }
        Log.info("Created Webdriver instance ", webDriver);
        return webDriver;
    }

    private static WebDriver createLocalWebDriver(){
        WebDriver localWebDriver;
        if (System.getProperty("browser") != null && System.getProperty("browser").equals("chrome")){
            System.setProperty("webdriver.chrome.driver", webDriverBasePath + "chromedriver_119");
            System.setProperty("webdriver.chrome.logfile", webDriverBasePath + "chromedriver.log");
            System.setProperty("webdriver.chrome.verboseLogging", "true");
            ChromeOptions options = new ChromeOptions();
           options.addArguments("--headless");

            options.addArguments("--remote-allow-origins=*");
            localWebDriver = new ChromeDriver(options);
        }else if (System.getProperty("browser") != null && System.getProperty("browser").equals("firefox")){
            System.setProperty("webdriver.gecko.driver", webDriverBasePath + "geckodriver");
            localWebDriver = new FirefoxDriver();
        } else {
            System.setProperty("webdriver.chrome.driver", webDriverBasePath + "chromedriver");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            localWebDriver = new ChromeDriver(options);
        }
        return localWebDriver;
    }
    private static WebDriver createRemoteWebDriver() throws MalformedURLException {
        FirefoxOptions firefoxOptions;
        ChromeOptions chromeOptions;
        WebDriver remoteWebDriver;

        String url = "http://"+System.getProperty("hubUrl")+":4444/wd/hub";

        if (System.getProperty("browser") != null && System.getProperty("browser").equals("chrome")){
            chromeOptions = new ChromeOptions();
            chromeOptions.setBrowserVersion(System.getProperty("browserVersion"));
            remoteWebDriver = new RemoteWebDriver(URI.create(url).toURL(),chromeOptions);
        } else if (System.getProperty("browser") != null && System.getProperty("browser").equals("firefox")){
            firefoxOptions = new FirefoxOptions();
            firefoxOptions.setBrowserVersion(System.getProperty("browserVersion"));
            remoteWebDriver = new RemoteWebDriver(URI.create(url).toURL(),firefoxOptions);
        } else {
            chromeOptions = new ChromeOptions();
            remoteWebDriver = new RemoteWebDriver(URI.create(url).toURL(),chromeOptions);
        }

        return remoteWebDriver;
    }
}
