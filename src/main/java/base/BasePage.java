package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class BasePage {

    private Properties properties;
    private final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    public WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    public WebDriver initializeDriver() throws MalformedURLException {
        boolean cloudTest = properties.getProperty("cloudtest").equals("yes");

        String browser = properties.getProperty("browser");

        MutableCapabilities capabilities;

        if (cloudTest) {

            String URL = "https://" + properties.getProperty("username") + ":" + properties.getProperty("access_key")
                    + properties.getProperty("domain");

            switch (browser) {
                case "chrome":
                    capabilities = new ChromeOptions();
                    break;
                case "firefox":
                    capabilities = new FirefoxOptions();
                    break;
                case "edge":
                    capabilities = new EdgeOptions();
                    break;
                case "iexplorer":
                    capabilities = new InternetExplorerOptions();
                    break;
                default:
                    throw new RuntimeException("Browser is not supported!");
            }

            capabilities.setCapability("browserVersion", properties.getProperty("browserVersion"));
            capabilities.setCapability("platformName", properties.getProperty("platformName"));
            capabilities.setCapability("sauce:options", setSauceOptions());
            threadLocalDriver.set(new RemoteWebDriver(new URL(URL), capabilities));

        } else {

            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    threadLocalDriver.set(new ChromeDriver());
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    threadLocalDriver.set(new FirefoxDriver());
                    break;
                case "opera":
                    WebDriverManager.operadriver().setup();
                    threadLocalDriver.set(new OperaDriver());
                    break;
                default:
                    throw new RuntimeException("Browser is not supported!");
            }
        }
        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        getDriver().get(properties.getProperty("baseURL"));

        return getDriver();
    }

    public Properties initializeProperties() {
        properties = new Properties();
        String path = "./src/main/java/properties/configuration.properties";
        try {
            FileInputStream file = new FileInputStream(path);
            properties.load(file);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Trouble locating the properties file");
        }
        return properties;
    }

    public void quitBrowser() {
        if (getDriver() != null) {
            System.out.println(getDriver());
            getDriver().quit();
        }
    }


    private MutableCapabilities setSauceOptions() {
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("seleniumVersion", "3.141.59");
        sauceOptions.setCapability("name", "Sauce Lab Demo test");
        sauceOptions.setCapability("build", "test123");
        sauceOptions.setCapability("public", "private");
        return sauceOptions;
    }
}
