package se.ifmo.pages.searching.respond;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.ifmo.ConfProperties;
import se.ifmo.pages.registration.RegistrationForm;
import se.ifmo.pages.registration.RegistrationPage;

import java.time.Duration;
import java.util.List;

public class RespondPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private final String baseUrl;
    private RegistrationPage rp;

    public RespondPage(WebDriver driver, String baseUrl) {
        this.driver = driver;
        this.baseUrl = baseUrl;
        rp = new RegistrationPage(driver);

        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfProperties.getProperty("duration"))));
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void sleep(Long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    private void fillField(By locator, String value) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        field.clear();
        field.sendKeys(value);
    }

    private void clickButton(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    private void forcedClick(By locator) {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(locator));
        js.executeScript("arguments[0].click();", btn);
    }

    private boolean isDisplayed(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
    }

    public boolean respondIsSent() {
        return isDisplayed(By.xpath("//*[contains(@class, 'f-test-button-Otklik_otpravlen')]"));
    }

    private void respondToCurrVacancy() {
        By locator = By.xpath(".//*[contains(@class, 'f-test-vacancy-response-button')]");
        WebElement respondBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        wait.until(ExpectedConditions.elementToBeClickable(respondBtn)).click();
    }

    public void fillWorkExpirience(RegistrationForm.WorkExpirience workExpirience) {
        if (workExpirience.getType() != null)
            fillField(By.xpath("//*[contains(@class, 'f-test-input-experience.position')]"), workExpirience.getType());
        if (workExpirience.getCompany() != null)
            fillField(By.xpath("//*[contains(@class, 'f-test-input-resumeCompany.title')]"), workExpirience.getCompany());
        if (workExpirience.getCompanySite() != null)
            fillField(By.xpath("//*[contains(@class, 'f-test-input-resumeCompany.website')]"), workExpirience.getCompanySite());

        if (workExpirience.getCompanyCity() != null)
            fillField(By.xpath("//*[contains(@class, 'f-test-input-town.id')]"), workExpirience.getCompanyCity());

        List<WebElement> workMonths = driver.findElements(By.xpath("//*[contains(@class, 'f-test-input-month')]")).stream().toList();
        if (workExpirience.getStartMonth() != null) workMonths.get(0).sendKeys(workExpirience.getStartMonth());
        if (workExpirience.getEndMonth() != null) workMonths.get(1).sendKeys(workExpirience.getEndMonth());

        List<WebElement> workYears = driver.findElements(By.xpath("//*[contains(@class, 'f-test-input-year')]")).stream().toList();
        if (workExpirience.getStartYear() != null) workYears.get(0).sendKeys(workExpirience.getStartYear());
        if (workExpirience.getEndYear() != null) workYears.get(1).sendKeys(workExpirience.getEndYear());

        if (workExpirience.getWorkResponsibilities() != null)
            fillField(By.xpath("//*[contains(@class, 'f-test-formField-responsibility')]/..//textarea"), workExpirience.getWorkResponsibilities());
    }

    public void respondWithoutAccount() {
        RegistrationForm rf = RegistrationForm.builder().firstName("Решал").lastName("Решалыч")
                .phoneNumber(RegistrationPage.getRandomNumber()).birthdate("05.08.1997")
                .workExpirience(
                        RegistrationForm.WorkExpirience.builder().type("Решатель проблем").company("РешаЙ")
                                .startMonth("Февраль").endMonth("Март")
                                .startYear("2020").endYear("2020").workResponsibilities("Решал самые насущные проблемы")
                                .build()
                ).build();

        respondToCurrVacancy();
        rp.fillBaseInfo(rf);
        try {
            fillWorkExpirience(rf.getWorkExpirience());
        } catch (NoSuchElementException e) {
            // normal way, because not all vacancies have this field
        }

        List<WebElement> btns = driver.findElements(By.xpath("//*[contains(@class, 'f-test-button-Otkliknutsya')]"));
        btns.get(btns.size() - 1).click();
    }

    public void respondWithAccount() {
        // TODO AUTHORIZE
        respondToCurrVacancy();
    }
}
