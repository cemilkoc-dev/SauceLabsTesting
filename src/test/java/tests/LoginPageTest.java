package tests;

import base.BasePage;

import base.PageManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Constants;

import java.net.MalformedURLException;
import java.util.Properties;

public class LoginPageTest extends BasePage {

    PageManager pageManager;
    Properties props;
    WebDriver driver;

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        props = initializeProperties();
        driver = initializeDriver();
        pageManager = new PageManager(driver);
    }

    @Test(priority = 1)
    public void pageTitleTest() {
        Assert.assertEquals(pageManager.getLoginPage().verifyTitle(), Constants.LOGIN_PAGE_TITLE);
    }

    @Test(priority = 2)
    public void validLoginTest() {
        pageManager.getLoginPage().doLogin(props.getProperty("appUserName"), props.getProperty("appPassword"));
        Assert.assertTrue(pageManager.getHomePage().isAppLogoDisplayed());
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @Test(priority = 3)
    public void invalidLoginTest(){
        pageManager.getLoginPage().doLogin("test", "pass");
        Assert.assertEquals(pageManager.getLoginPage().getErrorMessage(), Constants.LOGIN_PAGE_ERROR_MESSAGE);
    }

    @Test(priority = 4)
    public void blankLoginTest(){
        pageManager.getLoginPage().doLogin("", "");
        Assert.assertEquals(pageManager.getLoginPage().getErrorMessage(), Constants.LOGIN_PAGE_BLANK_ERROR_MESSAGE);
    }

    @AfterMethod
    public void tearDown() {
        quitBrowser();
    }

}
