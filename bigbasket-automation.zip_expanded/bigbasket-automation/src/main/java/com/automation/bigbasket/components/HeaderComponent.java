package com.automation.bigbasket.components;

import com.automation.bigbasket.pages.BasePage;
import com.automation.bigbasket.utils.TextUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/** Header shown on every page: search box + mini basket. */
public class HeaderComponent extends BasePage {

    // NOTE: verify these in DevTools - BigBasket is a React app and class names change often.
    private static final By SEARCH_INPUT = By.xpath("(//input[contains(@placeholder,'Search for')])[1]");
    private static final By BASKET_BUTTON = By.xpath("//button[contains(normalize-space(.),'My Basket')]");
    private static final By BASKET_AMOUNT =
            By.xpath("//button[contains(normalize-space(.),'My Basket')]//*[not(*) and contains(text(),'" + RUPEE + "')]");

    public HeaderComponent(WebDriver driver) {
        super(driver);
    }

    public void search(String term) {
        log.info("Searching for '{}'", term);
        WebElement box = wait.clickable(SEARCH_INPUT);
        box.click();
        box.clear();
        box.sendKeys(term);
        box.sendKeys(Keys.ENTER);
    }

    /** Amount shown on the header basket button (0 when empty / not shown). */
    public double getBasketAmount() {
        if (driver.findElements(BASKET_AMOUNT).isEmpty()) return 0;
        return TextUtils.parsePrice(driver.findElement(BASKET_AMOUNT).getText());
    }

    /** Waits until the header basket amount equals expected (within tolerance). Returns the last value read. */
    public double waitForBasketAmount(double expected, double tolerance, int seconds) {
        wait.untilQuietly(d -> Math.abs(getBasketAmount() - expected) <= tolerance, seconds);
        return getBasketAmount();
    }

    public void openBasket() {
        log.info("Opening basket");
        wait.click(BASKET_BUTTON);
    }
}
