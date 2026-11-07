package tests;



import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.BigBasketHomePage;
import utils.DriverFactory;

public class BigBasketTest extends BaseTest {

    @Test
    public void launchBigBasket() {

        BigBasketHomePage homePage =
                new BigBasketHomePage(DriverFactory.getDriver());

        String title = homePage.getPageTitle();

        System.out.println("BigBasket Page Title: " + title);

        
    }
    @Test
    public void searchMilk() {

        BigBasketHomePage homePage =new BigBasketHomePage(DriverFactory.getDriver());
                

      
    }
}