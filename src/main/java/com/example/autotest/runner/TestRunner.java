package com.example.autotest.runner;

import com.example.autotest.driver.DriverFactory;
import com.example.autotest.model.TestResult;
import org.openqa.selenium.WebDriver;

public class TestRunner {
    public static TestResult execute(AutomatedTest test, boolean headless) {
        WebDriver driver = DriverFactory.create(headless);
        long start = System.currentTimeMillis();
        try {
            test.run(driver);
            return new TestResult(test.getName(), true, System.currentTimeMillis() - start, "OK");
        } catch (Throwable t) {
            return new TestResult(test.getName(), false, System.currentTimeMillis() - start, t.getMessage());
        } finally {
            driver.quit();
        }
    }
}