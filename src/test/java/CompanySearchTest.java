import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import se.ifmo.pages.searching.SearchPage;
import se.ifmo.pages.searching.company.CompanyPage;

import java.time.Duration;

public class CompanySearchTest {

    private static final String baseUrl = "https://spb.superjob.ru/clients/";
    private static WebDriver driver;

    private static SearchPage sp;
    private static CompanyPage cp;

    @BeforeAll
    public static void setUpBeforeAll() {
        driver = new FirefoxDriver();
        sp = new SearchPage(driver, baseUrl);
        cp = new CompanyPage(driver, baseUrl);
    }

    @BeforeEach
    public void setUp() {
        driver.get(baseUrl);
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void testCV() {
        Assertions.assertTrue(cp.CompanyIsDisplayed());
    }

    @Test
    public void testCompanyWithAttractiveEmployment() {
        cp.chooseCompanyWithAttractiveEmployment();
        Assertions.assertTrue(cp.CompanyIsDisplayed());
    }

    @Test
    public void testCompanyWithOpenEmployer() {
        cp.chooseCompanyWithOpenEmployer();
        Assertions.assertTrue(cp.CompanyIsDisplayed());
    }

    @Test
    public void testCompanyWithOpenVacancies() {
        cp.chooseCompanyWithOpenVacancies();
        Assertions.assertTrue(cp.CompanyIsDisplayed());
    }
}
