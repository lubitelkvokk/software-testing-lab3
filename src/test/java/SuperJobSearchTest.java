import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SuperJobSearchTest {
    private WebDriver driver;
    private final String baseUrl = "https://spb.superjob.ru/";

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    private void performSearch(String searchType, String keyword, String location) {
        driver.get(baseUrl);

        selectSearchType(searchType);

        WebElement searchInput = driver.findElement(By.xpath("//input[contains(@class, 'f-test-input-keywords')]"));
        searchInput.clear();
        searchInput.sendKeys(keyword);



        // Выбор местоположения
        if (location != null && !location.isEmpty()) {
            selectLocation(location);
        }

        // Нажатие кнопки поиска
        WebElement searchButton = driver.findElement(By.xpath("//*[contains(@class,'f-test-button-Najti')]"));
        searchButton.click();
    }

    private void selectSearchType(String searchType) {
        WebElement dropdown = driver.findElement(By.xpath("//*[contains(@class,'f-test-select-selected')]"));
        dropdown.click();

        List<WebElement> options = driver.findElements(By.xpath("//div[contains(@class,'f-test-select-option')]"));
        for (WebElement option : options) {
            if (option.getText().equalsIgnoreCase(searchType)) {
                option.click();
                break;
            }
        }
    }

    private void selectLocation(String location) {
        WebElement locationButton = driver.findElement(By.xpath("//*[contains(@class,'f-test-clickable-')]"));
        locationButton.click();

        WebElement clearButton = driver.findElement(By.xpath("//*[contains(@class,'f-test-button-Ochistit')]"));
        clearButton.click();

        List<WebElement> locations = driver.findElements(By.xpath("//div[contains(@class,'f-test-checkable-geo')]"));
        for (WebElement loc : locations) {
            if (loc.getText().equalsIgnoreCase(location)) {
                loc.click();
                break;
            }
        }

        WebElement applyButton = driver.findElement(By.xpath("//*[contains(@class,'f-test-button-Primenit')]"));
        applyButton.click();
    }

    @Test
    public void testVacancySearch() {
        performSearch("Вакансии", "Разработчик", "Санкт-Петербург");
    }

    @Test
    public void testCompanySearch() {
        performSearch("Компании", "Яндекс", "Москва");
    }

    @Test
    public void testResumeSearch() {
        performSearch("Резюме", "Тестировщик", "Новосибирск");
    }

    @Test
    public void testCourseSearch() {
        performSearch("Курсы", "Java", "Екатеринбург");
    }
}
