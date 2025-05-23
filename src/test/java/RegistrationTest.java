import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import se.ifmo.pages.MainPage;
import se.ifmo.pages.registration.RegistrationError;
import se.ifmo.pages.registration.RegistrationForm;
import se.ifmo.pages.registration.RegistrationPage;

public class RegistrationTest {

    private static WebDriver driver;
    private static JavascriptExecutor js;
    private static RegistrationForm rf;
    private static RegistrationPage rp;
    private static MainPage mp;
    public static String baseUrl = "https://spb.superjob.ru/";

    @BeforeAll
    public static void setUpBeforeAll() {
        driver = new ChromeDriver();
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
    }

    @AfterEach
    public void tearDown() {
        driver.manage().deleteAllCookies();
    }

    @AfterAll
    public static void tearDownAfterAll() {
        driver.quit();
    }

    private void initDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":

            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.manage().window().maximize();
        rp = new RegistrationPage();
        mp = new MainPage(driver);
        js = (JavascriptExecutor) driver;

        driver.get(baseUrl);
    }

    @ParameterizedTest(name = "Browser: {0}")
    @ValueSource(strings = {"chrome", "firefox"})
    public void testValidRegistration(String browser) {
        initDriver(browser);

        rf.setPhoneNumber(null);
        rp.createAccount(rf);
        rp.fillRegistrationForm(rf);
        assert rp.isRegistrationFinished();
    }

    @ParameterizedTest(name = "Browser: {0}")
    @ValueSource(strings = {"chrome", "firefox"})
    public void testInvalidPhoneNumber(String browser) {
        initDriver(browser);

        rf.setPhoneNumber("123");
        rp.createAccount(rf);
        rp.fillRegistrationForm(rf);
        assert rp.isMessagePresent(String.valueOf(RegistrationError.INVALID_PHONE_NUMBER));
    }

    @ParameterizedTest(name = "Browser: {0}")
    @ValueSource(strings = {"chrome", "firefox"})
    public void testPhoneNumberIsEmpty(String browser) {
        initDriver(browser);

        rf.setPhoneNumber("");
        rp.createAccount(rf);
        rp.fillRegistrationForm(rf);
        Assertions.assertTrue(rp.isElementDisplayedByClass("f-test-resume_card"));
    }


    @ParameterizedTest(name = "Browser: {0}")
    @ValueSource(strings = {"chrome", "firefox"})
    public void testPhoneNumberAsOnlySpecificSymbols(String browser) {
        initDriver(browser);

        rf.setPhoneNumber("###FFFS!!!!");
        rp.createAccount(rf);
        rp.fillRegistrationForm(rf);
        Assertions.assertTrue(rp.isElementDisplayedByClass("f-test-resume_card"));
    }

    @ParameterizedTest(name = "Browser: {0}")
    @ValueSource(strings = {"chrome", "firefox"})
    public void testIncorrectPhoneNumber(String browser) {
        initDriver(browser);

        rf.setPhoneNumber("+70001200691");
        rp.createAccount(rf);
        rp.fillRegistrationForm(rf);
        Assertions.assertTrue(rp.isMessagePresent(String.valueOf(RegistrationError.INCORRECT_PHONE_NUMBER)));
    }

    @ParameterizedTest(name = "Browser: {0}")
    @ValueSource(strings = {"chrome", "firefox"})
    public void testDuplicatePhoneNumber(String browser) {
        initDriver(browser);

        rf.setPhoneNumber("+79911200691");
        rp.createAccount(rf);
        rp.fillRegistrationForm(rf);
        Assertions.assertTrue(rp.isMessagePresent(String.valueOf(RegistrationError.BUSY_PHONE_NUMBER)));
    }

    @ParameterizedTest(name = "Browser: {0}")
    @ValueSource(strings = {"chrome", "firefox"})
    public void testPostResumeWithoutContacts(String browser) {
        initDriver(browser);
        driver.get(baseUrl);
        mp.postResume();
        rf.setPhoneNumber(null);
        rf.setEmail(null);
        rp.fillRegistrationForm(rf);
        Assertions.assertTrue(rp.isMessagePresent(String.valueOf(RegistrationError.EMPTY_PHONE_AND_EMAIL)));
    }
}

