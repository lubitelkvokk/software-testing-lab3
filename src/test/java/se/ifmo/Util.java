package se.ifmo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

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
