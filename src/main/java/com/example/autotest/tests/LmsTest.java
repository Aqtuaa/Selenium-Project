package com.example.autotest.tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LmsTest {

        WebDriver driver;
        WebDriverWait wait;

        @AfterEach
        void tearDown() {
                if (driver != null) driver.quit();
        }

// ===================== TEST 1: OPEN COURSE =====================
        @Test
        void testOpenCourse() throws InterruptedException {
                driver = openWithLoggedInProfile();
                wait = new WebDriverWait(driver, Duration.ofSeconds(15));

                driver.get("https://binusmaya.binus.ac.id/");
                System.out.println("Buka Bimay");

                String originalWindow = driver.getWindowHandle();

                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//p[text()='LMS']"))).click();

                wait.until(d -> d.getWindowHandles().size() > 1);
                for (String handle : driver.getWindowHandles()) {
                        if (!handle.equals(originalWindow)) {
                                driver.switchTo().window(handle);
                                break;
                        }
                }

                wait.until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[href='/lms/course']"))).click();

                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//a[contains(@class,'tab-item-link') and text()='LEC']")));

                Thread.sleep(1500);

                By lecTab = By.xpath("//a[contains(@class,'tab-item-link') and text()='LEC']");
                int maxRetry = 3;
                for (int i = 0; i < maxRetry; i++) {
                        wait.until(ExpectedConditions.elementToBeClickable(lecTab)).click();
                        Thread.sleep(1000);

                        WebElement lecElement = driver.findElement(lecTab);
                        String classAttr = lecElement.getAttribute("class");
                        if (classAttr != null && !classAttr.contains("tab-inactive")) {
                                break;
                        }
                }

                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[text()='Automation Testing']"))).click();

                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(@class,'tab-item-link')]//span[text()='Session 1']"))).click();

                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[text()='1. Introduction to Software Testing']"))).click();

                Thread.sleep(3000);
        }

    // ===================== TEST 2: CREATE FORUM THREAD =====================
        @Test
        void testCreateForumThread() throws InterruptedException {
                driver = openWithLoggedInProfile();
                wait = new WebDriverWait(driver, Duration.ofSeconds(15));

                driver.get("https://binusmaya.binus.ac.id/");

                String originalWindow = driver.getWindowHandle();

                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//p[text()='LMS']"))).click();

                wait.until(d -> d.getWindowHandles().size() > 1);
                for (String handle : driver.getWindowHandles()) {
                        if (!handle.equals(originalWindow)) {
                                driver.switchTo().window(handle);
                                break;
                        }
                }

                wait.until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[href='/lms/forum']"))).click();

                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//a[contains(@class,'tab-item-link') and text()='LEC']")));

                Thread.sleep(1500);

                By lecTab = By.xpath("//a[contains(@class,'tab-item-link') and text()='LEC']");
                int maxRetry = 3;
                for (int i = 0; i < maxRetry; i++) {
                        wait.until(ExpectedConditions.elementToBeClickable(lecTab)).click();
                        Thread.sleep(1000);

                        WebElement lecElement = driver.findElement(lecTab);
                        String classAttr = lecElement.getAttribute("class");
                        if (classAttr != null && !classAttr.contains("tab-inactive")) {
                                break;
                        }
                }

                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[text()='Automation Testing']"))).click();

                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[text()='CREATE NEW THREAD']"))).click();

                WebElement titleField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@placeholder='Write title here...']")));
                titleField.clear();
                titleField.sendKeys("[AUTOMATED TEST] Thread - " + System.currentTimeMillis());

                WebElement bodyEditor = wait.until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector("div.jodit-wysiwyg")));
                bodyEditor.click();
                bodyEditor.sendKeys("Testing forum otomatis menggunakan Selenium WebDriver");

                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[text()='Post']"))).click();

                Thread.sleep(3000);
        }

    // ===================== TEST 3: GIVE LAMP REACTION =====================
        @Test
        void testGiveLampReaction() throws InterruptedException {
                driver = openWithLoggedInProfile();
                wait = new WebDriverWait(driver, Duration.ofSeconds(15));

                driver.get("https://binusmaya.binus.ac.id/");

                String originalWindow = driver.getWindowHandle();

                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//p[text()='LMS']"))).click();

                wait.until(d -> d.getWindowHandles().size() > 1);
                for (String handle : driver.getWindowHandles()) {
                        if (!handle.equals(originalWindow)) {
                                driver.switchTo().window(handle);
                                break;
                        }
                }

                wait.until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[href='/lms/forum']"))).click();

                By lecTab = By.xpath("//a[contains(@class,'tab-item-link') and text()='LEC']");
                WebElement lecElement = wait.until(ExpectedConditions.presenceOfElementLocated(lecTab));
                if (lecElement.getAttribute("class").contains("tab-inactive")) {
                        wait.until(ExpectedConditions.elementToBeClickable(lecTab)).click();
                        Thread.sleep(1500);
                }

                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[text()='Automation Testing']"))).click();

                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(@class,'tab-item-link')]//span[text()='Session 1']"))).click();

                Thread.sleep(1500); 

                WebElement threadLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("(//span[contains(@class,'thread-title')])[1]/ancestor::a[1]")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", threadLink);

                wait.until(ExpectedConditions.urlContains("/thread/"));

                wait.until(ExpectedConditions.elementToBeClickable(
                        By.id("button-like"))).click();
        }
    // ===================== HELPER =====================
        private WebDriver openWithLoggedInProfile() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=C:\\SeleniumProfile");
        options.addArguments("profile-directory=Default");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
        }
}