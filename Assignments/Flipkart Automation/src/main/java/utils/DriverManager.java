package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverManager {

    private static WebDriver driver;

    /**
     * This method is used to initialize the driver for chrome driver
     */
    public static void initializeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--disable-popup-blocking");
        options.setAcceptInsecureCerts(true);
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
        capabilities.setCapability(CapabilityType.SUPPORTS_LOCATION_CONTEXT, true);
        capabilities.setCapability(CapabilityType.SUPPORTS_APPLICATION_CACHE, true);
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        driver = WebDriverManager.chromedriver().capabilities(options).create();
        System.out.println("Chrome Browser is launched");

        driver.manage().window().maximize();

    }

    public static WebDriver getDriver() {
        if (driver == null) {
            initializeDriver();
        }
        return driver;
    }

    /**
     * This method quits/close the driver if it is not null
     */
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

}