import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import se.ifmo.WebDriverBuilder;
import se.ifmo.pages.searching.SearchPage;
import se.ifmo.pages.searching.vacancy.*;
import se.ifmo.util.DriverRealisation;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class VacancySearchTest {
    // early russia.superjob
    private static final String baseUrl = "https://spb.superjob.ru/vakansii/razrabotchik.html";

    private  SearchPage sp;
    private  VacancyPage vp;
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
    public void testVacancy(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        sp.getUrl(baseUrl);

        Assertions.assertTrue(vp.isUrlContainsVacancies());
        Assertions.assertTrue(vp.vacancyIsDisplayed());
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testVacancyWithSalaryFilter(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        sp.getUrl(baseUrl);
        vp.filterBySalary();
        Assertions.assertTrue(vp.isUrlContainsSalaryConditions());
        Assertions.assertTrue(vp.vacancyIsDisplayed());
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testVacancyWithWorkingRateFilter(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        sp.getUrl(baseUrl);
        vp.filterByWorkingRate(WorkingRate.MONTH);
        Assertions.assertTrue(vp.isUrlContainsWorkingRateConditions());
        Assertions.assertTrue(vp.vacancyIsDisplayed());
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testVacancyWithPublicationPeriodFilter(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        sp.getUrl(baseUrl);
        vp.filterByPublicationPeriod(PublicationPeriod.LAST_24H);
        Assertions.assertTrue(vp.isUrlContainsPublicationPeriodConditions());
        Assertions.assertTrue(vp.vacancyIsDisplayed());
        String firstVacancyTime = vp.firstVacancyTime();
        Assertions.assertTrue(firstVacancyTime.contains("Сегодня") ||
                firstVacancyTime.contains("Вчера"));
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testVacancyWithEmploymentTypeFilter(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        sp.getUrl(baseUrl);
        vp.filterByEmploymentType(EmploymentType.FULL);

        Assertions.assertTrue(vp.isUrlContainsEmploymentTypeConditions());
        Assertions.assertTrue(vp.vacancyIsDisplayed());
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testVacancyWithVacancyTypeFilter(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        sp.getUrl(baseUrl);
        vp.filterByVacancyType(VacancyType.NO_EXPERIENCE_REQUIRED);
        Assertions.assertTrue(vp.isUrlContainsVacancyType());
        Assertions.assertTrue(vp.vacancyIsDisplayed());
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testVacancyWithAdditionalParamsFilter(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        sp.getUrl(baseUrl);
        vp.filterByAdditionalParams(AdditionalParams.AVAILABLE_FOR_STUDENTS);

        Assertions.assertTrue(vp.isUrlContainsAdditionalParams());
        Assertions.assertTrue(vp.vacancyIsDisplayed());
    }

    static Stream<Arguments> browser() {
        return Stream.of(
                arguments(DriverRealisation.CHROME),
                arguments(DriverRealisation.FIREFOX)
        );
    }
}
