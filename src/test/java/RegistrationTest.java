import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.*;
import se.ifmo.SingletonWebDriver;
import se.ifmo.pages.MainPage;
import se.ifmo.pages.registration.RegistrationError;
import se.ifmo.pages.registration.RegistrationForm;
import se.ifmo.pages.registration.RegistrationPage;
import se.ifmo.util.DriverRealisation;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class RegistrationTest {

    private static SingletonWebDriver singletonWebDriver;
    private static JavascriptExecutor js;
    private RegistrationForm rf;
    private RegistrationPage rp;
    private MainPage mp;
    public static final String baseUrl = "https://spb.superjob.ru/";


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
//        driver.manage().deleteAllCookies();
    }

    @AfterAll
    public static void tearDownAfterAll() {
        SingletonWebDriver.clearDrivers();
    }

    private void initDriver(DriverRealisation driverRealisation) {
        rp = new RegistrationPage(driverRealisation);
        mp = new MainPage(driverRealisation);
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testValidRegistration(DriverRealisation browserDriver) {
        initDriver(browserDriver);

        rp.getUrl(baseUrl);
        rf.setPhoneNumber(null);
        rf.setEmail(RegistrationPage.getRandomEmail());
        rp.createAccount(rf);
        rp.fillRegistrationForm(rf);
        assert rp.isRegistrationFinished();
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testInvalidPhoneNumber(DriverRealisation driverRealisation) {
        initDriver(driverRealisation);

        rp.getUrl(baseUrl);
        rf.setPhoneNumber("123");
        rp.createAccount(rf);
        rp.fillRegistrationForm(rf);
        assert rp.isMessagePresent(String.valueOf(RegistrationError.INVALID_PHONE_NUMBER));
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testPhoneNumberIsEmpty(DriverRealisation driverRealisation) {
        initDriver(driverRealisation);

        rp.getUrl(baseUrl);
        rf.setPhoneNumber("");
        rf.setEmail(RegistrationPage.getRandomEmail());
        rp.createAccount(rf);
        rp.fillRegistrationForm(rf);
        Assertions.assertTrue(rp.isElementDisplayedByClass("f-test-resume_card"));
    }


    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testPhoneNumberAsOnlySpecificSymbols(DriverRealisation driverRealisation) {
        initDriver(driverRealisation);

        rp.getUrl(baseUrl);
        rf.setPhoneNumber("###FFFS!!!!");
        rf.setEmail(RegistrationPage.getRandomEmail());
        rp.createAccount(rf);
        rp.fillRegistrationForm(rf);
        Assertions.assertTrue(rp.isElementDisplayedByClass("f-test-resume_card"));
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testIncorrectPhoneNumber(DriverRealisation driverRealisation) {
        initDriver(driverRealisation);

        rp.getUrl(baseUrl);
        rf.setPhoneNumber("+70001200691");
        rp.createAccount(rf);
        rp.fillRegistrationForm(rf);
        Assertions.assertTrue(rp.isMessagePresent(String.valueOf(RegistrationError.INCORRECT_PHONE_NUMBER)));
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testDuplicatePhoneNumber(DriverRealisation driverRealisation) {
        initDriver(driverRealisation);

        rp.getUrl(baseUrl);
        rf.setPhoneNumber("+79911200691");
        rp.createAccount(rf);
        rp.fillRegistrationForm(rf);
        Assertions.assertTrue(rp.isMessagePresent(String.valueOf(RegistrationError.BUSY_PHONE_NUMBER)));
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testPostResumeWithoutContacts(DriverRealisation driverRealisation) {
        initDriver(driverRealisation);
        mp.getUrl(baseUrl);
        mp.postResume();
        rf.setPhoneNumber(null);
        rf.setEmail(null);
        rp.fillRegistrationForm(rf);
        Assertions.assertTrue(rp.isMessagePresent(String.valueOf(RegistrationError.EMPTY_PHONE_AND_EMAIL)));
    }

    static Stream<Arguments> browser() {
        return Stream.of(
                arguments(DriverRealisation.CHROME),
                arguments(DriverRealisation.FIREFOX)
        );
    }
}

