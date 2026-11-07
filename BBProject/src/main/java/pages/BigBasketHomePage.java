package pages;



import org.openqa.selenium.WebDriver;

public class BigBasketHomePage {

    private WebDriver driver;

    // Constructor
    public BigBasketHomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Get page title
    public String getPageTitle() {
        return driver.getTitle();
    }

    // Get current URL
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
        
    }
 // Search box locator
   // private By searchBox =
   //         By.xpath("//input[@placeholder='Search for Products...']");


   

    
    
}

