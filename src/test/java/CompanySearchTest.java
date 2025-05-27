import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import se.ifmo.WebDriverBuilder;
import se.ifmo.pages.searching.company.CompanyPage;
import se.ifmo.util.DriverRealisation;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CompanySearchTest {

    private static final String baseUrl = "https://spb.superjob.ru/clients/";

    private CompanyPage cp;

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
        cp = new CompanyPage(genericDriver.getDriver(driverRealisation), baseUrl);
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
