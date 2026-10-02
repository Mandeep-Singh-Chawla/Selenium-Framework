package com.automation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {

    private static final Properties PROPERTIES = new Properties();

    static {
        load("config/config.properties", true);
        load("config/" + environment() + ".properties", false);
    }

    private Config() {
    }

    public static String environment() {
        String env = System.getProperty("env");
        if (env == null || env.trim().isEmpty()) {
            return "qa";
        }
        return env.trim();
    }

    public static String get(String key) {
        String override = systemProperty(key);
        if (override != null && !override.trim().isEmpty()) {
            return override.trim();
        }
        String value = PROPERTIES.getProperty(key);
        return value == null ? "" : value.trim();
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    private static void load(String classpathFile, boolean required) {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream(classpathFile)) {
            if (input == null) {
                if (required) {
                    throw new IllegalStateException(classpathFile + " was not found on the classpath");
                }
                return;
            }
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load " + classpathFile, e);
        }
    }

    private static String systemProperty(String key) {
        String direct = System.getProperty(key);
        if (direct != null) {
            return direct;
        }
        if ("hub.url".equals(key)) {
            return System.getProperty("hubUrl");
        }
        if ("browser.version".equals(key)) {
            return System.getProperty("browserVersion");
        }
        return null;
    }
}
