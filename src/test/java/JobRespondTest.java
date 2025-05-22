import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import se.ifmo.pages.LoginPage;
import se.ifmo.pages.searching.SearchPage;
import se.ifmo.pages.searching.respond.RespondPage;
import se.ifmo.pages.searching.vacancy.VacancyPage;
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

    @AfterAll
    public static void tearDownAfterAll() {
        driver.quit();
    }

    @Test
    public void respondToVacancyWithoutAccount() {
//        vp.goToFirstJobLink();

        rp.respondWithoutAccount();
        Assertions.assertTrue(rp.respondIsSent());
    }

//    @Test
    // TODO COOKIES NOT WORKING
//    public void respondToVacancyWithAccount() {
//        CookiesHelper.loadCookies(driver);
//        driver.navigate().refresh();

//        vp.goToFirstJobLink();

//        rp.respondWithAccount();
//        Assertions.assertTrue(rp.respondIsSent());
//    }
}
