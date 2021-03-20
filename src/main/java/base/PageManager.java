package base;

import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.LoginPage;

public class PageManager {
    private LoginPage loginPage;
    private HomePage homePage;
    private final WebDriver driver;

    public PageManager(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage getLoginPage() {
        return (loginPage == null) ? loginPage = new LoginPage(driver) : loginPage;
    }

    public HomePage getHomePage() {
        return (homePage == null) ? homePage = new HomePage(driver) : homePage;
    }
}
