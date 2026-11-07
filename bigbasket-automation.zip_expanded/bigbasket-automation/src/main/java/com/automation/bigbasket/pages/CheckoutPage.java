package com.automation.bigbasket.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Checkout entry -> login / address prompt for unauthenticated users. */
public class CheckoutPage extends BasePage {

    // ---- Locators (calibrate against the live DOM; see README) ----
    private static final By CHECKOUT_BTN = By.xpath(
            "//button[contains(normalize-space(.),'Checkout') or contains(normalize-space(.),'Proceed')]");
    private static final By PHONE_INPUT = By.xpath(
            "//input[@type='tel' or contains(@placeholder,'hone') or contains(@placeholder,'obile')]");
    private static final By CONTINUE_BTN = By.xpath(
            "//button[contains(normalize-space(.),'Continue') or contains(normalize-space(.),'OTP') "
                    + "or contains(normalize-space(.),'Login')]");
    private static final By ERROR_MSG = By.xpath(
            "//*[@role='alert' or contains(@class,'rror')][string-length(normalize-space(.))>0]");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void proceedToCheckout() {
        log.info("Clicking checkout");
        wait.click(CHECKOUT_BTN);
    }

    public boolean isLoginPromptDisplayed() {
        return wait.isVisible(PHONE_INPUT, 10);
    }

    /** Submits the login form with the mandatory phone field empty and returns the validation message. */
    public String submitEmptyAndGetError() {
        wait.visible(PHONE_INPUT);
        wait.click(CONTINUE_BTN);
        String msg = wait.visible(ERROR_MSG).getText().trim();
        log.info("Validation message shown: '{}'", msg);
        return msg;
    }
}
