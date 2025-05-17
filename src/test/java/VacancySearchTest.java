package se.ifmo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import se.ifmo.searching.SearchOption;
import se.ifmo.searching.SearchPage;
import se.ifmo.searching.vacancy.*;

import java.time.Duration;

public class VacancySearchTest {

    private final String baseUrl = "https://spb.superjob.ru/";
    private WebDriver driver;

    private SearchPage sp;
    private VacancyPage vp;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        sp = new SearchPage(driver, baseUrl);
        vp = new VacancyPage(driver, baseUrl);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testVacancy() {
        sp.performSearch(SearchOption.VACANCY, "Разработчик", "Санкт-Петербург");
    }

    @Test
    public void testVacancyWithSalaryFilter() {
        sp.performSearch(SearchOption.VACANCY, "Разработчик", "Санкт-Петербург");
        vp.filterBySalary();
    }

    @Test
    public void testVacancyWithWorkingRateFilter() {
        sp.performSearch(SearchOption.VACANCY, "Разработчик", null);
        vp.filterByWorkingRate(WorkingRate.MONTH);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void testVacancyWithPublicationPeriodFilter() {
        sp.performSearch(SearchOption.VACANCY, "Разработчик", null);
        vp.filterByPublicationPeriod(PublicationPeriod.LAST_24H);
        Assertions.assertTrue(vp.vacancyIsDisplayed());
        String firstVacancyTime = vp.firstVacancyTime();
        Assertions.assertTrue(firstVacancyTime.contains("Сегодня") ||
                firstVacancyTime.contains("Вчера"));
    }

    @Test
    public void testVacancyWithEmploymentTypeFilter() {
        sp.performSearch(SearchOption.VACANCY, "Разработчик", null);
        vp.filterByEmploymentType(EmploymentType.WATCH);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void testVacancyWithVacancyTypeFilter() {
        sp.performSearch(SearchOption.VACANCY, "Разработчик", null);
        vp.filterByVacancyType(VacancyType.NO_EXPERIENCE_REQUIRED);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void testVacancyWithAdditionalParamsFilter() {
        sp.performSearch(SearchOption.VACANCY, "Разработчик", null);
        vp.filterByAdditionalParams(AdditionalParams.AVAILABLE_FOR_STUDENTS);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
        }
    }
}
