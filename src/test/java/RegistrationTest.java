import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import se.ifmo.pages.MainPage;
import se.ifmo.pages.registration.RegistrationError;
import se.ifmo.pages.registration.RegistrationForm;
import se.ifmo.pages.registration.RegistrationPage;

import java.time.Duration;

public class RegistrationTest {

    private static WebDriver driver;
    private static JavascriptExecutor js;
    private static RegistrationForm rf;
    private static RegistrationPage rp;
    private static MainPage mp;

    @BeforeAll
    public static void setUpBeforeAll() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        rp = new RegistrationPage(driver);
        mp = new MainPage(driver);
        js = (JavascriptExecutor) driver;
        driver.manage().window().maximize();
    }

    @BeforeEach
    public void setUp() {
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

        driver.get(MainPage.baseUrl);
    }

    @AfterEach
    public void tearDown(){
        driver.manage().deleteAllCookies();
    }

    @AfterAll
    public static void tearDownAfterAll() {
        driver.quit();
    }

    @Test
    public void testValidRegistration() {
        rp.createAccount(rf);
        rp.fillRegistrationForm(rf);
        assert rp.isRegistrationFinished();
    }

    @Test
    public void testInvalidPhoneNumber() {
        rf.setPhoneNumber("123");
        rp.createAccount(rf);
        rp.fillRegistrationForm(rf);
        assert rp.isMessagePresent(String.valueOf(RegistrationError.INVALID_PHONE_NUMBER));
    }

    @Test
    public void testPhoneNumberIsEmpty() {
        rf.setPhoneNumber("");
        rp.createAccount(rf);
        rp.fillRegistrationForm(rf);
        Assertions.assertTrue(rp.isElementDisplayedByClass("f-test-resume_card"));
    }

    @Test
    public void testPhoneNumberAsOnlySpecificSymbols() {
        rf.setPhoneNumber("###FFFS!!!!");
        rp.createAccount(rf);
        rp.fillRegistrationForm(rf);
        Assertions.assertTrue(rp.isElementDisplayedByClass("f-test-resume_card"));
    }

    @Test
    public void testIncorrectPhoneNumber() {
        rf.setPhoneNumber("+70001200691");
        rp.createAccount(rf);
        rp.fillRegistrationForm(rf);
        Assertions.assertTrue(rp.isMessagePresent(String.valueOf(RegistrationError.INCORRECT_PHONE_NUMBER)));
    }

    @Test
    public void testDuplicatePhoneNumber() {
        rf.setPhoneNumber("+79911200691");
        rp.createAccount(rf);
        rp.fillRegistrationForm(rf);
       Assertions.assertTrue(rp.isMessagePresent(String.valueOf(RegistrationError.BUSY_PHONE_NUMBER)));
    }

    @Test
    public void testPostResumeWithoutContacts() {
        driver.get(MainPage.baseUrl);
        mp.postResume();
        rf.setPhoneNumber(null);
        rf.setEmail(null);
        rp.fillRegistrationForm(rf);
        Assertions.assertTrue(rp.isMessagePresent(String.valueOf(RegistrationError.EMPTY_PHONE_AND_EMAIL)));
    }
}

