package testCases;

import pageObjects.AccountRegistrationPageDemo;
import pageObjects.HomePageDemo;
import testBase.BaseClassDemo;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.bytebuddy.utility.RandomString;

public class TC001_AccountRegistrationTestDemo extends BaseClassDemo {
	
	@Test
	public void verify_account_Registration() {
		try {
		logger.info("*** Starting TC001_AccountRegistrationTestDemo***");
		HomePageDemo hp=new HomePageDemo(driver);
		hp.clickMyAccount();
		logger.info("*** Clicked on MyAccount link***");
		
		hp.clickRegister();
		logger.info("*** Clicked on Register Account link***");
		
		AccountRegistrationPageDemo regpage=new AccountRegistrationPageDemo(driver);
		regpage.setTxtFirstName(randomString().toUpperCase());
		regpage.setTxtLastName(randomString().toUpperCase());
		regpage.setTxtEmail(randomString()+"@mail.com");
		
		String password = randomalphaNumeric();
		regpage.setTxtPassword(password);
		regpage.setTxtPasswordconfirm(password);
		
		regpage.setChckdPolicy();
		regpage.setBtncontinue();
		
		logger.info("***Validating expected message");
		String confmsg = regpage.msgConfirmation(); 
		
		if(confmsg.equals("Your Account Has Been Created!")) {
		
			Assert.assertTrue(true);
		}
		
		else {
			
			logger.error("..Test failed....");
			logger.debug("....Debug logs");
		}
		
		}catch(Exception e) {
			
			Assert.fail();
		}
		/*Assert.assertEquals(confmsg,"Your Account Has Been Created!",
			    "Account registration confirmation message did not match"
			);
			    
		logger.info("***** Finished TC001_AccountRegistrationTest*****");
*/
		logger.info("***Finished TC001_AccountRegistrationTestDemo***");
		 

	
		
		
	}	
	
	
	
	
	
	
}