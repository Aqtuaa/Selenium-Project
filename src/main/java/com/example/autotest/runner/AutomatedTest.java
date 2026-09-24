package com.example.autotest.runner;

import org.openqa.selenium.WebDriver;

public interface AutomatedTest {
    String getName();
    void run(WebDriver driver) throws Exception;
}