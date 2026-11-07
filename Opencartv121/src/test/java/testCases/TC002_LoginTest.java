package testCases;
//Execute master.xml

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {
	@Test(groups= {"Sanity","Master"})
	public void verify_login() 
	{
		logger.info("***** Starting TC002_LoginTest*****");
		try {
		
		//HomePage
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		 
		//Login Page
		LoginPage lp=new LoginPage(driver);
		lp.setTxtEmailAddress(p.getProperty("email"));
		lp.setTxtPassword(p.getProperty("password"));
		lp.btnLogin();
		
		//MyAccount
		MyAccountPage macc=new MyAccountPage(driver);
		boolean targetPage = macc.isMyAccountPageExists();
		
		//Assert.assertEquals(targetPage, true,"Login failed");
		Assert.assertTrue(targetPage);
		
		}
		catch(Exception e) {
		    e.printStackTrace();
		    Assert.fail("Login test failed: " + e.getMessage(), e);
		}
		logger.info("***** Finished TC002_LoginTest*****");
		

		
	
	}


}

