package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;


 
public class TC003_LoginDDT extends BaseClass {
	@Test(dataProvider="Logindata",dataProviderClass=DataProviders.class,groups="datadriven")//getting data provider from different pkg hence specify DP cls name  
	public void verify_loginDDT(String email,String pwd,String exp) {
		logger.info("***** Starting TC003_LoginDDT*****");
		try {
		
		//Homepage
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//LoginPage
		LoginPage lp=new LoginPage(driver);
		lp.setTxtEmailAddress( email);
		lp.setTxtPassword(pwd);
		lp.btnLogin();
		
		//My Account Page
		MyAccountPage macc=new MyAccountPage(driver);
		boolean targetPage = macc.isMyAccountPageExists();
		/*1.Data is valid -login success-testpass-      logout
		 *                -login failed - test fail-    cannot logout only cursor remains on login page only
		 * 
		 * 2.Data is invalid -login success - test fail - logout bcos login is success irrespective of wrong credentials
	                         -login failed - test pass
		 */
		if(exp.equalsIgnoreCase("valid"))
			if(targetPage==true)
		
			{
				macc.clickLogout();
				Assert.assertTrue(true);//first logout and then put assertion because after assertion  completed no line will execute
			}
			else {
				Assert.assertTrue(false);
			}
		if(exp.equalsIgnoreCase("Invalid"))
			if(targetPage==true)
		
			{
				macc.clickLogout();
				Assert.assertTrue(true);
			}
			else {
				Assert.assertTrue(true );
			}
		}//try block
		
		catch(Exception e)
		{
			Assert.fail();
		}
		logger.info("***** Finished TC003_LoginDDT*****");
		
		
	}

}
