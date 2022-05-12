package com.example.glisshopselenium;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestSearchItem {
    private WebDriver driver;
    private ExtentReports report;
    private ExtentTest test;

    @BeforeTest
    public void setUp(){
        System.setProperty("webdriver.gecko.driver", "/home/ylion/selenium/geckodriver");
        this.driver = new FirefoxDriver();
        driver.manage().window().maximize();
        report = new ExtentReports("/home/ylion/workspace/glisshop-selenium/src/report/item_search_report.html", true);
        test = report.startTest("TestSearchItem");
        test.log(LogStatus.INFO, "Search item test has started");
    }

    @Test(priority = 1)
    public void testGetGlisshopMainpage() {
        test.log(LogStatus.INFO, "Search item test has started");
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
    public void testSearchByBrand() throws InterruptedException {
        String brand = "lafuma";
        searchByBrand(brand);
        test.log(LogStatus.INFO, "Searching by brand : " + brand);

        String URL = driver.getCurrentUrl();

        if(URL.toLowerCase().contains(brand)){
            test.log(LogStatus.PASS, "Results fit requested brand : " + brand);
        }
        else test.log(LogStatus.FAIL, "Search by brand failed");
        Assert.assertTrue(URL.toLowerCase().contains(brand));

    }

    @AfterTest
    public void exit() {
        driver.close();
        test.log(LogStatus.INFO, "Search item test has finished");
        report.endTest(test);
        report.flush();
    }

    private void searchByBrand(String brand) throws InterruptedException {
        WebElement searchText = driver.findElement(By.name("searchText"));
        Thread.sleep(800);
        searchText.click();

        WebElement input = driver.findElement(By.id("df-searchbox__dffullscreen"));
        input.sendKeys(brand + Keys.ENTER);
        Thread.sleep(1200);
    }
}
