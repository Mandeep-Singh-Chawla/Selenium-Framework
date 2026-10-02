package com.automation.tests;

import com.automation.core.WebDriverManager;
import com.automation.utils.Config;
import com.automation.utils.Log;
import com.automation.utils.Utilities;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class BaseTest {

    private static final AtomicBoolean SUITE_STARTED = new AtomicBoolean(false);
    private static final AtomicBoolean SUITE_FINISHED = new AtomicBoolean(false);
    private static final Set<String> STARTED_TESTS = ConcurrentHashMap.newKeySet();
    private static final Set<String> FINISHED_TESTS = ConcurrentHashMap.newKeySet();

    private final ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        if (!SUITE_STARTED.compareAndSet(false, true)) {
            return;
        }
        validateRuntimeConfig();
        Log.info("Suite started | env=" + Config.environment()
                + ", browser=" + Config.get("browser")
                + ", host=" + Config.get("host")
                + ", hub=" + Config.get("hub.url")
                + ", headless=" + Config.get("headless"));
    }

    @BeforeTest(alwaysRun = true)
    public void beforeTest(ITestContext context) {
        if (STARTED_TESTS.add(context.getName())) {
            Log.info("Test started: " + context.getName());
        }
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        Log.info("Class started: " + getClass().getName());
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        Thread.currentThread().setName(method.getName());
        Log.info("Method started: " + method.getName());
        webDriverThreadLocal.set(WebDriverManager.createDriverInstance());
    }

    public WebDriver getWebDriver() {
        return webDriverThreadLocal.get();
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                Utilities.addAttachment("Failure - " + result.getName(), getWebDriver());
                Utilities.captureBrowserLogs("browser", getWebDriver());
            }
            Log.info("Method finished: " + result.getName() + " [" + statusName(result.getStatus()) + "]");
        } finally {
            WebDriver webDriver = webDriverThreadLocal.get();
            if (webDriver != null) {
                try {
                    webDriver.quit();
                } finally {
                    webDriverThreadLocal.remove();
                }
            }
        }
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        Log.info("Class finished: " + getClass().getName());
    }

    @AfterTest(alwaysRun = true)
    public void afterTest(ITestContext context) {
        if (FINISHED_TESTS.add(context.getName())) {
            Log.info("Test finished: " + context.getName()
                    + " | passed=" + context.getPassedTests().size()
                    + ", failed=" + context.getFailedTests().size()
                    + ", skipped=" + context.getSkippedTests().size());
        }
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        if (!SUITE_FINISHED.compareAndSet(false, true)) {
            return;
        }
        Log.info("Suite finished");
    }

    private static void validateRuntimeConfig() {
        String browser = Config.get("browser");
        if (!"chrome".equalsIgnoreCase(browser) && !"firefox".equalsIgnoreCase(browser)) {
            throw new IllegalStateException("Unsupported browser: " + browser);
        }
        if ("grid".equalsIgnoreCase(Config.get("host")) && Config.get("hub.url").isEmpty()) {
            throw new IllegalStateException("host=grid requires hub.url");
        }
    }

    private static String statusName(int status) {
        switch (status) {
            case ITestResult.SUCCESS:
                return "PASSED";
            case ITestResult.FAILURE:
                return "FAILED";
            case ITestResult.SKIP:
                return "SKIPPED";
            default:
                return "UNKNOWN";
        }
    }
}
