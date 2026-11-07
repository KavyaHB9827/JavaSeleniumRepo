package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage1;
import pageObjects.HomePage1;
import testBase.BaseClass1;

public class TC001_AccountRegistrationTest1 extends BaseClass1 {
	@Test
	
	public void verify_account_registration() {
		logger.info("*****Starting TC001_AccountRegistrationTest*****");
		
		
		HomePage1 hp=new HomePage1(driver);
		hp.clickMyAccount();
		logger.info(" Clicked on Myaccount link");
		hp.clickRegister();
		logger.info("Clicked on registrationPage link");
		
		AccountRegistrationPage1 regpage=new AccountRegistrationPage1(driver);
		logger.info("Providing Customer Details");
		regpage.setTxtFirstName(RandomeString().toUpperCase());
		regpage.setTxtLastName(RandomeString().toUpperCase());
		regpage.setTxtEmail(RandomeString()+"@gmail.com");//randomly generated email
		regpage.setTxtTelephone(randomPhoneNumber());//THIS WAS MISSED
		
		
		String password = RandomeAlphaNumeric();//same password must confirm so store in variable and use when required multiple times
		 
		
		regpage.setTxtPassword(password);
		regpage.setTxtPasswordconfirm(password);
		 
		regpage.setChckdPolicy();
		//regpage.setRadioButtonNo();//pavan didnt include as it is checked default
		regpage.setBtncontinue();
		
		String confmsg = regpage.getMsgConfirmation();

		Assert.assertEquals(confmsg,
			    "Your Account Has Been Created!",
			    "Account registration confirmation message did not match"
			);
		logger.info("***** Finished TC001_AccountRegistrationTest*****");
			
		}
		
		
		
	}

	
	
	
	
	
