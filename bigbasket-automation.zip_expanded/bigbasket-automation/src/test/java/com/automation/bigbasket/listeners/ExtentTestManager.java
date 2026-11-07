package com.automation.bigbasket.listeners;

import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Thread-safe holder for the current test's ExtentTest + a step() helper that logs to console AND report. */
public final class ExtentTestManager {

    private static final Logger LOG = LogManager.getLogger(ExtentTestManager.class);
    private static final ThreadLocal<ExtentTest> CURRENT = new ThreadLocal<>();

    private ExtentTestManager() { }

    public static void set(ExtentTest test) { CURRENT.set(test); }

    public static ExtentTest get() { return CURRENT.get(); }

    public static void remove() { CURRENT.remove(); }

    public static void step(String message) {
        LOG.info(message);
        ExtentTest t = CURRENT.get();
        if (t != null) t.info(message);
    }
}
