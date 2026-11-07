package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePageDemo;
import pageObjects.LoginPageDemo;
import pageObjects.MyAccountPageDemo;
import testBase.BaseClassDemo;
import utilities.DataProvidersPrc;

public class TC_003_LoginDDTDemo extends BaseClassDemo{
	@Test(dataProvider="LoginData", dataProviderClass=DataProvidersPrc.class)
	public void verify_login(String email,String pwd,String exp) {
		logger.info("***TC_003_LoginDDTDemo Started *** ");
		
		//HOME PAGE
		HomePageDemo hp=new HomePageDemo(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//LOGIN PAGE
		
		LoginPageDemo lp=new LoginPageDemo(driver);
		lp.setTxtEmailAddress(email);
		lp.setTxtPassword(pwd);
		lp.clickLogin();
		
		//MYACCOUNT PAGE
		MyAccountPageDemo macc=new MyAccountPageDemo(driver);
		boolean targetPage = macc.isMyAccountPageExist();
		
		/*1.Data is valid -login success-testpass-      logout
		 *                -login failed - test fail-    cannot logout only cursor remains on login page only
		 * 
		 * 2.Data is invalid -login success - test fail - logout bcos login is success irrespective of wrong credentials
	                         -login failed - test pass
		 */
		
		
			
		if(exp.equalsIgnoreCase("Valid")) 
		
		{
			if(targetPage==true)
				macc.clickLogout();
			Assert.assertTrue(true);
			
		}
		else{
			Assert.assertTrue(false);
		}
		
		
		if(exp.equalsIgnoreCase("Invalid")) 
		{
			if(targetPage==false);
			macc.clickLogout();
			Assert.assertTrue(false);
		}
		
		else {
			Assert.assertTrue(true);
		}


			
		
		
	}	
		
	}
