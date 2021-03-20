package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.TestUtils;

public class HomePage {

    TestUtils testUtils;

    By appLogo = By.xpath("//div[@class='app_logo']");

    public HomePage(WebDriver driver) {
        testUtils = new TestUtils(driver);
    }

    public boolean isAppLogoDisplayed() {
        return testUtils.doIsDisplayed(appLogo);
    }


}
