package com.automation.bigbasket.pages;

import com.automation.bigbasket.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

/** Parent of every page / component. Holds the driver, wait helper and logger. */
public abstract class BasePage {

    /** Rupee sign, escaped so the source file is encoding-safe. */
    protected static final String RUPEE = "\u20B9";

    protected final WebDriver driver;
    protected final WaitUtils wait;
    protected final Logger log = LogManager.getLogger(getClass());

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }
}
