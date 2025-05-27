import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import se.ifmo.WebDriverBuilder;
import se.ifmo.pages.LoginPage;
import se.ifmo.pages.searching.SearchPage;
import se.ifmo.pages.searching.respond.RespondPage;
import se.ifmo.pages.searching.vacancy.VacancyPage;
import se.ifmo.util.CookiesHelper;
import se.ifmo.util.DriverRealisation;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class JobRespondTest {
    private static final String baseUrl = "https://spb.superjob.ru";
    private static final String baseVacancyUrl = "https://spb.superjob.ru/vakansii/razrabotchik.html";

    private SearchPage sp;
    private RespondPage rp;
    private WebDriverBuilder genericDriver;

    @BeforeEach
    public void setUp() {
        genericDriver = new WebDriverBuilder();
    }

    @AfterEach
    public void tearDown() {
        genericDriver.clearDrivers();
    }

    public void browser_setup(DriverRealisation driverRealisation) {
        sp = new SearchPage(genericDriver.getDriver(driverRealisation), baseUrl);
        rp = new RespondPage(genericDriver.getDriver(driverRealisation), baseUrl);
    }


    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void respondToVacancyWithoutAccount(DriverRealisation driverRealisation) {
//        vp.goToFirstJobLink();
        browser_setup(driverRealisation);

        sp.getUrl(baseVacancyUrl);
        rp.respondWithoutAccount();
        Assertions.assertTrue(rp.respondIsSent());
    }

//    @Test
//   COOKIES NOT WORKING
//    public void respondToVacancyWithAccount() {
//        CookiesHelper.loadCookies(driver);
//        driver.navigate().refresh();

//        vp.goToFirstJobLink();

//        rp.respondWithAccount();
//        Assertions.assertTrue(rp.respondIsSent());
//    }

    static Stream<Arguments> browser() {
        return Stream.of(
                arguments(DriverRealisation.CHROME),
                arguments(DriverRealisation.FIREFOX)
        );
    }
}
