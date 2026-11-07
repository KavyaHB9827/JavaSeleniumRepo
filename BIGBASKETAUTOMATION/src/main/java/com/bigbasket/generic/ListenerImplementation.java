package com.bigbasket.generic;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;

public class ListenerImplementation extends BaseClass implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        String methodName = result.getName();
        String TIME = jLib.getSystemTime();

        TakesScreenshot ts = (TakesScreenshot) driver;
        String screenshot = ts.getScreenshotAs(OutputType.BASE64);
        File temp = ts.getScreenshotAs(OutputType.FILE);
        File dest = new File("./screenshots/Image_" + TIME + ".png");

        try {
            FileHandler.copy(temp, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        test.addScreenCaptureFromPath("." + dest, "error image");
        test.addScreenCaptureFromBase64String(screenshot, "error image");
        test.log(Status.FAIL, methodName + " is failed");
    }
}