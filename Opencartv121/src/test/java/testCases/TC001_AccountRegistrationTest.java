package testCases;
import java.time.Duration;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;


public class TC001_AccountRegistrationTest extends BaseClass {
	@Test(groups= {"Regression","Master"})
	public void verify_account_registration() {
		
		logger.info("****** Starting TC001_AccountRegistrationTest****** ");
		try {
		
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		logger.info("**** Clicked on Myaccount link****");
		
		hp.clickRegister();
		logger.info("**** Clicked on Register link****");
		
		AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
		logger.info("**** Providing customer details****");
		
		
		regpage.setTxtFirstName(randomeString().toUpperCase());
		regpage.setTxtLastName(randomeString().toUpperCase());
		regpage.setTxtEmail(randomeString()+"@gmail.com");//randomly generated email
		regpage.setTxtTelephone(randomPhoneNumber());
		
		
		String password = randomeAlphaNumeric();//same password must confirm so store in variable and use when required multiple times
		
		
		regpage.setTxtPassword(password);
		regpage.setTxtPasswordConfirm(password);
		
		regpage.setChckdPolicy(); 
		//regpage.setRadioButtonNo();//pavan didnt include as it is checked already
		regpage.setBtnContinue();
		
		
		logger.info("**** Validatimg expected message****");
		String confmsg = regpage.getMsgConfirmation();
		if(confmsg.equals("Your Account Has Been Created!")) {
			Assert.assertTrue(true);
		}
		
		else{
			logger.error(".....Test failed....");
			logger.debug(".....Debug logs.....");
			Assert.assertTrue(false);
		} 	
		
		
		}catch(Exception e) {
		    e.printStackTrace();
		    Assert.fail("Registration failed: " + e.getMessage(), e);
		}//tryblock
	
		
		
	logger.info("****Finished TC001_AccountRegistrationTest****  ");
}

}
 