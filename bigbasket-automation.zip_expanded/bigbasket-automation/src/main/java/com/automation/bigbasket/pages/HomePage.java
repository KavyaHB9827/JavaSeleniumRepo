package com.automation.bigbasket.pages;

import com.automation.bigbasket.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    private static final By LOCATION_TRIGGER = By.xpath("//button[contains(normalize-space(.),'Select Location')]");
    private static final By LOCATION_INPUT = By.xpath(
            "//input[contains(@placeholder,'area') or contains(@placeholder,'pincode') or contains(@placeholder,'location')]");
    private static final By LOCATION_FIRST_SUGGESTION =
            By.xpath("(//*[@role='dialog' or contains(@class,'odal')]//li)[1]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        String url = ConfigReader.get("base.url");
        log.info("Launching {}", url);
        driver.get(url);
        wait.waitForPageLoad();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    /** BigBasket asks for a delivery location before showing prices - handle it if it appears. */
    public void selectLocation(String pinOrArea) {
        if (!wait.isVisible(LOCATION_INPUT, 4) && wait.isVisible(LOCATION_TRIGGER, 4)) {
            wait.click(LOCATION_TRIGGER);
        }
        if (wait.isVisible(LOCATION_INPUT, 6)) {
            log.info("Setting delivery location '{}'", pinOrArea);
            WebElement input = driver.findElement(LOCATION_INPUT);
            input.clear();
            input.sendKeys(pinOrArea);
            wait.click(LOCATION_FIRST_SUGGESTION);
            // modal should close once a location is chosen
            wait.untilQuietly(d -> d.findElements(LOCATION_INPUT).stream().noneMatch(WebElement::isDisplayed),
                    ConfigReader.getInt("explicit.wait"));
        } else {
            log.info("Location prompt not shown - continuing");
        }
    }
}
