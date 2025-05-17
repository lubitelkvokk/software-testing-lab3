package se.ifmo.searching.vacancy;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class VacancyPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private final String baseUrl;

    public VacancyPage(WebDriver driver, String baseUrl) {
        this.driver = driver;
        this.baseUrl = baseUrl;

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

    public void filterBySalary() {
        clickButton(By.xpath("//*[contains(@class, 'f-test-link-ot_')]"));
    }

    public void filterByWorkingRate(WorkingRate workingRate) {
        clickButton(By.xpath("//*[contains(@class, '" + workingRate.toString() + "')]"));
    }

    public void filterByPublicationPeriod(PublicationPeriod publicationPeriod) {
        clickButton(By.xpath("//*[contains(@class, '" + publicationPeriod.toString() + "')]"));
    }

    public void filterByEmploymentType(EmploymentType employmentType) {
        clickButton(By.xpath("//*[contains(@class, '" + employmentType.toString() + "')]"));
    }

    public void filterByVacancyType(VacancyType vacancyType) {
        clickButton(By.xpath("//*[contains(@class, '" + vacancyType.toString() + "')]"));
    }

    public void filterByAdditionalParams(AdditionalParams additionalParams) {
        clickButton(By.xpath("//*[contains(@class, '" + additionalParams.toString() + "')]"));
    }

    public String firstVacancyTime() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(@class, 'f-test-vacancy-item-')]"))
        ).findElement(
                By.xpath("./div/div/div/div/div/div/span")).getText();
    }

    public boolean vacancyIsDisplayed() {
        return isDisplayed(By.xpath("//*[contains(@class, 'f-test-vacancy-item-')]"));
    }

}
