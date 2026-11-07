package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage1 extends BasePage1 {

	public MyAccountPage1(WebDriver driver) {
		super(driver);
		
	}
	
	@FindBy(xpath="//h2[text()='My Account']")//My Account page heading 
	WebElement msgHeading;
	
	@FindBy(xpath="//div[@class='list-group']//a[text()='Logout']")//added in step no 6 and there 2 logout buttons so include div tag while writing xpath
	WebElement lnkLogout;


public boolean isMyAccountPageExists()
{
	
	try {
	return (msgHeading.isDisplayed()); 
	}
	catch(Exception e) {
		return false;
	}
}

public void clickLogout() {
	lnkLogout.click();
	
}
}

