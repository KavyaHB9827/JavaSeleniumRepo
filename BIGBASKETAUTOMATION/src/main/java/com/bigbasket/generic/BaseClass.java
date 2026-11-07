package com.bigbasket.generic;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;




public class BaseClass {

	public static ExtentReports report;
	public static ExtentTest test;
	public static WebDriver driver;
	public WebDriverWait wait;

    public JavaUtility jLib = new JavaUtility();
    public FileUtility fLib = new FileUtility();
    public ExcelUtility eLib = new ExcelUtility();
    public WebDriverUtility wLib = new WebDriverUtility();

    @BeforeSuite(alwaysRun = true)
    public void configReports() {
        String TIME = jLib.getSystemTime();
        ExtentSparkReporter spark = new ExtentSparkReporter("./HTML_reports/ExtentReport_" + TIME + ".html");
        report = new ExtentReports();
        report.attachReporter(spark);
    }

    @Parameters("Browser")
    @BeforeClass(alwaysRun = true)
    public void browserSetup(@Optional("chrome") String browserName) throws IOException {
        if (browserName.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        String URL = fLib.getDataFromPropertyFile("url");
        driver.get(URL);                       // Scenario 1: launch website
    }

    @BeforeMethod(alwaysRun = true)
    public void createTest(Method method) {
        test = report.createTest(method.getName());
    }

    @AfterClass(alwaysRun = true)
    public void closeBrowser() {
        driver.quit();
    }

    @AfterSuite(alwaysRun = true)
    public void reportBackup() {
        report.flush();
    }
}