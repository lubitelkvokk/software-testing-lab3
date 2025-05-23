import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.ifmo.ConfProperties;
import se.ifmo.pages.searching.SearchPage;
import se.ifmo.pages.searching.course.*;
import se.ifmo.pages.searching.cv.CVPage;
import se.ifmo.pages.searching.cv.EducationType;
import se.ifmo.pages.searching.cv.EmploymentType;
import se.ifmo.pages.searching.cv.SeekerActivity;

import java.time.Duration;

public class CourseSearchTest {

    private static final String baseUrl = "https://spb.superjob.ru/kursy/";
    private static WebDriver driver;
    private static WebDriverWait wait;

    private static CoursePage coursePage;

    @BeforeAll
    public static void setUpBeforeAll() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfProperties.getProperty("duration"))));
        coursePage = new CoursePage(driver, baseUrl);
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
        Assertions.assertTrue(coursePage.CourseIsDisplayed());
    }

    @Test
    public void testCourseWithSpecializationFilter() {
        coursePage.filterBySpecialization(Specialization.ADMINISTRATIVE_WORK);
        Assertions.assertTrue(wait.until(ExpectedConditions.urlContains(Specialization.ADMINISTRATIVE_WORK.getUrlName())));
    }

    @Test
    public void testCourseWithEducationFormatFilter() {
        coursePage.filterByEducationType(EducationFormat.ONLINE);
        Assertions.assertTrue(wait.until(ExpectedConditions.urlContains(EducationFormat.ONLINE.getUrlName())));
        Assertions.assertTrue(coursePage.CourseIsDisplayed());
    }

    @Test
    public void testCourseWithDifficultyLevelFilter() {
        coursePage.filterByDifficultyLevel(DifficultyLevel.START);
        Assertions.assertTrue(wait.until(ExpectedConditions.urlContains(DifficultyLevel.START.getUrlName())));
        Assertions.assertTrue(coursePage.CourseIsDisplayed());
    }

    @Test
    public void testCourseWithCostFilter() {
        coursePage.filterByCost(Cost.LESS_THAN_5000_RUB);
        Assertions.assertTrue(wait.until(ExpectedConditions.urlContains(Cost.LESS_THAN_5000_RUB.getUrlName())));
        Assertions.assertTrue(coursePage.CourseIsDisplayed());
    }
}
