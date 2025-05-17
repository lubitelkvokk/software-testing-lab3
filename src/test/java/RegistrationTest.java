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
import se.ifmo.registration.RegistrationPage;

import java.time.Duration;

public class RegistrationTest {

    private WebDriver driver;
    private JavascriptExecutor js;
    private RegistrationForm rf;
    private RegistrationPage rp;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        rp = new RegistrationPage(driver);
        js = (JavascriptExecutor) driver;

        String email = RegistrationPage.getRandomEmail();
        String phone = RegistrationPage.getRandomNumber();
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

    @Test
    public void testValidRegistration() {
        rp.registerUser(rf);
        assert rp.isRegistrationFinished();
    }

    @Test
    public void testInvalidPhoneNumber() {
        rf.setPhoneNumber("123");
        rp.registerUser(rf);
        assert rp.isMessagePresent(String.valueOf(RegistrationError.INVALID_PHONE_NUMBER));
    }

    @Test
    public void testPhoneNumberIsEmpty() {
        rf.setPhoneNumber("");
        rp.registerUser(rf);
        Assertions.assertTrue(rp.isElementDisplayedByClass("f-test-resume_card"));
    }

    @Test
    public void testPhoneNumberAsOnlySpecificSymbols() {
        rf.setPhoneNumber("###FFFS!!!!");
        rp.registerUser(rf);
        Assertions.assertTrue(rp.isElementDisplayedByClass("f-test-resume_card"));
    }

    @Test
    public void testIncorrectPhoneNumber() {
        rf.setPhoneNumber("+70001200691");
        rp.registerUser(rf);
        assert rp.isMessagePresent(String.valueOf(RegistrationError.INCORRECT_PHONE_NUMBER));
    }

    @Test
    public void testDuplicatePhoneNumber() {
        rf.setPhoneNumber("+79911200691");
        rp.registerUser(rf);
        assert rp.isMessagePresent(String.valueOf(RegistrationError.BUSY_PHONE_NUMBER));
    }
}

