package com.automation.bigbasket.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.automation.bigbasket.config.ConfigReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ExtentManager {

    private static ExtentReports extent;

    private ExtentManager() { }

    public static synchronized ExtentReports get() {
        if (extent == null) {
            try {
                Files.createDirectories(Paths.get(ConfigReader.get("report.dir")));
            } catch (IOException e) {
                throw new RuntimeException("Cannot create report directory", e);
            }
            String ts = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            ExtentSparkReporter spark =
                    new ExtentSparkReporter(ConfigReader.get("report.dir") + "/ExtentReport_" + ts + ".html");
            spark.config().setDocumentTitle("BigBasket Automation Report");
            spark.config().setReportName("BigBasket E2E - Selenium + TestNG");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Browser", ConfigReader.get("browser"));
            extent.setSystemInfo("Headless", ConfigReader.get("headless"));
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java", System.getProperty("java.version"));
        }
        return extent;
    }
}
