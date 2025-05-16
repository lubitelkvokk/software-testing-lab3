import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.openqa.selenium.WebDriver;
import se.ifmo.ConfProperties;
import se.ifmo.Util;
import se.ifmo.pages.LoginPage;
import se.ifmo.pages.MainPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthTest {
    private LoginPage loginPage;
    private MainPage mainPage;
    private WebDriver driver;

    @BeforeAll
    public void setUpBeforeClass() {
        driver = Util.setUpDriver();
        loginPage = new LoginPage(driver);
        mainPage = new MainPage(driver);
    }


    private void loginOnce(String login, String password) {
        if (!loginPage.checkLoginBtn()){
            loginPage.clickLoginBtn();
            loginPage.inputLogin(login);
            loginPage.inputPasswd(password);
            Assertions.assertTrue(mainPage.isLoggedIn(), "Логин не удался");
        }
    }

    @Test
    public void loginTest() {
        loginOnce(ConfProperties.getProperty("login"), ConfProperties.getProperty("password"));
        String username = mainPage.getName();
        Assertions.assertEquals(ConfProperties.getProperty("username"), username, "Имена пользователей не совпадают");
    }


    @Test
    public void incorrectPasswordTest() {
        loginOnce(ConfProperties.getProperty("login"), ConfProperties.getProperty("incorrect_password"));
        Assertions.assertTrue(loginPage.isIncorrectPasswd());
    }

    @Test
    public void logoutTest() {
        loginOnce(ConfProperties.getProperty("login"), ConfProperties.getProperty("password"));
        mainPage.clickLogoutBtn();
        Assertions.assertTrue(loginPage.checkLoginBtn(), "Кнопка входа не отображается после выхода");
    }

    @AfterAll
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
