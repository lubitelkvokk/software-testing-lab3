import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import se.ifmo.ConfProperties;
import se.ifmo.pages.LoginPage;
import se.ifmo.pages.MainPage;
import se.ifmo.util.CookiesHelper;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthTest {

    private static final String baseUrl = "https://spb.superjob.ru/";
    private static LoginPage loginPage;
    private static MainPage mainPage;
    private static WebDriver driver;
    private static JavascriptExecutor js;

    @BeforeAll
    public static void setUpAll() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
        driver.manage().window().maximize();

        try {
            CookiesHelper.clearCookiesFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Cookie could not be deleted", e);
        }
        loginPage = new LoginPage(driver, baseUrl);
        mainPage = new MainPage(driver);
    }

    @BeforeEach
    public void setUp() {
        driver.manage().deleteAllCookies();
        driver.get(baseUrl);
        CookiesHelper.loadCookies(driver);
    }

    @AfterAll
    public static void tearDownAll() {
        driver.quit();
//        try {
//            CookiesHelper.clearCookiesFile();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException("Cookies aren't cleaned");
//        }
    }


    @Test
    @Order(1)
    public void incorrectPasswordTest() {
        loginPage.loginOnce(ConfProperties.getProperty("email"), ConfProperties.getProperty("incorrect_password"));
        Assertions.assertTrue(loginPage.isIncorrectPasswd());
    }

    @Test
    @Order(2)
    public void loginTest() {
        loginPage.loginOnce(ConfProperties.getProperty("email"), ConfProperties.getProperty("password"));
        String username = mainPage.getName();
        Assertions.assertEquals(ConfProperties.getProperty("username"), username, "Имена пользователей не совпадают");
        CookiesHelper.saveCookies(driver);
    }

    @Test
    @Order(3)
    public void logoutTest() {
        driver.navigate().refresh();
        mainPage.clickAvatar();
        mainPage.clickLogoutBtn();
        Assertions.assertTrue(loginPage.checkLoginBtn(), "Кнопка входа не отображается после выхода");
    }

}
