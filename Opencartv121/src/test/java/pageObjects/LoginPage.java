package pageObjects;
//Maintain separate page for login with valid credentials 

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

	public LoginPage(WebDriver driver) {
		super(driver);
	}	
	@FindBy(xpath="//input[@id='input-email']")
	WebElement txtEmailAddress;
	
	@FindBy(xpath="//input[@id='input-password']")
	WebElement txtPassword;
	
	@FindBy(xpath="//input[@type='submit' and @value='Login']")
	WebElement btnLogin;

	public void setTxtEmailAddress(String email) {
		txtEmailAddress.sendKeys(email);
	}

	public void setTxtPassword(String password) {
		txtPassword.sendKeys(password);
	}

	public void btnLogin() {
		 System.out.println("Login button displayed: " + btnLogin.isDisplayed());
		  System.out.println("Login button enabled: " + btnLogin.isEnabled());
		  
		btnLogin.click();
		 System.out.println("Login button clicked successfully");
		}
	}
	
	
	
	

