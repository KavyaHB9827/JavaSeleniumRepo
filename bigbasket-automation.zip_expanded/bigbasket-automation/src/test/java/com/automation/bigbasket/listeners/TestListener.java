package com.automation.bigbasket.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.automation.bigbasket.driver.DriverFactory;
import com.automation.bigbasket.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private ExtentReports extent;

    @Override
    public void onStart(ITestContext context) {
        extent = ExtentManager.get();
    }

    @Override
    public void onTestStart(ITestResult result) {
        String name = result.getMethod().getMethodName();
        Object[] params = result.getParameters();
        if (params != null && params.length > 0) name += " [" + params[0] + "]";
        ExtentTest test = extent.createTest(name, result.getMethod().getDescription());
        ExtentTestManager.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.get().pass("Test passed");
        attachScreenshot("Final state");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTestManager.get().fail(result.getThrowable());
        attachScreenshot("Failure screenshot");
        saveScreenshotToDisk(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.get().skip(result.getThrowable() != null ? result.getThrowable().getMessage() : "Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        ExtentTestManager.remove();
    }

    private void attachScreenshot(String title) {
        WebDriver d = DriverFactory.getDriver();
        if (d == null) return;
        try {
            ExtentTestManager.get().addScreenCaptureFromBase64String(ScreenshotUtils.asBase64(d), title);
        } catch (Exception ignored) {
            // never let reporting break the run
        }
    }

    private void saveScreenshotToDisk(String name) {
        WebDriver d = DriverFactory.getDriver();
        if (d == null) return;
        try {
            ScreenshotUtils.saveToDisk(d, name);
        } catch (Exception ignored) {
            // ignore
        }
    }
}
