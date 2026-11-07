package com.automation.bigbasket.utils;

import com.automation.bigbasket.config.ConfigReader;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ScreenshotUtils {

    private ScreenshotUtils() { }

    public static String asBase64(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }

    /** Saves a PNG under target/screenshots and returns its path. */
    public static String saveToDisk(WebDriver driver, String name) throws IOException {
        byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Path dir = Paths.get(ConfigReader.get("screenshot.dir"));
        Files.createDirectories(dir);
        String ts = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        Path file = dir.resolve(name + "_" + ts + ".png");
        Files.write(file, bytes);
        return file.toString();
    }
}
