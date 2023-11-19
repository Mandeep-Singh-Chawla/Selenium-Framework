package com.newtours.utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

public class Utilities {

    public static void addAttachment(String name, WebDriver driver) {
        long id = Thread.currentThread().getId();
        Allure.addAttachment("Thread ID [" + id + "] - ["+ driver + "]" + name, new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    public static void captureBrowserLogs(String logType, WebDriver driver) {
        LogEntries logEntries = driver.manage().logs().get(logType);
        StringBuilder logs = new StringBuilder();

        for (org.openqa.selenium.logging.LogEntry entry : logEntries) {
            logs.append(new Date(entry.getTimestamp()) + " "
                    + entry.getLevel() + " " + entry.getMessage());
            logs.append(System.lineSeparator());
        }
        Allure.addAttachment(logType + " Logs :", String.valueOf(logs));
    }

    public static void captureWebDriverLogs() throws FileNotFoundException {
        Allure.addAttachment( "WebDriver Logs :", new FileInputStream(".src/main/resources/chromedriver.log"));
    }

    
}
