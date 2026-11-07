package testCases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage1;
import pageObjects.LoginPage1;
import pageObjects.MyAccountPage1;
import testBase.BaseClass1;
import utilities.DataProvidersPrc;

public class TC_003_LoginDDT1 extends BaseClass1 {

	@Test(dataProvider="LoginData", dataProviderClass=DataProvidersPrc.class )//dataProviderClass=DataProvidersPrc.class-getting data provider from dif class 
		public void verify_Login(String eamil,String pwd,String exp) {
		logger.info("***Starting TC_003_LoginDDT1***");
		
	 
		//HOME PAGE
		try {
		HomePage1 hp=new HomePage1(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//LOGIN PAGE
		LoginPage1 lp=new LoginPage1(driver);
		lp.setTxtEmailAddress(eamil);
		lp.setTxtPassword(pwd); 
		lp.clickLogin();
		
		//MY ACCOUNT PAGE
		MyAccountPage1 macc=new MyAccountPage1(driver);
		boolean targetPage = macc.isMyAccountPageExists();
		
		/*1.Data is valid -login success-testpass-      logout
		 *                -login failed - test fail-    cannot logout only cursor remains on login page only
		 * 
		 * 2.Data is invalid -login success - test fail - logout bcos login is success irrespective of wrong credentials
	                         -login failed - test pass
		 */
		if( exp.equalsIgnoreCase("Valid"))
		{
			if(targetPage==true);// then lOGIN IS SUCCESFUL
			macc.clickLogout();
			Assert.assertTrue(true);
		}
		else {
			Assert.assertTrue(false);
		}//1
		
		if(exp.equalsIgnoreCase("InValid"))
		{
			if(targetPage==true);//lOGIN IS SUCCESFUL even for invalid credentials
			macc.clickLogout();
			Assert.assertTrue(false); 
		}
		else {
			Assert.assertTrue(true);
		}//2
		}catch(Exception e) {
			Assert.fail();
			
		}
		logger.info("***Finished TC_003_LoginDDT1***");
			
		}

	
}
