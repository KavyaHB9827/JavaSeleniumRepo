package com.automation.bigbasket.pages;

import com.automation.bigbasket.components.HeaderComponent;
import com.automation.bigbasket.models.BasketItem;
import com.automation.bigbasket.utils.TextUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class BasketPage extends BasePage {

    // ---- Locators (calibrate against the live DOM; see README) ----
    private static final By BASKET_CONTAINER =
            By.xpath("//*[@role='dialog' or contains(@class,'asket') or contains(@class,'art')]");
    private static final By ROWS = By.xpath(
            "//*[@role='dialog' or contains(@class,'asket') or contains(@class,'art')]"
                    + "//li[.//a[contains(@href,'/pd/')] and .//button[normalize-space()='+']]");
    private static final By ROW_NAME = By.xpath("(.//h3 | .//a[contains(@href,'/pd/')])[1]");
    private static final By ROW_QTY = By.xpath(".//button[normalize-space()='+']/preceding-sibling::*[1]");
    private static final By ROW_UNIT_PRICE = By.xpath("(.//*[not(*) and contains(text(),'" + RUPEE + "')])[1]");
    private static final By SUB_TOTAL = By.xpath(
            "(//*[contains(normalize-space(text()),'Sub Total') or contains(normalize-space(text()),'Subtotal')]"
                    + "/following::*[not(*) and contains(text(),'" + RUPEE + "')])[1]");

    public BasketPage(WebDriver driver) {
        super(driver);
    }

    public BasketPage open() {
        new HeaderComponent(driver).openBasket();
        wait.visible(ROWS);
        return this;
    }

    public List<BasketItem> getItems() {
        List<BasketItem> items = new ArrayList<>();
        for (WebElement row : driver.findElements(ROWS)) {
            String name = row.findElement(ROW_NAME).getText().trim();
            int qty = TextUtils.parseInt(row.findElement(ROW_QTY).getText());
            double unit = TextUtils.parsePrice(row.findElement(ROW_UNIT_PRICE).getText());
            items.add(new BasketItem(name, qty, unit));
        }
        log.info("Basket rows: {}", items);
        return items;
    }

    /** Total amount (item subtotal) displayed in the basket. */
    public double getDisplayedTotal() {
        return TextUtils.parsePrice(wait.visible(SUB_TOTAL).getText());
    }

    /** Waits until the basket shows the expected number of rows. */
    public void waitForItemCount(int expected) {
        wait.until(d -> d.findElements(ROWS).size() == expected);
    }
}
