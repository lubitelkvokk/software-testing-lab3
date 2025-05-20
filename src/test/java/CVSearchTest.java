import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import se.ifmo.pages.searching.SearchPage;
import se.ifmo.pages.searching.cv.CVPage;
import se.ifmo.pages.searching.cv.EducationType;
import se.ifmo.pages.searching.cv.EmploymentType;
import se.ifmo.pages.searching.cv.SeekerActivity;

import java.time.Duration;

public class CVSearchTest {

    private static final String baseUrl = "https://spb.superjob.ru/resume/search_resume.html";
    private static WebDriver driver;

    private static SearchPage sp;
    private static CVPage cvPage;

    @BeforeAll
    public static void setUpBeforeAll() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        sp = new SearchPage(driver, baseUrl);
        cvPage = new CVPage(driver, baseUrl);
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
        Assertions.assertTrue(cvPage.CVIsDisplayed());
    }

    @Test
    public void testCVWithSalaryFilter() {
        cvPage.filterBySalary();
        Assertions.assertTrue(cvPage.CVIsDisplayed());
    }

    @Test
    public void testCVWithPublicationPeriodFilter() {
        cvPage.filterBySeekerActivity(SeekerActivity.LAST_24H);
        Assertions.assertTrue(cvPage.CVIsDisplayed());
    }

    @Test
    public void testCVWithEmploymentTypeFilter() {
        cvPage.filterByEmploymentType(EmploymentType.NON_DEFINE);
        Assertions.assertTrue(cvPage.CVIsDisplayed());
    }

    @Test
    public void testCVWithEmploymentType() {
        cvPage.filterByEducationType(EducationType.INCOMPLETE_HIGHER);
        Assertions.assertTrue(cvPage.CVIsDisplayed());
    }
}
