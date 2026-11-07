package testBase;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.Logger;//careful
import org.apache.logging.log4j.LogManager;


public class BaseClass1 {
	 
	public WebDriver driver;
	public Logger logger; //log4j
	 public Properties p;
	
	 @BeforeClass
	 @Parameters({"os", "browser"})
	 public void setUp(String os, String br) throws IOException {

	     // Load property file
	     FileReader file = new FileReader( "./src/test/resources/config.properties" );
	     p = new Properties();
	     p.load(file);
	     file.close();

	     // Initialize logger
	     logger = LogManager.getLogger(this.getClass());

	     // Launch selected browser
	     switch (br.toLowerCase()) {
	         case "chrome":driver = new ChromeDriver();
             break;
	             

	         case "edge":driver = new EdgeDriver();
             break;
	             

	         case "firefox":driver = new FirefoxDriver();
             break;
	             

	         default:throw new IllegalArgumentException(
	                 "Invalid browser: " + br );
	            
	             
	     }

	     driver.manage().deleteAllCookies();
	     driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	     driver.get("https://tutorialsninja.com/demo/index.php");
	     driver.manage().window().maximize();
	 }
	
	 @AfterClass
	 public void tearDown() {
	     if (driver != null) {
	         driver.quit();
	     }
	 }
	
	public String RandomeString() {
		String generatedString = RandomStringUtils.randomAlphabetic(10);
		return generatedString;
	
	}
	public String randomeNumber() {
		String generatedNumber = RandomStringUtils.randomNumeric(10);
		return generatedNumber;
	}
	
	
	public String RandomeAlphaNumeric() {
		String generatedNumber = RandomStringUtils.randomNumeric(10);
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return (generatedNumber+"@"+generatedString);
	}

public String randomPhoneNumber() {
    long number = 1000000000L+ (long) (Math.random() * 9000000000L);
    return String.valueOf(number);//randomPhoneNumber is from chat gpt    

    
}


}
