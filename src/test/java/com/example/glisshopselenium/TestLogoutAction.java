package com.example.glisshopselenium;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestLogoutAction {
    private WebDriver driver;
    private ExtentReports report;
    private ExtentTest test;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "/home/ylion/selenium/geckodriver");
        this.driver = new FirefoxDriver();
        driver.manage().window().maximize();
        report = new ExtentReports("/home/ylion/workspace/glisshop-selenium/src/report/logout_report.html", true);
        test = report.startTest("TestLogoutAction");
        test.log(LogStatus.INFO, "Login test has started");
    }

    @Test
    public void assertUserIsLogged() throws InterruptedException {
        registerUser();
        Thread.sleep(1500);

        String expectedURL = "https://www.glisshop.com/mon-compte/mon-compte.html";
        if(driver.getCurrentUrl().equals(expectedURL)){
            test.log(LogStatus.PASS, "User has been redirected to correct URL");
        }
        else test.log(LogStatus.FAIL, "Logging with registered user failed");

        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
    }

    @Test
    public void assertUserIsRedirectedOnLogout() throws InterruptedException {
        //check if user is redirected to the right URL on logout
        WebElement logout = driver.findElement(By.cssSelector("[title='Déconnexion']"));
        test.log(LogStatus.WARNING, "Logout button is clicked");
        Thread.sleep(1200);
        logout.click();

        //check if user is correctly logged out and login button is available
        WebElement submit = driver.findElement(By.xpath("//button[@class='btn btn_l btn_l1 btn_full btn_l1_quaternary']"));
        if(submit.isDisplayed()){
            test.log(LogStatus.PASS, "User has been logged out");
        }
        else test.log(LogStatus.FAIL, "User is still logged in");
        Assert.assertTrue(submit.isDisplayed());
        Thread.sleep(1200);
    }

    @Test
    public void assertAccountPageCantBeAccessed() {
        //verify that user can't access to account manager page once logged out
    }

    @AfterTest
    public void exit() {
        driver.close();
        test.log(LogStatus.INFO, "Logout test has finished");
        report.endTest(test);
        report.flush();
    }

    private void registerUser() throws InterruptedException {
        driver.get("https://www.glisshop.com/identification/");
        test.log(LogStatus.WARNING, "Logging user");
        String registerdUser = "hello12345@test.fr";
        String password = "Motdepasse123!";

        login(registerdUser, password);

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
        Thread.sleep(1500);
    }
}
