package com.bigbasket.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(xpath = "//input[@placeholder='Search for Products...']")
    private WebElement searchTextField;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public WebElement getSearchTextField() {
        return searchTextField;
    }
}