import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import se.ifmo.ConfProperties;
import se.ifmo.pages.LoginPage;
import se.ifmo.searching.SearchPage;
import se.ifmo.searching.respond.RespondPage;
import se.ifmo.searching.vacancy.VacancyPage;
import se.ifmo.util.CookiesHelper;

import java.time.Duration;

public class JobRespondTest {
    private static final String baseUrl = "https://spb.superjob.ru";
    private static final String baseVacancyUrl = "https://spb.superjob.ru/vakansii/razrabotchik.html";
    private static WebDriver driver;

    private static SearchPage sp;
    private static VacancyPage vp;
    private static RespondPage rp;
    private static LoginPage lp;

    @BeforeAll
    public static void setUpBeforeAll() {

//        driver.manage().deleteAllCookies();
//        lp = new LoginPage(driver, baseVacancyUrl);
//        lp.loginAndSaveCookie(ConfProperties.getProperty("email"), ConfProperties.getProperty("password"));
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

    @BeforeEach
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        sp = new SearchPage(driver, baseUrl);
        vp = new VacancyPage(driver, baseUrl);
        rp = new RespondPage(driver, baseUrl);
        driver.manage().window().maximize();

        driver.manage().deleteAllCookies();
        driver.get(baseVacancyUrl);
//        driver.navigate().to(baseVacancyUrl);
    }

    @AfterEach
    public  void tearDownAfterAll() {
        driver.quit();
    }

    @Test
    public void respondToVacancyWithoutAccount() {
        vp.goToFirstJobLink();

        rp.respondWithoutAccount();
        Assertions.assertTrue(rp.respondIsSent());
    }

    @Test
    public void respondToVacancyWithAccount() {
        CookiesHelper.loadCookies(driver);
        driver.navigate().refresh();

        vp.goToFirstJobLink();

        rp.respondWithAccount();
        Assertions.assertTrue(rp.respondIsSent());
    }
}
