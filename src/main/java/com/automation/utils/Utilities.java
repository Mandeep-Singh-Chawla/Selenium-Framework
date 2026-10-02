package com.automation.utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;

import java.io.ByteArrayInputStream;
import java.util.Date;

public final class Utilities {

    private Utilities() {
    }

    public static void addAttachment(String name, WebDriver driver) {
        if (driver == null) {
            return;
        }
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), "png");
        } catch (Exception e) {
            Log.warn("Could not capture screenshot '" + name + "': " + e.getMessage());
        }
    }

    public static void captureBrowserLogs(String logType, WebDriver driver) {
        if (driver == null || !supportsBrowserLogs(driver)) {
            return;
        }
        try {
            LogEntries logEntries = driver.manage().logs().get(logType);
            StringBuilder logs = new StringBuilder();
            for (LogEntry entry : logEntries) {
                logs.append(new Date(entry.getTimestamp()))
                        .append(' ')
                        .append(entry.getLevel())
                        .append(' ')
                        .append(entry.getMessage())
                        .append(System.lineSeparator());
            }
            if (logs.length() > 0) {
                Allure.addAttachment(logType + " logs", logs.toString());
            }
        } catch (Exception e) {
            Log.warn("Could not capture " + logType + " logs: " + e.getMessage());
        }
    }

    private static boolean supportsBrowserLogs(WebDriver driver) {
        String browser = ((HasCapabilities) driver).getCapabilities().getBrowserName();
        return "chrome".equalsIgnoreCase(browser) || "msedge".equalsIgnoreCase(browser);
    }
}
