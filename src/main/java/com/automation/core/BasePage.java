package com.automation.core;

import com.automation.utils.Config;
import com.automation.utils.Log;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected final WebDriver webDriver;
    private final WebDriverWait wait;

    protected BasePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(Config.getInt("timeout.explicit")));
    }

    @Step("Open URL")
    public void goTo(String url) {
        Log.info("Opening URL " + url, webDriver);
        webDriver.get(url);
    }

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void type(By locator, String value) {
        WebElement element = visible(locator);
        element.clear();
        element.sendKeys(value);
    }

    protected void click(By locator) {
        clickable(locator).click();
    }

    protected String text(By locator) {
        return visible(locator).getText().trim();
    }

    protected boolean isVisible(By locator, int seconds) {
        try {
            new WebDriverWait(webDriver, Duration.ofSeconds(seconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
