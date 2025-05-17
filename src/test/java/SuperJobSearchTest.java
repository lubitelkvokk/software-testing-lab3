import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import se.ifmo.searching.SearchOption;
import se.ifmo.searching.SearchPage;
import se.ifmo.searching.vacancy.VacancyPage;

import java.time.Duration;

public class SuperJobSearchTest {
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
