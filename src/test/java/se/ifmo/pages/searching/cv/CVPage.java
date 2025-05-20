package se.ifmo.pages.searching.cv;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.ifmo.pages.searching.vacancy.*;

import java.time.Duration;

public class CVPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public CVPage(WebDriver driver, String baseUrl) {
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

    public void filterBySalary() {
        clickButton(By.xpath("//*[contains(@class, 'f-test-link-ot_')]"));
    }

    public void filterByWorkingRate(WorkingRate workingRate) {
        clickButton(By.xpath("//*[contains(@class, '" + workingRate.toString() + "')]"));
    }

    public void filterBySeekerActivity(SeekerActivity seekerActivity) {
        clickButton(By.xpath("//*[contains(@class, '" + seekerActivity.toString() + "')]"));
    }

    public void filterByEmploymentType(EmploymentType employmentType) {
        clickButton(By.xpath("//*[contains(@class, '" + employmentType.toString() + "')]"));
    }
    public void filterByVacancyType(VacancyType vacancyType) {
        clickButton(By.xpath("//*[contains(@class, '" + vacancyType + "')]"));
    }

    public void filterByEducationType(EducationType educationType) {
        clickButton(By.xpath("//*[contains(@class, '" + educationType + "')]"));
    }

    public WebElement getFirstCV() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(@class, 'f-test-resume-snippet-')]"))
        );
    }

    public String firstCVTime() {
        return getFirstCV().findElement(
                By.xpath("./div/div/div/div/div/div/div/div/div/div/span")).getText();
    }

    public boolean CVIsDisplayed() {
        return isDisplayed(By.xpath("//*[contains(@class, 'f-test-resume-snippet-')]"));
    }

    public void goToFirstJobLink() {
        getFirstCV().findElement(By.xpath(".//*[contains(@class, 'f-test-link-')]")).click();
        Object[] windowHandles = driver.getWindowHandles().toArray();
        driver.switchTo().window((String) windowHandles[1]);
    }

}
