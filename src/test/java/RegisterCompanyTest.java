package se.ifmo.registration;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import se.ifmo.WebDriverBuilder;
import se.ifmo.pages.registration.company.CompanyType;
import se.ifmo.pages.registration.company.ExperienceVar;
import se.ifmo.pages.registration.company.RegisterCompanyPage;
import se.ifmo.pages.registration.RegistrationPage;
import se.ifmo.pages.searching.company.CompanyPage;
import se.ifmo.util.DriverRealisation;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegisterCompanyTest {
    private static WebDriver driver;
    private RegisterCompanyPage registerPage;
    private static WebDriverBuilder driverBuilder;


    @BeforeAll
    public static void setUp() {
        driverBuilder = new WebDriverBuilder();
    }

    @AfterAll
    public static void tearDown() {
        driverBuilder.clearDrivers();
    }

    public void browser_setup(DriverRealisation driverRealisation) {
        registerPage = new RegisterCompanyPage(driverBuilder.getDriver(driverRealisation));
    }

    @Order(1)
    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testRegisterCompany(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);

        registerPage.openMainPage();
        registerPage.clickLoginButton();
        String email = RegistrationPage.getRandomEmail();
        String phone = RegistrationPage.getRandomNumber();
        registerPage.fillLoginForm(email, phone);

        registerPage.clickContinueButton();
        registerPage.registerAs(CompanyType.PRIVATE_PERSON);
        registerPage.clickContinueButton();
        registerPage.fillCompanyDetails("BIG_COMPANY_TECH", "125283246346", "big.company.tech.com", "Делаем интересные вещи");
        registerPage.clickContinueButton();
        registerPage.fillHRDetails("Антон", "Бобов", "Генеральный директор");
        registerPage.submitForm();

        Assertions.assertTrue(registerPage.isRegistrationSuccessful());
    }

    @Order(2)
    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testVacancyCreate(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);

        registerPage.goToPersonalAccount();
        registerPage.createVacancy("Developer", ExperienceVar.FROM_1YEAR);
        Assertions.assertTrue(registerPage.isVacancyPublicationSuccessfull());
    }

    static Stream<Arguments> browser() {
        return Stream.of(
                arguments(DriverRealisation.CHROME),
                arguments(DriverRealisation.FIREFOX)
        );
    }
}

