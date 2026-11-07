package com.automation.bigbasket.config;

import java.io.InputStream;
import java.util.Properties;

/** Reads config.properties. Any value can be overridden with -Dkey=value on the Maven command line. */
public final class ConfigReader {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is == null) {
                throw new IllegalStateException("config.properties not found on classpath");
            }
            PROPS.load(is);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private ConfigReader() { }

    public static String get(String key) {
        String override = System.getProperty(key);
        return override != null ? override : PROPS.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key).trim());
    }

    public static double getDouble(String key) {
        return Double.parseDouble(get(key).trim());
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key).trim());
    }
}
