package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.TestUtils;

public class LoginPage {

    TestUtils testUtils;

    By userName = By.id("user-name");
    By password = By.id("password");
    By loginBtn = By.xpath("//input [@value='LOGIN']");
    By errorMsg = By.xpath("//h3 [@data-test='error']");

    public LoginPage(WebDriver driver) {
        testUtils = new TestUtils(driver);
    }

    public String verifyTitle() {
        return testUtils.doGetPageTitle();
    }

    public void doLogin(String user, String pass) {
        testUtils.doSendKeys(userName, user);
        testUtils.doSendKeys(password, pass);
        testUtils.doClick(loginBtn);
    }

    public String getErrorMessage() {
        return testUtils.doGetText(errorMsg);
    }

}
