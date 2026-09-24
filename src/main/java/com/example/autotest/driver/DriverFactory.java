package com.example.autotest.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    public static WebDriver create(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1366,768");
        if (headless) {
            options.addArguments("--headless=new");
        }
        return new ChromeDriver(options);
    }
}