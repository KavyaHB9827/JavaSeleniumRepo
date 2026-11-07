package com.automation.bigbasket.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.DataProvider;

import java.io.InputStream;

/** Data-driven support: every object in testdata.json becomes one execution of the test. */
public class TestDataProvider {

    @DataProvider(name = "e2eData")
    public static Object[][] e2eData() throws Exception {
        try (InputStream is = TestDataProvider.class.getClassLoader().getResourceAsStream("testdata.json")) {
            if (is == null) throw new IllegalStateException("testdata.json not found on classpath");
            TestData[] rows = new ObjectMapper().readValue(is, TestData[].class);
            Object[][] out = new Object[rows.length][1];
            for (int i = 0; i < rows.length; i++) out[i][0] = rows[i];
            return out;
        }
    }
}
