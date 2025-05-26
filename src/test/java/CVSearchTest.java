import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import se.ifmo.SingletonWebDriver;
import se.ifmo.pages.searching.SearchPage;
import se.ifmo.pages.searching.company.CompanyPage;
import se.ifmo.pages.searching.cv.CVPage;
import se.ifmo.pages.searching.cv.EducationType;
import se.ifmo.pages.searching.cv.EmploymentType;
import se.ifmo.pages.searching.cv.SeekerActivity;
import se.ifmo.util.DriverRealisation;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CVSearchTest {

    private static final String baseUrl = "https://spb.superjob.ru/resume/search_resume.html";

    private SearchPage sp;
    private CVPage cvPage;

    @AfterAll
    public static void tearDown() {
        SingletonWebDriver.clearDrivers();
    }

    public void browser_setup(DriverRealisation driverRealisation) {
        sp = new SearchPage(driverRealisation, baseUrl);
        cvPage = new CVPage(driverRealisation, baseUrl);
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testCV(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        sp.getUrl(baseUrl);
        Assertions.assertTrue(cvPage.CVIsDisplayed());
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testCVWithSalaryFilter(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        sp.getUrl(baseUrl);
        cvPage.filterBySalary();
        Assertions.assertTrue(cvPage.CVIsDisplayed());
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testCVWithPublicationPeriodFilter(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        sp.getUrl(baseUrl);
        cvPage.filterBySeekerActivity(SeekerActivity.LAST_24H);
        Assertions.assertTrue(cvPage.CVIsDisplayed());
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testCVWithEmploymentTypeFilter(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        sp.getUrl(baseUrl);
        cvPage.filterByEmploymentType(EmploymentType.NON_DEFINE);
        Assertions.assertTrue(cvPage.CVIsDisplayed());
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testCVWithEducationType(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        sp.getUrl(baseUrl);
        cvPage.filterByEducationType(EducationType.INCOMPLETE_HIGHER);
        Assertions.assertTrue(cvPage.CVIsDisplayed());
    }

    static Stream<Arguments> browser() {
        return Stream.of(
                arguments(DriverRealisation.CHROME),
                arguments(DriverRealisation.FIREFOX)
        );
    }
}
