package se.ifmo.pages.registration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.ifmo.ConfProperties;
import se.ifmo.SingletonWebDriver;
import se.ifmo.util.DriverRealisation;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class RegistrationPage {


    private final WebDriver driver;
    private final WebDriverWait wait;

    public RegistrationPage(DriverRealisation driverRealisation) {
        driver = SingletonWebDriver.getDriver(driverRealisation);
        wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfProperties.getProperty("duration"))));
    }

    public static String getRandomEmail() {
        return "reg.test.id" + System.currentTimeMillis() + "@yandex.ru";
    }

    public static String getRandomNumber() {
        return "+79" + (new Random().nextInt(900000000) + 100000000);
    }

    private void fillBirthDate(String birthDate) {
        By by = By.xpath("//*[contains(@class, 'f-test-input-birthDate')]");
        clickButton(by);
        visibilityOfElementLocated(by).sendKeys(birthDate);
    }

    private void fillField(By locator, String value) {
        WebElement field = visibilityOfElementLocated(locator);
        field.clear();
        field.sendKeys(value);
    }

    private WebElement visibilityOfElementLocated(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement waitUntilVisibleElement(By locator) {
        return visibilityOfElementLocated(locator);
    }

    private List<WebElement> waitUntilVisibleElements(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    private void fillPhoneNumber(String phoneNumber) {
        fillField(By.xpath("//*[contains(@class, 'f-test-input-phone')]"), phoneNumber);
    }

    private void fillEmail(String email) {
        By locator = By.xpath("//*[contains(@class, 'f-test-input-contacts.email')]");
        WebElement emailField = waitUntilVisibleElement(locator);
        emailField.clear();
        if (emailField.getText().isEmpty()) {
            fillField(locator, email);
        }
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

        if (workExpirience.getCompanyDescription() != null)
            fillField(By.xpath("//textarea[contains(@name, 'resumeCompany.description')]"), workExpirience.getCompanyDescription());


        List<WebElement> workMonths = waitUntilVisibleElements(By.xpath("//*[contains(@class, 'f-test-input-month')]")).stream().toList();
        if (workExpirience.getStartMonth() != null) workMonths.get(0).sendKeys(workExpirience.getStartMonth());
        if (workExpirience.getEndMonth() != null) workMonths.get(1).sendKeys(workExpirience.getEndMonth());

        List<WebElement> workYears = waitUntilVisibleElements(By.xpath("//*[contains(@class, 'f-test-input-year')]")).stream().toList();
        if (workExpirience.getStartYear() != null) workYears.get(0).sendKeys(workExpirience.getStartYear());
        if (workExpirience.getEndYear() != null) workYears.get(1).sendKeys(workExpirience.getEndYear());

        if (workExpirience.getWorkResponsibilities() != null)
            fillField(By.xpath("//*[contains(@class, 'f-test-formField-responsibility')]//textarea"), workExpirience.getWorkResponsibilities());
    }

    private void clickButton(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public boolean isMessagePresent(String message) {
        try {
            return waitUntilVisibleElement(By.xpath("//*[contains(text(), '" + message + "')]")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementDisplayedByClass(String className) {
        return waitUntilVisibleElement(By.xpath("//*[contains(@class, " + className + ")]")).isDisplayed();
    }

    public boolean isRegistrationFinished() {
        return waitUntilVisibleElement(By.xpath("//*[contains(@class, 'f-test-resume_card')]")).isDisplayed();
    }

    private void finishRegistration() {
        List<WebElement> saves = waitUntilVisibleElements(By.xpath(("//*[contains(@class, 'f-test-button-Sohranit')]")));
        saves.get(saves.size() - 1).click();
    }

    public void fillBaseInfo(RegistrationForm rf) {
        if (rf.getFirstName() != null)
            fillField(By.xpath("//*[contains(@class, 'f-test-input-person.firstName')]"), rf.getFirstName());
        if (rf.getLastName() != null)
            fillField(By.xpath("//*[contains(@class, 'f-test-input-person.lastName')]"), rf.getLastName());
        if (rf.getPhoneNumber() != null) fillPhoneNumber(rf.getPhoneNumber());
        if (rf.getBirthdate() != null) fillBirthDate(rf.getBirthdate());
    }

    public void createAccount(RegistrationForm rf) {
        clickButton(By.xpath("//*[contains(@class, 'f-test-button-Voĭti')]"));
        fillField(By.xpath("//*[contains(@class, 'f-test-input-login')]"), rf.getEmail());
        clickButton(By.xpath("//*[contains(@class, 'f-test-button-Prodolzhit')]"));
    }

    public void fillRegistrationForm(RegistrationForm rf) {
        fillBaseInfo(rf);

        fillWorkExpirience(rf.getWorkExpirience());

        for (String skill : rf.getProfessionalSkills()) {
            fillField(By.xpath("//*[contains(@class, 'f-test-input-professionalSkills')]"), skill);
            clickButton(By.xpath("//*[contains(@class, 'f-test-option-')]"));
        }
        fillField(By.xpath("//*[contains(@class, 'f-test-input-salary')]"), rf.getSalary());
        fillField(By.xpath("//*[contains(@class, 'f-test-input-position')]"), rf.getDesiredPosition());

        finishRegistration();
    }

    public void getUrl(String baseUrl) {
        driver.get(baseUrl);
    }
}
