package se.ifmo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import se.ifmo.util.DriverRealisation;

import java.util.ArrayList;
import java.util.List;

public class SingletonWebDriver {
    private static List<WebDriver> drivers = new ArrayList<WebDriver>();


    private static boolean driverIsCreated(WebDriver driver) {
        boolean flag = false;
        for (WebDriver d : drivers) {
            if (d instanceof driver.getClass()) {
                flag = true;
            }
        }
        return flag;
    }

    public static WebDriver getDriver(DriverRealisation dr) {
        switch (dr) {
            case CHROME:
                if (drivers.co)
                    driver = new ChromeDriver();
                break;
            case FIREFOX:
                driver = new FirefoxDriver();
        }
    }
}
