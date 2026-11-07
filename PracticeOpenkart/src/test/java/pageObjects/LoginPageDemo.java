package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPageDemo extends BasePageDemo {

	public LoginPageDemo(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//input[@id='input-email']")
	WebElement txtEmailAddress;
	
	@FindBy(xpath="//input[@value='Login']")
	WebElement txtPassword;
	
	@FindBy(xpath="//input[@value='Login']")
	WebElement btnLogin;

	public void setTxtEmailAddress(String email) {
		txtEmailAddress.sendKeys(email);;
	}

	public void setTxtPassword(String pwd) {
		txtPassword.sendKeys(pwd);;
	}
	
	public void clickLogin() {
		btnLogin.click();
	}

	
	

}
