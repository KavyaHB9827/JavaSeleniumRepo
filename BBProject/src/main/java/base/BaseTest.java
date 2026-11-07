package base;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


import utils.ConfigReader;
import utils.DriverFactory;

public class BaseTest {

    @BeforeMethod
    public void setUp() {

        String browser = ConfigReader.getProperty("browser");

        DriverFactory.initializeDriver(browser);

        DriverFactory.getDriver().get(
                ConfigReader.getProperty("url"));
    }

    @AfterMethod
    public void tearDown() {

        DriverFactory.quitDriver();
    }
}
