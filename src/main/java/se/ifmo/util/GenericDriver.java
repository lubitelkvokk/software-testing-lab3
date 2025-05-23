package se.ifmo.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.ifmo.ConfProperties;

import java.time.Duration;
import java.util.List;

public class GenericDriver{
    protected WebDriver driver;
    protected WebDriverWait wait;

    public GenericDriver(DriverRealisation driverRealisation) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfProperties.getProperty("duration"))));
    }

    public void fillField(String field, String value) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(field))).sendKeys(value);
    }

    public WebElement visibilityOfElementLocated(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public List<WebElement> visibilityOfAllElementsLocatedBy(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public void get(String url) {
        driver.get(url);
    }

    public void click(By locator){
       wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

}
