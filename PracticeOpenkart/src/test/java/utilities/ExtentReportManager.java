package utilities;

	import java.awt.Desktop;
	import java.io.File;
	import java.io.IOException;
	import java.net.URL;
	import java.text.SimpleDateFormat;
	import java.util.Date;
	import java.util.List;

	import org.apache.commons.io.FileUtils;
	import org.openqa.selenium.OutputType;
	import org.openqa.selenium.TakesScreenshot;
	import org.openqa.selenium.WebDriver;
	import org.testng.ITestContext;
	import org.testng.ITestListener;
	import org.testng.ITestResult;

	import com.aventstack.extentreports.ExtentReports;
	import com.aventstack.extentreports.ExtentTest;
	import com.aventstack.extentreports.Status;
	import com.aventstack.extentreports.reporter.ExtentSparkReporter;
	import com.aventstack.extentreports.reporter.configuration.Theme;

	/**
	 * Utility: TestNG listener that builds the Extent HTML report.
	 *
	 * Register it in testng.xml:
	 *   <listeners>
	 *       <listener class-name="utilities.ExtentReportManager"/>
	 *   </listeners>
	 *
	 * Maven dependency:
	 *   com.aventstack:extentreports:5.1.2
	 */
	public class ExtentReportManager implements ITestListener {

	    private ExtentSparkReporter sparkReporter;
	    private ExtentReports extent;
	    private ExtentTest test;

	    private String repName;

	    // ---------- Runs once, before the <test> tag in testng.xml starts ----------
	    @Override
	    //testContext=which test method got executed.....
	    public void onStart(ITestContext testContext) {

	        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
	        repName = "Test-Report-" + timeStamp + ".html";

	        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);//specify location of the report

	        sparkReporter.config().setDocumentTitle("Automation Report");   //REport title
	        sparkReporter.config().setReportName("Functional Testing");     // REPORT NAME
	        sparkReporter.config().setTheme(Theme.DARK);

	        extent = new ExtentReports();
	        extent.attachReporter(sparkReporter);

	        // Environment / system info shown on the dash board
	        extent.setSystemInfo("Application", "My Application");
	        extent.setSystemInfo("Operating System", System.getProperty("os.name"));//TESTER NAME
	        extent.setSystemInfo("User Name", System.getProperty("user.name"));
	        extent.setSystemInfo("Environment", "QA");

	        // Pull values from testng.xml <parameter> tags, if present
	        String os = testContext.getCurrentXmlTest().getParameter("os");
	        if (os != null) {
	            extent.setSystemInfo("Operating System (param)", os);
	        }
	        String browser = testContext.getCurrentXmlTest().getParameter("browser");
	        if (browser != null) {
	            extent.setSystemInfo("Browser", browser);
	        }

	        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
	        if (!includedGroups.isEmpty()) {
	            extent.setSystemInfo("Groups", includedGroups.toString());
	        }
	    }

	    // ---------- Runs before every @Test ----------
	    @Override
	    public void onTestStart(ITestResult result) {
	        // Optional: create the entry here so every status method can use "test"
	    }

	    // ---------- PASS ----------
	    @Override
	    public void onTestSuccess(ITestResult result) {
	        test = extent.createTest(result.getTestClass().getName());
	        test.assignCategory(result.getMethod().getGroups());   // shows groups as categories
	        test.log(Status.PASS, result.getName() + " got successfully executed");
	    }

	    // ---------- FAIL ----------
	    @Override
	    public void onTestFailure(ITestResult result) {
	        test = extent.createTest(result.getTestClass().getName());
	        test.assignCategory(result.getMethod().getGroups());

	        test.log(Status.FAIL, result.getName() + " got failed");
	        test.log(Status.INFO, result.getThrowable().getMessage());

	        try {
	            String imgPath = captureScreen(result);
	            test.addScreenCaptureFromPath(imgPath);
	        } catch (Exception e) {
	            test.log(Status.WARNING, "Could not attach screenshot: " + e.getMessage());
	        }
	    }

	    // ---------- SKIP ----------
	    @Override
	    public void onTestSkipped(ITestResult result) {
	        test = extent.createTest(result.getTestClass().getName());
	        test.assignCategory(result.getMethod().getGroups());
	        test.log(Status.SKIP, result.getName() + " got skipped");
	        if (result.getThrowable() != null) {
	            test.log(Status.INFO, result.getThrowable().getMessage());
	        }
	    }

	    // ---------- Runs once, after all tests finish ----------
	    @Override
	    public void onFinish(ITestContext testContext) {
	        extent.flush();   // IMPORTANT: without this, the report file is empty

	        //  OPTIONAL:Auto-open the report in the default browser (skip this on CI/Jenkins)
	        String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
	        File extentReport = new File(pathOfExtentReport);
	        try {
	            Desktop.getDesktop().browse(extentReport.toURI());
	        } catch (Exception e) {
	            // headless / CI machine - ignore
	        }

	        /* Optional: email the report
	        try {
	            URL url = new URL("file:///" + System.getProperty("user.dir") + "\\reports\\" + repName);
	            // ImageHtmlEmail email = new ImageHtmlEmail(); ... (Apache commons-email)
	        } catch (Exception e) { e.printStackTrace(); }
	        */
	    }

	    // ---------- Helper: take screenshot using the driver from the failed test class ----------
	    private String captureScreen(ITestResult result) throws IOException {

	        // Gets the driver field from the running test class (works if your BaseClass has `public WebDriver driver;`)
	        Object testClass = result.getInstance();
	        WebDriver driver;
	        try {
	            driver = (WebDriver) testClass.getClass().getSuperclass().getDeclaredField("driver").get(testClass);
	        } catch (Exception e) {
	            throw new IOException("Unable to access driver from test class", e);
	        }

	        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

	        TakesScreenshot ts = (TakesScreenshot) driver;
	        File source = ts.getScreenshotAs(OutputType.FILE);

	        String targetPath = System.getProperty("user.dir") + "\\screenshots\\"
	                + result.getName() + "_" + timeStamp + ".png";
	        FileUtils.copyFile(source, new File(targetPath));

	        return targetPath;
	    }
	}



