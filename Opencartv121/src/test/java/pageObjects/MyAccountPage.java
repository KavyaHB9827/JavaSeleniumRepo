package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {

	public MyAccountPage(WebDriver driver) {
		super(driver);	
}
	@FindBy(xpath="//h2[text()='My Account']")//MyAccountPage Heading left side
	WebElement msgHeading;
	
	@FindBy(xpath="//div[@class='list-group']//a[text()='Logout']")//added in step no 6 and there 2 logout buttons so include div tag while writing xpat
	WebElement lnkLogout;
	
	//No validation must be performed in POM Class boolean methods can be used......
	public boolean isMyAccountPageExists() {
	    try {
	        return msgHeading.isDisplayed();
	    }
	    catch (Exception e) {
	        return false;
	    }
	}
	public void clickLogout() {
		lnkLogout.click();
	}
}