package com.newtours.tests;

import com.newtours.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import static com.newtours.utils.Utilities.addAttachment;

public abstract class BaseTest {

    private ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();

    @BeforeMethod
    public void createDriver(ITestContext context){
        Thread.currentThread().setName(context.getCurrentXmlTest().getName());
        webDriverThreadLocal.set(WebDriverManager.createDriverInstance());
    }

    public WebDriver getWebDriver(){
        return webDriverThreadLocal.get();
    }

    @AfterMethod
    public void quitDriver() {
        addAttachment("ScreenShot before Quit", webDriverThreadLocal.get());
        webDriverThreadLocal.get().quit();
    }

}
