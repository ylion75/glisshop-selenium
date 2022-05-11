package com.example.glisshopselenium;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
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
import java.io.FileReader;
import java.io.IOException;

public class TestCreateAccount {
    private WebDriver driver;
    private static ExtentReports report;
    private static ExtentTest test;
    private final String CSVPATH = "/home/ylion/workspace/glisshop-selenium/src/test/data/CreateAccountData.csv";
    private String username;
    private String userPassword;
    private String userPasswordConfirm;


    @BeforeTest
    public void setUp() throws CsvValidationException, IOException {
        System.setProperty("webdriver.gecko.driver", "/home/ylion/selenium/geckodriver");
        this.driver = new FirefoxDriver();
        driver.manage().window().maximize();
        report = new ExtentReports("/home/ylion/workspace/glisshop-selenium/src/report/account_creation_report.html", true);
        test = report.startTest("TestCreateAccount");

        CSVReader csvReader = new CSVReader(new FileReader(this.CSVPATH));
        String[] csvData;
        while ((csvData = csvReader.readNext()) != null) {
            this.username = csvData[0];
            this.userPassword = csvData[1];
            this.userPasswordConfirm = csvData[2];
        }
    }

    @Test(priority = 1)
    public void testGetGlisshopMainpage() {
        test.log(LogStatus.INFO, "Account creation test has started");
        driver.get("https://www.glisshop.com/");
        String expectedURL = "https://www.glisshop.com/";
        try{
            Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
            test.log(LogStatus.PASS, "Glisshop main page has successfully loaded");
        }
        catch(AssertionError e){
            test.log(LogStatus.ERROR, "Error retrieving Glisshop main page");
        }
    }

    @Test(priority = 2)
    public void testGetAccountCreationPage() throws InterruptedException {
        WebElement AccountLink = driver.findElement(By.className("c-header__short-account"));
        AccountLink.click();
        Thread.sleep(800);
        String expectedURL = "https://www.glisshop.com/glisshop/creation-de-compte.html";

        try{
            Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
            test.log(LogStatus.PASS, "Account creation page has been successfully loaded");
        }
        catch(AssertionError e){
            test.log(LogStatus.ERROR, "Error retrieving account creation page");
        }
    }

    @Test(priority = 3)
    public void completeLoginForm() throws InterruptedException {
        WebElement username = driver.findElement(By.id("rbs-user-create-account-email"));
        WebElement password = driver.findElement(By.id("rbs-user-create-account-password"));
        WebElement confirmPassword = driver.findElement(By.id("rbs-user-create-account-confirm-password"));
        Thread.sleep(800);
        username.sendKeys(this.username);
        password.sendKeys(userPassword);
        confirmPassword.sendKeys(userPasswordConfirm);
        test.log(LogStatus.INFO, "Account details completed");
        Thread.sleep(800);
    }

    @Test(priority = 4)
    public void submitForm() throws InterruptedException {
        WebElement submit = driver.findElement(By.xpath("//button[@class='btn btn_l btn_l1 btn_full btn_l1_quaternary']"));
        Thread.sleep(800);
        submit.click();
        test.log(LogStatus.INFO, "Account creation form submitted");
    }

    @Test(priority = 5)
    public void testAccountCreationSuccessful() throws IOException {
        //user should be redicted to expectedURL after successful account creation
        String expectedURL = "https://www.glisshop.com/mon-compte/mon-compte.html";
        System.out.println(driver.getCurrentUrl());
        System.out.println(expectedURL);

        if(expectedURL.equals(driver.getCurrentUrl())){
            test.log(LogStatus.PASS, "User has been successfully redireced to account management page");
        }
        else test.log(LogStatus.FAIL, test.addBase64ScreenShot(captureScreen(driver)) + "Account creation test has failed");

        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
    }

    @AfterTest
    public void exit(){
        driver.close();
        test.log(LogStatus.INFO, "Account creation test has finished");
        report.endTest(test);
        report.flush();
    }

    private String captureScreen(WebDriver driver) throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File fileDestination = new File("src/report/screenshots", System.currentTimeMillis()+".png");
        String screenshot_path = "screenshots/"+System.currentTimeMillis()+".png";
        System.out.println(screenshot_path);
        FileUtils.copyFile(screenshot, fileDestination);

        return screenshot_path;
    }
}
