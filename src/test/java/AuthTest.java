import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import se.ifmo.ConfProperties;
import se.ifmo.WebDriverBuilder;
import se.ifmo.pages.LoginPage;
import se.ifmo.pages.MainPage;
import se.ifmo.util.DriverRealisation;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthTest {

    private static final String baseUrl = "https://spb.superjob.ru/";
    private LoginPage loginPage;
    private MainPage mainPage;
    private static WebDriverBuilder genericDriver;

    @BeforeAll
    public static void setUpBeforeAll() {
       genericDriver = new WebDriverBuilder();
    }

    @AfterAll
    public static void tearDownAll() {
        genericDriver.clearDrivers();
    }

    public void browser_setup(DriverRealisation driverRealisation) {
        loginPage = new LoginPage(genericDriver.getDriver(driverRealisation), baseUrl);
        mainPage = new MainPage(genericDriver.getDriver(driverRealisation));
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    @Order(1)
    public void incorrectPasswordTest(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        mainPage.getUrl(baseUrl);

        loginPage.loginOnce(ConfProperties.getProperty("email"), ConfProperties.getProperty("incorrect_password"));
        Assertions.assertTrue(loginPage.isIncorrectPasswd());
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    @Order(2)
    public void loginTest(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        mainPage.getUrl(baseUrl);

        loginPage.loginOnce(ConfProperties.getProperty("email"), ConfProperties.getProperty("password"));
        String username = mainPage.getName();
        Assertions.assertEquals(ConfProperties.getProperty("username"), username, "Имена пользователей не совпадают");
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    @Order(3)
    public void logoutTest(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);

        mainPage.clickAvatar();
        mainPage.clickLogoutBtn();
        Assertions.assertTrue(loginPage.checkLoginBtn(), "Кнопка входа не отображается после выхода");
    }

    static Stream<Arguments> browser() {
        return Stream.of(
                arguments(DriverRealisation.CHROME),
                arguments(DriverRealisation.FIREFOX)
        );
    }

}
