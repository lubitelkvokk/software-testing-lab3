import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import se.ifmo.WebDriverBuilder;
import se.ifmo.pages.searching.course.*;
import se.ifmo.util.DriverRealisation;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CourseSearchTest {

    private static final String baseUrl = "https://spb.superjob.ru/kursy/";
    private  CoursePage coursePage;

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
        coursePage = new CoursePage(genericDriver.getDriver(driverRealisation), baseUrl);
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testCV(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        coursePage.getUrl(baseUrl);

        Assertions.assertTrue(coursePage.CourseIsDisplayed());
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testCourseWithSpecializationFilter(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        coursePage.getUrl(baseUrl);

        coursePage.filterBySpecialization(Specialization.ADMINISTRATIVE_WORK);
        Assertions.assertTrue(coursePage.isUrlContainsSpecialization(Specialization.ADMINISTRATIVE_WORK));
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testCourseWithEducationFormatFilter(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        coursePage.getUrl(baseUrl);

        coursePage.filterByEducationType(EducationFormat.ONLINE);
        Assertions.assertTrue(coursePage.isUrlContainsEducationFormat(EducationFormat.ONLINE));
        Assertions.assertTrue(coursePage.CourseIsDisplayed());
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testCourseWithDifficultyLevelFilter(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        coursePage.getUrl(baseUrl);

        coursePage.filterByDifficultyLevel(DifficultyLevel.START);
        Assertions.assertTrue(coursePage.isUrlContainsDifficultyLevel(DifficultyLevel.START));
        Assertions.assertTrue(coursePage.CourseIsDisplayed());
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testCourseWithCostFilter(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        coursePage.getUrl(baseUrl);

        coursePage.filterByCost(Cost.LESS_THAN_5000_RUB);
        Assertions.assertTrue(coursePage.isUrlContainsCost(Cost.LESS_THAN_5000_RUB));
        Assertions.assertTrue(coursePage.CourseIsDisplayed());
    }

    static Stream<Arguments> browser() {
        return Stream.of(
                arguments(DriverRealisation.CHROME),
                arguments(DriverRealisation.FIREFOX)
        );
    }
}
