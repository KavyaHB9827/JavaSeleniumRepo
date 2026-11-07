package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePageDemo;
import pageObjects.LoginPageDemo;
import pageObjects.MyAccountPageDemo;
import testBase.BaseClassDemo;

public class TC002_LoginTestDemo extends BaseClassDemo {
	@Test
	public void verify_Login() {
		logger.info("*** TC002_LoginTestDemo is starting***");
		try {
		//Homepage
		
		HomePageDemo hp=new HomePageDemo(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//LoginPage
		LoginPageDemo lp= new LoginPageDemo(driver);
		lp.setTxtEmailAddress(p.getProperty("email"));
		lp.setTxtPassword(p.getProperty("password"));
		lp.clickLogin();
		
		//MyAccountPage
		MyAccountPageDemo macc=new MyAccountPageDemo(driver);
		boolean targetPage = macc.isMyAccountPageExist();
		Assert.assertTrue(targetPage);
		}
		catch(Exception e) {
		Assert.fail();
		}
		logger.info("***TC002_Login is Finished***");
}

}
