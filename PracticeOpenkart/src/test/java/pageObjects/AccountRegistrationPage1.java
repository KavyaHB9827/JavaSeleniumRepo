package pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountRegistrationPage1 extends BasePage1 {

	public AccountRegistrationPage1(WebDriver driver) {
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
	
	//sol2
	//chckdPolicy.submit();
	
	//sol3
	//Actions act=new Actions(driver);
	//act.moveToElement(btnContinue).click().perform();
	
	//sol4
	//JavascriptExecutor js=(JavascriptExecutor)driver;
	//js.executEscript("arguements[0].click().btnContinue")
	
	//sol5
	//WebDriverWait mywait=new WebDriverWait(driver,Duration.ofSeconds(20));
	// mywait.until(ExpectedConditions.elementToBeClickable(btnContinue));
   
	

	
	//must not do any validation should do in test case class only msg confirmation can be done
	public String getMsgConfirmation() {
	    return msgConfirmation.getText();//chat gpt condider this
	}
	
	}




/*public String getMsgConfirmation() {
try {
return (msgConfirmation.getText());
} catch(Exception e) {
return(e.getMessage());
}*/