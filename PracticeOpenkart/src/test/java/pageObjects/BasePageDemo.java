package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePageDemo {
	public WebDriver driver;
	public BasePageDemo(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

}
