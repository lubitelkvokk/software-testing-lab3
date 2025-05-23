package se.ifmo.pages.searching.course;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.ifmo.ConfProperties;
import se.ifmo.pages.searching.cv.EducationType;
import se.ifmo.pages.searching.cv.EmploymentType;
import se.ifmo.pages.searching.cv.SeekerActivity;
import se.ifmo.pages.searching.vacancy.VacancyType;
import se.ifmo.pages.searching.vacancy.WorkingRate;

import java.time.Duration;

public class CoursePage {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public CoursePage(WebDriver driver, String baseUrl) {
        this.driver = driver;

        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfProperties.getProperty("duration"))));
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

    public void filterBySpecialization(Specialization specialization) {
        clickButton(By.xpath("//*[contains(@class, '" + specialization + "')]"));
    }

    public void filterByEducationType(EducationFormat educationFormat) {
        clickButton(By.xpath("//*[contains(@class, '" + educationFormat + "')]"));
    }

    public void filterByDifficultyLevel(DifficultyLevel difficultyLevel) {
        clickButton(By.xpath("//*[contains(@class, '" + difficultyLevel + "')]"));
    }

    public void filterByCost(Cost cost) {
        clickButton(By.xpath("//*[contains(@class, '" + cost + "')]"));
    }

    public boolean CourseIsDisplayed() {
        return isDisplayed(By.xpath("//*[contains(@class, 'f-test-badge')]"));
    }

}
