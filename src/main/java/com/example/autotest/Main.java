package com.example.autotest;

import com.example.autotest.runner.AutomatedTest;
import com.example.autotest.runner.TestRunner;
import org.openqa.selenium.WebDriver;

public class Main {

    public Main(){
        AutomatedTest test = new AutomatedTest() {
            public String getName() {
                return "Open selenium.dev";
            }

            public void run(WebDriver driver) {
                driver.get("https://www.selenium.dev");
                if (!driver.getTitle().toLowerCase().contains("selenium")) {
                    throw new AssertionError("Title tidak sesuai: " + driver.getTitle());
                }
            }
        };

        System.out.println(TestRunner.execute(test, false));
    }
    public static void main(String[] args) {
        new Main();
    }
}