package com.automation.bigbasket.utils;

import com.automation.bigbasket.config.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

/** All synchronisation lives here - the framework never uses Thread.sleep(). */
public class WaitUtils {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("explicit.wait")));
        this.wait.ignoring(StaleElementReferenceException.class);
    }

    public WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public List<WebElement> allVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public boolean isVisible(By locator, int seconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(seconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isVisible(By locator) {
        return isVisible(locator, ConfigReader.getInt("short.wait"));
    }

    /** Click with automatic JS fallback when something overlays the element. */
    public void click(By locator) {
        WebElement el = clickable(locator);
        try {
            el.click();
        } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
            jsClick(driver.findElement(locator));
        }
    }

    public void click(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
            jsClick(element);
        }
    }

    public void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    public void type(By locator, String text) {
        WebElement el = visible(locator);
        el.clear();
        el.sendKeys(text);
    }

    public void waitForPageLoad() {
        wait.until(d -> "complete".equals(((JavascriptExecutor) d).executeScript("return document.readyState")));
    }

    /** Generic custom condition. */
    public <T> T until(Function<WebDriver, T> condition) {
        return wait.until(condition);
    }

    /** Like until(), but returns false instead of throwing on timeout. */
    public boolean untilQuietly(Function<WebDriver, Boolean> condition, int seconds) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                    .ignoring(StaleElementReferenceException.class)
                    .until(condition);
        } catch (TimeoutException e) {
            return false;
        }
    }
}
