import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.ifmo.registration.RegistrationError;
import se.ifmo.registration.RegistrationForm;

import java.time.Duration;
import java.util.*;

public class RegistrationTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private RegistrationForm rf;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
        wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        js = (JavascriptExecutor) driver;

        String email = getRandomEmail();
        String phone = getRandomNumber();
        String[] skills = new String[]{"Git", "Maven"};
        rf = RegistrationForm.builder()
                .email(email).firstName("Дмитрий").lastName("Сухоруков")
                .birthdate("01.01.2001").phoneNumber(phone)
                .salary("100000").professionalSkills(skills).desiredPosition("Developer")
                .build();
        rf.setWorkExpirience(
                RegistrationForm.WorkExpirience.builder().type("Developer").startMonth("Февраль")
                        .endMonth("Март").startYear("2025").endYear("2025")
                        .currentlyWork(true).haveExpirience(true).companyDescription("NICHEGO NE DELALI")
                        .build()
        );
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    private String getRandomEmail() {
        return "reg.test.id" + System.currentTimeMillis() + "@yandex.ru";
    }

    private String getRandomNumber() {
        return "+79" + (new Random().nextInt(900000000) + 100000000);
    }

    private void fillBirthDate(String birthDate) {
        WebElement dateField = driver.findElement(By.xpath("//*[contains(@class, 'f-test-input-birthDate')]"));
        dateField.click();

        WebElement inputField = dateField.findElement(By.xpath(".//input"));
        inputField.clear();
        inputField.sendKeys(birthDate);
    }

    private void fillField(By locator, String value) {
        WebElement field = driver.findElement(locator);
        field.clear();
        field.sendKeys(value);
    }

    private void fillPhoneNumber(String phoneNumber) {
        fillField(By.xpath("//*[contains(@class, 'f-test-input-phone')]"), phoneNumber);
    }

    private void fillWorkExpirience(RegistrationForm.WorkExpirience workExpirience) {
        if (!workExpirience.isHaveExpirience()) {
            clickButton(By.xpath("//div[@id='app']/div/div/div/div[4]/div/div/div/div/div/div/div[2]/div/div/div/div[2]/form/div/div[9]/div/label/span/span"));
        }
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


        List<WebElement> workMonths = driver.findElements(By.xpath("//*[contains(@class, 'f-test-input-month')]")).stream().toList();
        if (workExpirience.getStartMonth() != null) workMonths.get(0).sendKeys(workExpirience.getStartMonth());
        if (workExpirience.getEndMonth() != null) workMonths.get(1).sendKeys(workExpirience.getEndMonth());

        List<WebElement> workYears = driver.findElements(By.xpath("//*[contains(@class, 'f-test-input-year')]")).stream().toList();
        if (workExpirience.getStartYear() != null) workYears.get(0).sendKeys(workExpirience.getStartYear());
        if (workExpirience.getEndYear() != null) workYears.get(1).sendKeys(workExpirience.getEndYear());

        // TODO NOT WORKING CLICK
//        if (workExpirience.isCurrentlyWork())
//            clickButton(By.xpath("//*[contains(@class, 'f-test-checkable-Rabotayu_po_nastoyaschee_vremya')]"));

        if (workExpirience.getWorkResponsibilities() != null)
            fillField(By.xpath("//*[contains(@class, 'f-test-formField-responsibility')]//textarea"), workExpirience.getWorkResponsibilities());
    }

    private void clickButton(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    private boolean isMessagePresent(String message) {
        try {
            return driver.findElement(By.xpath("//*[contains(text(), '" + message + "')]")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isElementDisplayedByClass(String className) {
        return driver.findElement(By.xpath("//*[contains(@class, " + className + ")]")).isDisplayed();
    }

    private boolean isRegistrationFinished() {
        return driver.findElement(By.xpath("//*[contains(@class, 'f-test-resume_card')]")).isDisplayed();
    }

    private void finishRegistration() {
        List<WebElement> saves = driver.findElements(By.xpath(("//*[contains(@class, 'f-test-button-Sohranit')]")));
        saves.get(saves.size() - 1).click();
    }

    private void registerUser(RegistrationForm rf) {
        driver.get("https://spb.superjob.ru/");
        driver.manage().window().maximize();
        clickButton(By.xpath("//*[contains(@class, 'f-test-button-Voĭti')]"));
        fillField(By.xpath("//*[contains(@class, 'f-test-input-login')]"), rf.getEmail());
        clickButton(By.xpath("//*[contains(@class, 'f-test-button-Prodolzhit')]"));

        fillField(By.xpath("//*[contains(@class, 'f-test-input-person.firstName')]"), rf.getFirstName());
        fillField(By.xpath("//*[contains(@class, 'f-test-input-person.lastName')]"), rf.getLastName());
        fillPhoneNumber(rf.getPhoneNumber());
        fillBirthDate(rf.getBirthdate());


        fillWorkExpirience(rf.getWorkExpirience());

        for (String skill : rf.getProfessionalSkills()) {
            fillField(By.xpath("//*[contains(@class, 'f-test-input-professionalSkills')]"), skill);
            driver.findElement(By.xpath("//*[contains(@class, 'f-test-option-')]")).click();
        }
        fillField(By.xpath("//*[contains(@class, 'f-test-input-salary')]"), rf.getSalary());
        fillField(By.xpath("//*[contains(@class, 'f-test-input-position')]"), rf.getDesiredPosition());

        finishRegistration();
    }

    @Test
    public void testValidRegistration() {
        registerUser(rf);
        assert isRegistrationFinished();
    }

    @Test
    public void testInvalidPhoneNumber() {
        rf.setPhoneNumber("123");
        registerUser(rf);
        assert isMessagePresent(String.valueOf(RegistrationError.INVALID_PHONE_NUMBER));
    }

    @Test
    public void testPhoneNumberAsOnlySpecificSymbols() {
        rf.setPhoneNumber("###FFFS!!!!");
        registerUser(rf);
        Assertions.assertTrue(isElementDisplayedByClass("f-test-resume_card"));
    }

    @Test
    public void testIncorrectPhoneNumber() {
        rf.setPhoneNumber("+70001200691");
        registerUser(rf);
        assert isMessagePresent(String.valueOf(RegistrationError.INCORRECT_PHONE_NUMBER));
    }

    @Test
    public void testDuplicatePhoneNumber() {
        rf.setPhoneNumber("+79911200691");
        registerUser(rf);
        assert isMessagePresent(String.valueOf(RegistrationError.BUSY_PHONE_NUMBER));
    }
}

