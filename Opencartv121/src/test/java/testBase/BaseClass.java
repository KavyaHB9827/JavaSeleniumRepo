package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.Logger;//careful
import org.apache.logging.log4j.LogManager;

public class BaseClass {
	public  static WebDriver driver;//to have same driver reference throughout
	public Logger logger;//log4j
	public Properties p;
	
	
	
	@BeforeMethod(groups= {"Sanity","Regression","Master"})
	@Parameters({"os","browser"})
	public void setUp(String os,String br) throws IOException { 
		
		//loading config.properties file
		
		FileReader file=new FileReader(".//src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		
		 // Initialize logger
		
		logger=LogManager.getLogger(this.getClass());//log4j2
		
		//for GRID ENVIRONMENT
		if(p.getProperty("execution_env").equalsIgnoreCase("remote")){
			
			DesiredCapabilities capabilities=new DesiredCapabilities();
			
			//OS
			if(os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
						}
			else if(os.equalsIgnoreCase("windows"))
					
			{
				capabilities.setPlatform(Platform.MAC);
			}
			else {
				System.out.println("No Matching OS");
				return;
			}
			//BROWSER
			switch(br.toLowerCase())
			{
			case "chrome":capabilities.setBrowserName("chrome");break;
			case "edge":capabilities.setBrowserName("MicrosoftEdge");break;
			default: System.out.println("No Matching Browser");return;
			
				
			}
			//driver for remote execution
			driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);//url and code wont change even u use docker,VM, standalone,distributed
		}	
		
		if(p.getProperty("execution_env").equalsIgnoreCase("local")) {
			switch(br.toLowerCase())
			
			{
			 // Launch selected browser
			case "chrome" :driver=new ChromeDriver();break;
			case "edge" :driver=new EdgeDriver();break;
			case "firefox" :driver=new FirefoxDriver();break;
			
			default:throw new IllegalArgumentException(
	                "Invalid browser: " + br ); //if browser itself wrong it exits from execution automatically
			}
			
			
		}
		
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appURL2"));//READING URL from properties file
		driver.manage().window().maximize();
	}
	@AfterMethod(groups= {"Sanity","Regression","Master"})
	public void tearDown() {
		if(driver!=null) {
		driver.quit();//you may get socket exception with driver.close
		}
	}
	public String randomeString() {
		String generatedstring = RandomStringUtils.randomAlphabetic(5);
		return generatedstring;
		
	}
	
	public String randomeNumber() {
		
		String generatednumber = RandomStringUtils.randomNumeric(10);
		return generatednumber;
	}
public String randomeAlphaNumeric()

{
	String generatednumber = RandomStringUtils.randomNumeric(10);//password will have alpha numeric combo of number and string
	String generatedstring = RandomStringUtils.randomAlphabetic(5);
	return(generatednumber+"@"+generatedstring);
	

	}
public String randomPhoneNumber() {
    long number = 1000000000L+ (long) (Math.random() * 9000000000L);
    return String.valueOf(number);//randomPhoneNumber is from chat gpt

    
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


