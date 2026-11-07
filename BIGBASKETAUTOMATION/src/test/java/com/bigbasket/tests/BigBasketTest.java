package com.bigbasket.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.bigbasket.generic.BaseClass;
import com.bigbasket.generic.ListenerImplementation;
import java.io.IOException;

import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import com.bigbasket.pom.HomePage;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Listeners(ListenerImplementation.class)
public class BigBasketTest extends BaseClass {

    @Test
    public void launchBigBasketTest() {
        String title = driver.getTitle();
        String url = driver.getCurrentUrl();

        Assert.assertTrue(title.toLowerCase().contains("bigbasket"), "Title does not contain BigBasket");
        Assert.assertTrue(url.contains("bigbasket"), "URL is not BigBasket");
        test.log(Status.PASS, "BigBasket launched. Title: " + title);
    }
    
    @Test
    public void searchProductTest() {

        HomePage hp = new HomePage(driver);

        String PRODUCT = "Milk";

        wait.until(ExpectedConditions.visibilityOf(hp.getSearchTextField()));

        hp.getSearchTextField().sendKeys(PRODUCT, Keys.ENTER);

        test.log(Status.PASS, "Searched for product: " + PRODUCT);
    }
    }
