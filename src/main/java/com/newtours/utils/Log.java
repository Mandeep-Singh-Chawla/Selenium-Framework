package com.newtours.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class Log {
    //Initialize Log4j instance
    private static final Logger Log =  LogManager.getLogger(Log.class);
    //Info Level Logs
    public static void info (String message) {
        Log.info(message);
    }
    public static void info (String message, WebDriver webDriver) {
        Log.info("[" + webDriver + "] - " +message);
    }
    //Warn Level Logs
    public static void warn (String message) {
        Log.warn(Thread.currentThread().getId()+" - "+message);
    }
    //Error Level Logs
    public static void error (String message) {
        Log.error(Thread.currentThread().getId()+" - "+message);
    }
    //Fatal Level Logs
    public static void fatal (String message) {
        Log.fatal(Thread.currentThread().getId()+" - "+message);
    }
    //Debug Level Logs
    public static void debug (String message) {
        Log.debug(Thread.currentThread().getId()+" - "+message);
    }
}
