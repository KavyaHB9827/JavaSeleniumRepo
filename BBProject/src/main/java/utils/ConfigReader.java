package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static {
        properties = new Properties();

        try {
            FileInputStream file = new FileInputStream(
                    "src/test/resources/config.properties");

            properties.load(file);
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    
    
}
