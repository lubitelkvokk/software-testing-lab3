package se.ifmo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import se.ifmo.util.DriverRealisation;

import java.util.ArrayList;
import java.util.List;

public class SingletonWebDriver {

    private static ChromeDriver chromeDriver;
    private static FirefoxDriver firefoxDriver;

    public static WebDriver getDriver(DriverRealisation dr) {
        switch (dr) {
            case CHROME:
                if (chromeDriver == null)
                    chromeDriver = new ChromeDriver();
                return chromeDriver;
            case FIREFOX:
                if (firefoxDriver == null)
                    firefoxDriver = new FirefoxDriver();
                return firefoxDriver;
        }
        throw new RuntimeException("Driver not supported");
    }

    public static void clearDrivers(){
        if (chromeDriver != null) chromeDriver.quit();
        if (firefoxDriver != null) firefoxDriver.quit();
        chromeDriver = null;
        firefoxDriver = null;
    }
}
