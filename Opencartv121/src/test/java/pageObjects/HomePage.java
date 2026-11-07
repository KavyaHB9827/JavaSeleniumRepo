package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
		
	}
	
	@FindBy(xpath="//span[text()='My Account']")
	WebElement lnkMyaccount;
	
	public WebElement getLnkLogin() {
		return lnkLogin;
	}

	public void setLnkLogin(WebElement lnkLogin) {
		this.lnkLogin = lnkLogin;
	}

	@FindBy(xpath="//a[text()='Register']")
	WebElement lnkRegister;
	
	@FindBy(linkText="Login")//Login link added in step no 5
	WebElement lnkLogin;

	
	public void clickMyAccount() {
		lnkMyaccount.click();
	}
	
	public void clickRegister() {
		lnkRegister.click();
	}
	public void clickLogin() {
		lnkLogin.click();
	}
	
}
