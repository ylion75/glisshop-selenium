package com.example.glisshopselenium;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class TestLoginAction {
    private WebDriver driver;
    private ExtentReports report;
    private ExtentTest test;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "/home/ylion/selenium/geckodriver");
        this.driver = new FirefoxDriver();
        driver.manage().window().maximize();
        report = new ExtentReports("/home/ylion/workspace/glisshop-selenium/src/report/login_report.html", true);
        test = report.startTest("TestLoginAction");
        test.log(LogStatus.INFO, "Login test has started");
    }

    @Test(priority=1)
    public void loadGlisshopSite() {
        test.log(LogStatus.INFO, "Loading glisshop site");
        driver.get("https://www.glisshop.com/");
    }

    @Test(priority = 2)
    public void testGetLoginPage() throws InterruptedException {
        //get account management page
        WebElement AccountManagementPage = driver.findElement(By.className("c-header__short-account"));
        AccountManagementPage.click();
        Thread.sleep(800);
        WebElement LoginPage = driver.findElement(By.cssSelector("a[href='/identification/']"));
        LoginPage.click();
        Thread.sleep(800);
        String expectedURL = "https://www.glisshop.com/identification/";

        if(driver.getCurrentUrl().equals(expectedURL)){
            test.log(LogStatus.PASS, "Login page has successfully loaded");
        }
        else test.log(LogStatus.ERROR, "Error retrieving Glisshop login page");
    }

    @Test(priority = 3)
    public void testLoginWithInvalidCredentialsFailed() throws InterruptedException {
        test.log(LogStatus.INFO, "Logging in with invalid credentials");
        String incorrectUsername = "testwillfail@test?com";
        String incorrectPassword = "Password123456!";

        login(incorrectUsername,incorrectPassword);

        WebElement text_danger = driver.findElement(By.className("text-danger"));
        Assert.assertNotNull(text_danger);
        test.log(LogStatus.PASS, "User can't log with invalid credentials");
    }

    @Test(priority = 4)
    public void testLoginWithRegisteredUser() throws InterruptedException, IOException {
        test.log(LogStatus.INFO, "Logging in with valid credentials");
        String registerdUser = "hello12345@test.fr";
        String password = "Motdepasse123!";

        login(registerdUser, password);
        Thread.sleep(1500);

        String expectedURL = "https://www.glisshop.com/mon-compte/mon-compte.html";

        System.out.println(driver.getCurrentUrl());
        System.out.println(expectedURL);
        if(driver.getCurrentUrl().equals(expectedURL)){
            test.log(LogStatus.PASS, "User has been redirected to correct URL");
        }
        else test.log(LogStatus.FAIL, test.addBase64ScreenShot(captureScreen(driver)) + "Logging with registered user failed");

        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
    }

    @AfterTest
    public void exit() {
        driver.close();
        test.log(LogStatus.INFO, "Login test has finished");
        report.endTest(test);
        report.flush();
    }

    private void login(String username, String password) throws InterruptedException {
        WebElement usernameElement = driver.findElement(By.id("block2-login"));
        WebElement passwordElement = driver.findElement(By.id("block2-password"));
        driver.findElement(By.id("block2-login")).clear();
        driver.findElement(By.id("block2-password")).clear();
        Thread.sleep(800);
        usernameElement.sendKeys(username);
        passwordElement.sendKeys(password);
        Thread.sleep(800);
        WebElement submit = driver.findElement(By.xpath("//button[@class='btn btn_l btn_l1 btn_full btn_l1_quaternary']"));
        submit.click();
        Thread.sleep(800);
    }

    private String captureScreen(WebDriver driver) throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File fileDestination = new File("src/report/screenshots", System.currentTimeMillis()+".png");
        String screenshot_path = "screenshots/"+System.currentTimeMillis()+".png";
        FileUtils.copyFile(screenshot, fileDestination);

        return screenshot_path;
    }
}
