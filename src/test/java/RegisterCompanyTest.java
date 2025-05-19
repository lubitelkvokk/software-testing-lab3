package se.ifmo.registration;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import se.ifmo.pages.registration.company.CompanyType;
import se.ifmo.pages.registration.company.ExperienceVar;
import se.ifmo.pages.registration.company.RegisterCompanyPage;
import se.ifmo.pages.registration.RegistrationPage;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegisterCompanyTest {
    private static WebDriver driver;
    private static RegisterCompanyPage registerPage;


    @BeforeAll
    public static void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        registerPage = new RegisterCompanyPage(driver);
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Order(1)
    @Test
    public void testRegisterCompany() {
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
    @Test
    public void testVacancyCreate() {
        registerPage.goToPersonalAccount();
        registerPage.createVacancy("Developer", ExperienceVar.FROM_1YEAR);
        Assertions.assertTrue(registerPage.isVacancyPublicationSuccessfull());
    }
}

