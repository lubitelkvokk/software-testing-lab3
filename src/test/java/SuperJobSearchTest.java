import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import se.ifmo.pages.searching.SearchOption;
import se.ifmo.pages.searching.SearchPage;
import se.ifmo.pages.searching.vacancy.VacancyPage;

import java.time.Duration;

public class SuperJobSearchTest {
    private static final String baseUrl = "https://spb.superjob.ru/";
    private static WebDriver driver;

    private static SearchPage sp;
    private static VacancyPage vp;


    @BeforeEach
    public void setUp(){
        driver.get(baseUrl);
    }

    @AfterAll
    public static void tearDownAfterAll() {
        driver.quit();
    }

    @Test
    public void testVacancySearch() {
        sp.performSearch(SearchOption.VACANCY, "Разработчик", "Санкт-Петербург");
        vp.filterBySalary();
    }

    @Test
    public void testCompanySearch() {
        sp.performSearch(SearchOption.COMPANY, "Яндекс", "Москва");
    }

    @Test
    public void testResumeSearch() {
        sp.performSearch(SearchOption.CV, "Тестировщик", "Новосибирск");
    }

    @Test
    public void testCourseSearch() {
        sp.performSearch(SearchOption.COURSE, "Java", "Екатеринбург");
    }
}
