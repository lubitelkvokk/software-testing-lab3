package se.ifmo.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import se.ifmo.ConfProperties;

import java.time.Duration;

public class Util {


    public static WebDriver setUpDriver() {
        WebDriver driver;
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("firefoxdriver"));
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
//        driver.manage().window().maximize();
        driver.get(ConfProperties.getProperty("loginpage"));
        return driver;
    }
}
