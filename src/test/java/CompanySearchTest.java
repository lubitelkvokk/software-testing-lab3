import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import se.ifmo.SingletonWebDriver;
import se.ifmo.pages.searching.SearchPage;
import se.ifmo.pages.searching.company.CompanyPage;
import se.ifmo.util.DriverRealisation;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CompanySearchTest {

    private static final String baseUrl = "https://spb.superjob.ru/clients/";

    private CompanyPage cp;

    @AfterAll
    public static void tearDown() {
        SingletonWebDriver.clearDrivers();
    }

    public void browser_setup(DriverRealisation driverRealisation) {
        cp = new CompanyPage(driverRealisation, baseUrl);
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testCV(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        cp.get(baseUrl);
        Assertions.assertTrue(cp.CompanyIsDisplayed());
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testCompanyWithAttractiveEmployment(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        cp.get(baseUrl);

        cp.chooseCompanyWithAttractiveEmployment();
        Assertions.assertTrue(cp.CompanyIsDisplayed());
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testCompanyWithOpenEmployer(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        cp.get(baseUrl);
        cp.chooseCompanyWithOpenEmployer();
        Assertions.assertTrue(cp.CompanyIsDisplayed());
    }

    @ParameterizedTest(name = "Browser: {0}")
    @MethodSource("browser")
    public void testCompanyWithOpenVacancies(DriverRealisation driverRealisation) {
        browser_setup(driverRealisation);
        cp.get(baseUrl);
        cp.chooseCompanyWithOpenVacancies();
        Assertions.assertTrue(cp.CompanyIsDisplayed());
    }

    static Stream<Arguments> browser() {
        return Stream.of(
                arguments(DriverRealisation.CHROME),
                arguments(DriverRealisation.FIREFOX)
        );
    }
}
