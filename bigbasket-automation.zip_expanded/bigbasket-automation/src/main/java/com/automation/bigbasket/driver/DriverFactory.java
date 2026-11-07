package com.automation.bigbasket.driver;

import com.automation.bigbasket.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/** Creates and holds a WebDriver per thread. Selenium Manager (built into Selenium 4.6+) fetches the driver binary. */
public final class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() { }

    public static WebDriver initDriver() {
        String browser = ConfigReader.get("browser").toLowerCase();
        boolean headless = ConfigReader.getBoolean("headless");
        WebDriver driver;

        switch (browser) {
            case "firefox":
                FirefoxOptions ff = new FirefoxOptions();
                if (headless) ff.addArguments("-headless");
                driver = new FirefoxDriver(ff);
                break;
            case "edge":
                EdgeOptions edge = new EdgeOptions();
                if (headless) edge.addArguments("--headless=new");
                driver = new EdgeDriver(edge);
                break;
            case "chrome":
            default:
                ChromeOptions chrome = new ChromeOptions();
                chrome.addArguments("--disable-notifications", "--disable-popup-blocking",
                        "--remote-allow-origins=*", "--disable-blink-features=AutomationControlled");
                if (headless) chrome.addArguments("--headless=new", "--window-size=1920,1080");
                driver = new ChromeDriver(chrome);
        }

        // Implicit wait deliberately left at 0 - only explicit waits are used (no mixing).
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getInt("page.load.timeout")));
        if (!headless) driver.manage().window().maximize();

        DRIVER.set(driver);
        return driver;
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void quitDriver() {
        WebDriver d = DRIVER.get();
        if (d != null) {
            d.quit();
            DRIVER.remove();
        }
    }
}
