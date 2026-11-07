package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import testBase.BaseClassDemo;

public class AccountRegistrationPageDemo extends BasePageDemo {
	public AccountRegistrationPageDemo(WebDriver driver)
	{
		super(driver);
	}
	
	
	@FindBy(xpath="//input[@id='input-firstname']")
	WebElement txtFirstName;
	
	@FindBy(xpath="//input[@id='input-lastname']")
	WebElement txtLastName;
	
	@FindBy(xpath="//input[@id='input-email']")
	WebElement txtEmail;
	
	@FindBy(xpath="//input[@id='input-telephone']")
	WebElement txtTelephone;
	
	@FindBy(xpath="//input[@id='input-password']")
	WebElement txtPassword;
	
	@FindBy(xpath="//input[@id='input-confirm']")
	WebElement txtPasswordconfirm;
	 
	@FindBy(xpath="//input[@value='0']")
	WebElement radioButtonNo;
	 
	@FindBy(xpath="//input[@name='agree']")
	WebElement chckdPolicy;
	
	@FindBy(xpath="//input[@type='submit']")
	WebElement btncontinue;
	
	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement msgConfirmation;

	public void setTxtFirstName(String fname) {
		txtFirstName.sendKeys(fname);
	}

	public void setTxtLastName(String lname) {
		txtLastName.sendKeys(lname);;
	}

	public void setTxtEmail(String email) {
		txtEmail.sendKeys(email);
	}

	public void setTxtTelephone(String tel) {
		txtTelephone.sendKeys(tel);
	}

	public void setTxtPassword(String pwd) {
		txtPassword.sendKeys(pwd); 
	}

	public void setTxtPasswordconfirm(String pwd) {
		txtPasswordconfirm.sendKeys(pwd);;
	}

	/*public void setRadioButtonNo() {

        radioButtonNo.click();
	}*/

	public void setChckdPolicy() {
		chckdPolicy.click();
	}
	public void setBtncontinue() {
		 btncontinue.click();
	}
	public String msgConfirmation() {
		return msgConfirmation.getText();
	}



}
