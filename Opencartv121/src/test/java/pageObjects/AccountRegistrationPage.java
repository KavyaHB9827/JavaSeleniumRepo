package pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountRegistrationPage extends BasePage {

	public AccountRegistrationPage(WebDriver driver) {
		super(driver);
		
	}
	
	@FindBy(xpath="//input[@name='firstname']")
	WebElement txtFirstName;
	
	@FindBy(xpath="//input[@name='lastname']")
	WebElement txtLastName;
	 	
	@FindBy(xpath="//input[@type='email']")
	WebElement txtEmail;
	
	@FindBy(xpath="//input[@id='input-telephone']")
	WebElement txtTelephone;
	
	@FindBy(xpath="//input[@id='input-password']")
	WebElement txtPassword;
	
	@FindBy(xpath="//input[@id='input-confirm']")
	WebElement txtPasswordConfirm;
	
	@FindBy(xpath="//input[@value='0']")
	WebElement radioButtonNo;
	
	

	@FindBy(xpath="//input[@name='agree']")
	WebElement chckdPolicy;
	
	  @FindBy(xpath="//input[@type='submit']")
	  WebElement btnContinue;
	
	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement msgConfirmation;//note 

	public void setTxtFirstName(String fname) {
	 txtFirstName.sendKeys(fname);
	 }

	public void setTxtLastName(String lname) {
		 txtLastName.sendKeys(lname);
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

	public void setTxtPasswordConfirm(String pwd) {
		 txtPasswordConfirm.sendKeys(pwd);
	}

	public void setRadioButtonNo() {
		 radioButtonNo.click();
	}

	public void setChckdPolicy() {
		chckdPolicy.click();;
	}

	public void setBtnContinue() {


		 btnContinue.click();
	} 
		 //sol2
		// btnContinue.submit();
		 
		 //sol3
       //  Actions act=new Actions(driver);	
        // act.moveToElement(btnContinue).click().perform();
         
        //sol4
        // JavascriptExecutor js=(JavascriptEscecutor)driver;
        //  js.executescript("arguements[0].click().btnContinue");
         
         //sol5
       // WebDriverWait mywait= new WebDriverWait(driver,Duration.ofSeconds(10));
       // mywait.until(ExpectedConditions.elementToBeClickable(btnContinue));
		 
		 
		 
		 
		 public String getMsgConfirmation() {
			    return msgConfirmation.getText();//chat gpt 

		 }

	}

	/*public String getMsgConfirmation() {
	try {
		return (msgConfirmation.getText());
	}catch(Exception e) {
		return(e.getMessage());
	}*/
	

	
	
	


	




 
