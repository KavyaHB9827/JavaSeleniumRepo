package testBase;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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



public class BaseClassDemo {
	public WebDriver driver;
	public Logger logger;
	public Properties p;
	
	
	@BeforeClass
	@Parameters({"os","browser"})
	
	public void setUp(String os,String br) throws IOException {
	
		//Loading config.property file
		FileReader file=new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		file.close();
		
		// Initialize logger
		logger=LogManager.getLogger(this.getClass());//LOG4J2
		
		// Launch selected browser
		switch(br.toLowerCase()) {
		case "chrome":driver=new ChromeDriver();break;
		case "edge":driver=new EdgeDriver();break;
		case "firefox":driver=new FirefoxDriver();break;
		default:throw new IllegalArgumentException(
                "Invalid browser: " + br );//parallel testing done only once after executing all test cases 
           
		
		
		}
		
		
		driver.manage().deleteAllCookies();
		driver.get(p.getProperty("appURL2"));//Reading url from properties file 
		driver.manage().window().maximize();
	}
		@AfterClass
		public void tearDown() {
			if(driver!=null) {
			driver.close();
			}
		}
		
		public String randomString() {
			String generatedstring = RandomStringUtils.randomAlphabetic(10);
			return generatedstring;
		}
		
		public String randomNumber() {
			String generatednumber = RandomStringUtils.randomNumeric(10);
			return generatednumber;
		}
		public String randomalphaNumeric() {
			String generatedstring = RandomStringUtils.randomAlphabetic(5);
			String generatednumber = RandomStringUtils.randomNumeric(10);
			return(generatedstring+"@"+generatednumber);

		}
		public String randomPhoneNumber() {
			long number=1000000000+(long)(Math.random()*9000000000L);
			return String.valueOf(number);
		}
		public String captureScreen(String tname) throws IOException {

		    String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss")
		            .format(new Date()); 

		    TakesScreenshot takesscreenshot = (TakesScreenshot) driver;

		    File sourceFile = takesscreenshot.getScreenshotAs(OutputType.FILE);

		    String targetFilePath = System.getProperty("user.dir")
		            + "\\screenshots\\" + tname + "_" + timeStamp + ".png";

		    File targetFile = new File(targetFilePath);

		    sourceFile.renameTo(targetFile);

		    return targetFilePath;//this path is needed in extent report to atatch
		}

		
	}


