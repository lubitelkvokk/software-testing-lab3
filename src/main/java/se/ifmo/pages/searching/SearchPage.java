package se.ifmo.pages.searching;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.ifmo.ConfProperties;
import se.ifmo.util.DriverRealisation;

import java.time.Duration;
import java.util.List;

public class SearchPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private final String baseUrl;

    public SearchPage(WebDriver driver, String baseUrl) {
        this.driver = driver;
        this.baseUrl = baseUrl;

        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfProperties.getProperty("duration"))));
    }

    private void sleep(Long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }
    private void clickButton(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void clickWithRetry(By locator) {
        for (int i = 0; i < 10; i++) {
            try {
                clickButton(locator);
                return;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                System.out.println("One more try to click filter");
            }
        }
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void selectSearchType(SearchOption so) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[contains(@class,'f-test-select-selected')]")));
        dropdown.click();
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='" + so.toString() + "']/div"))
        );
        option.click();
    }


    public void performSearch(SearchOption so, String keyword, String location) {
        selectSearchType(so);

        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[contains(@class, 'f-test-input-key')]")));
        searchInput.clear();
        searchInput.sendKeys(keyword);

        if (location != null && !location.isEmpty()) {
            selectLocation(location);
        }
        sleep(2000L);
        clickWithRetry(By.xpath("//*[contains(@class,'f-test-button-Najti')]"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(@class,'f-test-vacancy-item')]")));
    }

    private void fillTheCity(String location) {
        driver.findElement(By.xpath("//*[contains(@class, 'f-test-input-geo')]")).sendKeys(location);
        sleep(2000L);

        List<WebElement>
                locations = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//*[contains(@class,'f-test-checkable-geo')]"))
        );

        boolean cityFounded = false;
        for (WebElement city : locations) {
            if (city.getText().equalsIgnoreCase(location)) {
                if (!city.isDisplayed()) {
                    break;
                }
                wait.until(ExpectedConditions.elementToBeClickable(city)).click();
                cityFounded = true;
                break;
            }
        }
    }

    private void selectLocation(String location) {
        WebElement locationButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[contains(@class,'f-test-clickable-')]")));
        scrollToElement(locationButton);
        locationButton.click();

        WebElement clearButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[contains(@class,'f-test-button-Ochistit')]")));
        clearButton.click();

        fillTheCity(location);

        WebElement applyButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[contains(@class,'f-test-button-Primenit')]")));
        applyButton.click();
    }

    public void getUrl(String baseUrl) {
        driver.get(baseUrl);
    }
}
