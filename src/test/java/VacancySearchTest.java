import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.ifmo.ConfProperties;
import se.ifmo.pages.searching.SearchOption;
import se.ifmo.pages.searching.SearchPage;
import se.ifmo.pages.searching.vacancy.*;

import java.time.Duration;

public class VacancySearchTest {
    // early russia.superjob
    private static final String baseUrl = "https://spb.superjob.ru/vakansii/razrabotchik.html";
    private static WebDriver driver;
    private static WebDriverWait wait;

    private static SearchPage sp;
    private static VacancyPage vp;

    @BeforeAll
    public static void setUpBeforeAll() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfProperties.getProperty("duration"))));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        sp = new SearchPage(driver, baseUrl);
        vp = new VacancyPage(driver, baseUrl);

    }

    @BeforeEach
    public void setUp(){
        driver.get(baseUrl);
    }

    @AfterAll
    public static void tearDownAfterAll() {
        driver.quit();
    }

    @Test
    public void testVacancy() {
//        sp.performSearch(SearchOption.VACANCY, "Разработчик", "Санкт-Петербург");
        Assertions.assertTrue(wait.until(ExpectedConditions.urlContains("vakansii/razrabotchik")));
        Assertions.assertTrue(vp.vacancyIsDisplayed());
    }

    @Test
    public void testVacancyWithSalaryFilter() {
//        sp.performSearch(SearchOption.VACANCY, "Разработчик", null);
        vp.filterBySalary();
        Assertions.assertTrue(wait.until(ExpectedConditions.urlContains("payment_value")));
        Assertions.assertTrue(wait.until(ExpectedConditions.urlContains("payment_defined=1")));
        Assertions.assertTrue(vp.vacancyIsDisplayed());
    }

    @Test
    public void testVacancyWithWorkingRateFilter() {
//        sp.performSearch(SearchOption.VACANCY, "Разработчик", null);
        vp.filterByWorkingRate(WorkingRate.MONTH);
        Assertions.assertTrue(wait.until(ExpectedConditions.urlContains("paymentPeriod")));
        Assertions.assertTrue(vp.vacancyIsDisplayed());
    }

    @Test
    public void testVacancyWithPublicationPeriodFilter() {
//        sp.performSearch(SearchOption.VACANCY, "Разработчик", null);
        vp.filterByPublicationPeriod(PublicationPeriod.LAST_24H);
        Assertions.assertTrue(wait.until(ExpectedConditions.urlContains(PublicationPeriod.LAST_24H.getUrlName())));
        Assertions.assertTrue(vp.vacancyIsDisplayed());
        String firstVacancyTime = vp.firstVacancyTime();
        Assertions.assertTrue(firstVacancyTime.contains("Сегодня") ||
                firstVacancyTime.contains("Вчера"));
    }

    @Test
    public void testVacancyWithEmploymentTypeFilter() {
//        sp.performSearch(SearchOption.VACANCY, "Разработчик", null);
        vp.filterByEmploymentType(EmploymentType.WATCH);
        Assertions.assertTrue(wait.until(ExpectedConditions.urlContains(EmploymentType.WATCH.getUrlName())));
        Assertions.assertTrue(vp.vacancyIsDisplayed());
    }

    @Test
    public void testVacancyWithVacancyTypeFilter() {
//        sp.performSearch(SearchOption.VACANCY, "Разработчик", null);
        vp.filterByVacancyType(VacancyType.NO_EXPERIENCE_REQUIRED);
        Assertions.assertTrue(wait.until(ExpectedConditions.urlContains(VacancyType.NO_EXPERIENCE_REQUIRED.getUrlName())));
        Assertions.assertTrue(vp.vacancyIsDisplayed());
    }

    @Test
    public void testVacancyWithAdditionalParamsFilter() {
//        sp.performSearch(SearchOption.VACANCY, "Разработчик", null);
        vp.filterByAdditionalParams(AdditionalParams.AVAILABLE_FOR_STUDENTS);
        System.out.println(driver.getCurrentUrl());
        Assertions.assertTrue(wait.until(ExpectedConditions.urlContains(AdditionalParams.AVAILABLE_FOR_STUDENTS.getUrlName())));
        Assertions.assertTrue(vp.vacancyIsDisplayed());
    }
}
