package com.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public final class Log {

    private static final Logger LOGGER = LogManager.getLogger(Log.class);

    private Log() {
    }

    public static void info(String message) {
        LOGGER.info(message);
    }

    public static void info(String message, WebDriver webDriver) {
        LOGGER.info("[{}] - {}", webDriver, message);
    }

    public static void warn(String message) {
        LOGGER.warn(message);
    }

    public static void error(String message) {
        LOGGER.error(message);
    }

    public static void error(String message, Throwable throwable) {
        LOGGER.error(message, throwable);
    }

    public static void fatal(String message) {
        LOGGER.fatal(message);
    }

    public static void debug(String message) {
        LOGGER.debug(message);
    }
}
