package testCases;

import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage1;
import pageObjects.LoginPage1;
import pageObjects.MyAccountPage1;
import testBase.BaseClass1;

public class TC002_LoginTest1 extends BaseClass1 {
	@Test
	public void verify_login() {
		
		logger.info("***Starting TC002_LoginTest1***");
		try {
		//HOMEPAGE
		 HomePage1 hp=new HomePage1(driver);
		 hp.clickMyAccount();
		 hp.clickLogin();
		 
		 //LOGINPAGE
		LoginPage1 lp=new LoginPage1(driver);
		lp.setTxtEmailAddress(p.getProperty("email"));
		lp.setTxtPassword(p.getProperty("password"));
		lp.clickLogin();
		
		//MYACCOUNTPAGE
		MyAccountPage1 macc=new MyAccountPage1(driver);
	boolean targetPage = macc.isMyAccountPageExists();

	Assert.assertTrue(targetPage);//Assert.assertEquals(targetPage, true,"LoginFailed");
		}
		catch(Exception e) {
			Assert.fail();
			
		}
	logger.info("***Finished TC002_LoginTest1***");
		
	}

}