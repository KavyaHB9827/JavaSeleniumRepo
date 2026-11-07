package pageObjects;
//PARENT OF ALL PAGEOBJECT CLASSES bcos CONSTRUCTOR IS SAME
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
	
	public WebDriver driver;
	public BasePage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	}

	