package se.ifmo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import se.ifmo.util.DriverRealisation;

public class WebDriverBuilder {

    private ChromeDriver chromeDriver;
    private FirefoxDriver firefoxDriver;

    public WebDriver getDriver(DriverRealisation dr) {
        switch (dr) {
            case CHROME:
                if (chromeDriver == null) {
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--headless");
                    this.chromeDriver = new ChromeDriver(chromeOptions);
                    chromeDriver.manage().window().maximize();
                }
                return chromeDriver;
            case FIREFOX:
                if (firefoxDriver == null) {
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--headless");
                    this.firefoxDriver = new FirefoxDriver(firefoxOptions);
                    firefoxDriver.manage().window().maximize();
                }
                return firefoxDriver;
        }
        throw new RuntimeException("Driver not supported");
    }

    public void clearDrivers() {
        if (chromeDriver != null) chromeDriver.quit();
        if (firefoxDriver != null) firefoxDriver.quit();
    }
}
