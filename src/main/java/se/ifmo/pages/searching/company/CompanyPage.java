package se.ifmo.pages.searching.company;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CompanyPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public CompanyPage(WebDriver driver, String baseUrl) {
        this.driver = driver;

        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private void fillField(By locator, String value) {
        WebElement field = driver.findElement(locator);
        field.clear();
        field.sendKeys(value);
    }

    private void clickButton(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    private boolean isDisplayed(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
    }

    public void chooseCompanyWithOpenVacancies() {
        clickButton(By.xpath("//*[contains(@class, 'f-test-link-Kompanii_s_otkrytymi_vakansiyami')]"));
    }

    public void chooseCompanyWithAttractiveEmployment() {
        clickButton(By.xpath("//*[contains(@class, 'f-test-link-Privlekatelnyj_rabotodatel')]"));
    }

    public void chooseCompanyWithOpenEmployer() {
        clickButton(By.xpath("//*[contains(@class, 'f-test-link-Otkrytyj_rabotodatel')]"));
    }

    public boolean CompanyIsDisplayed() {
        return isDisplayed(By.xpath("//*[contains(@class, 'f-test-search-result-item')]"));
    }


}
