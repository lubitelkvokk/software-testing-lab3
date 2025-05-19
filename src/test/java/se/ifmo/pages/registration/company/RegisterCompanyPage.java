package se.ifmo.pages.registration.company;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterCompanyPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public RegisterCompanyPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    public void openMainPage() {
        driver.get("https://spb.superjob.ru/");
    }

    private void clickButton(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    private void fillField(By locator, String value) {
        WebElement field = driver.findElement(locator);
        field.clear();
        field.sendKeys(value);
    }

    public void clickLoginButton() {
        clickButton(By.xpath("//*[contains(@class, 'f-test-button-Voĭti')]"));
    }

    public void fillLoginForm(String email, String phone) {
        clickButton(By.xpath("//*[contains(@class, 'f-test-button-Ischu_sotrudnikov')]"));
        fillField(By.name("login"), email);
        clickContinueButton();
        fillField(By.xpath("//*[contains(@class, 'f-test-input-contacts.phone')]"), phone);
    }

    public void fillCompanyDetails(String title, String inn, String website, String description) {
        fillField(By.xpath("//*[contains(@name, 'companyDetail.inn')]"), inn);
        clickContinueButton();
        fillField(By.xpath("//input[@name='title']"), description);
        fillField(By.xpath("//textarea[@name='description']"), description);
        fillField(By.name("website"), website);
    }

    public void fillHRDetails(String firstName, String lastName, String position) {
        driver.findElement(By.name("hr.firstName")).sendKeys(firstName);
        driver.findElement(By.name("hr.lastName")).sendKeys(lastName);
        driver.findElement(By.name("hr.position")).sendKeys(position);
    }

    public void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void clickContinueButton() {
        scrollToElement(By.xpath("//*[contains(@class, 'f-test-button-Prodolzhit')]"));
        clickButton(By.xpath("//*[contains(@class, 'f-test-button-Prodolzhit')]"));
    }

    public void submitForm() {
        driver.findElement(By.cssSelector(".\\_9gypz")).click();
    }

    public void registerAs(CompanyType ct) {
        if (ct == null) return;
        clickButton(By.xpath("//*[contains(@class, '" + ct + "')]"));
    }

    public boolean isRegistrationSuccessful() {
        try {
            return driver.findElement(By.xpath("//*[contains(text(), 'Спасибо за регистрацию!')]")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    public void createVacancy(String position, ExperienceVar experienceVar) {
        clickButton(By.xpath("//*[contains(@class, 'f-test-button-Novaya_vakansiya')]"));
        fillField(By.xpath("//*[contains(@class, 'f-test-input-mainInfo.profession')]"), position);

        fillField(By.xpath("//*[contains(@class, 'f-test-input-salary_left-range-value')]"), position);
        fillField(By.xpath("//*[contains(@class, 'f-test-input-salary_right-range-value')]"), position);

//        scrollToElement(By.xpath("//*[contains(@class, '" + experienceVar + "')]"));
//        clickButton(By.xpath("//*[contains(@class, '" + experienceVar + "')]"));


        clickButton(By.xpath("//*[contains(@class, 'f-test-button-Sohranit_kak_neopublikovannuyu')]"));
    }

    public void goToPersonalAccount() {
        clickButton(By.xpath("//*[contains(@class, 'f-test-button-Perejti_v_Lichnyj_kabinet')]"));
    }

    public boolean isVacancyPublicationSuccessfull() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'f-test-button-Posmotret_glazami_soiskatelya')]"))).isDisplayed();
    }
}
