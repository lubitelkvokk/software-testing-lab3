import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import se.ifmo.WebDriverBuilder;
import se.ifmo.pages.searching.SearchOption;
import se.ifmo.pages.searching.SearchPage;
import se.ifmo.pages.searching.vacancy.VacancyPage;
import se.ifmo.util.DriverRealisation;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SuperJobSearchTest {
    private static final String baseUrl = "https://spb.superjob.ru/";

    private static SearchPage sp;
    private static VacancyPage vp;
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
        vp = new VacancyPage(genericDriver.getDriver(driverRealisation), baseUrl);
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    @Order(1)
    public void testVacancySearch(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        sp.getUrl(baseUrl);
        sp.performSearch(SearchOption.VACANCY, "Разработчик", "Санкт-Петербург");
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    @Order(2)
    public void testCompanySearch(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        sp.getUrl(baseUrl);
        sp.performSearch(SearchOption.COMPANY, "Яндекс", "Москва");
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    @Order(3)
    public void testResumeSearch(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        sp.getUrl(baseUrl);
        sp.performSearch(SearchOption.CV, "Тестировщик", "Новосибирск");
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    @Order(4)
    public void testCourseSearch(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        sp.getUrl(baseUrl);
        sp.performSearch(SearchOption.COURSE, "Java", "Екатеринбург");
    }

    static Stream<Arguments> browser() {
        return Stream.of(
                arguments(DriverRealisation.CHROME),
                arguments(DriverRealisation.FIREFOX)
        );
    }
}
