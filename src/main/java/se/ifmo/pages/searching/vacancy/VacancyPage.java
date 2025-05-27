package se.ifmo.pages.searching.vacancy;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.ifmo.ConfProperties;

import java.time.Duration;

public class VacancyPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public VacancyPage(WebDriver driver, String baseUrl) {
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
        for (int i = 0; i < 10; i++){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
                return;
            }
            catch(StaleElementReferenceException e){
                System.out.println("One more try to click");
            }
        }
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

    public WebElement getFirstJob() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(@class, 'f-test-vacancy-item-')]"))
        );
    }

    public String firstVacancyTime() {
        return getFirstJob().findElement(
                By.xpath("./div/div/div/div/div/div/span")).getText();
    }

    public boolean vacancyIsDisplayed() {
        return isDisplayed(By.xpath("//*[contains(@class, 'f-test-vacancy-item-')]"));
    }

    public void goToFirstJobLink() {
        getFirstJob().findElement(By.xpath(".//*[contains(@class, 'f-test-link-')]")).click();
        Object[] windowHandles = driver.getWindowHandles().toArray();
        driver.switchTo().window((String) windowHandles[1]);
    }

    public boolean isUrlContainsVacancies() {
        return wait.until(ExpectedConditions.urlContains("vakansii/razrabotchik"));
    }

    public boolean isUrlContainsSalaryConditions() {
        return wait.until(ExpectedConditions.urlContains("payment_value")) &&
                wait.until(ExpectedConditions.urlContains("payment_defined=1"));
    }

    public boolean isUrlContainsWorkingRateConditions() {
        return wait.until(ExpectedConditions.urlContains("paymentPeriod"));
    }

    public boolean isUrlContainsPublicationPeriodConditions() {
        return wait.until(ExpectedConditions.urlContains(PublicationPeriod.LAST_24H.getUrlName()));
    }

    public boolean isUrlContainsEmploymentTypeConditions() {
        return wait.until(ExpectedConditions.urlContains(EmploymentType.FULL.getUrlName()));
    }

    public boolean isUrlContainsAdditionalParams() {
        return wait.until(ExpectedConditions.urlContains(AdditionalParams.AVAILABLE_FOR_STUDENTS.getUrlName()));
    }

    public boolean isUrlContainsVacancyType() {
        return wait.until(ExpectedConditions.urlContains(VacancyType.NO_EXPERIENCE_REQUIRED.getUrlName()));
    }

}
