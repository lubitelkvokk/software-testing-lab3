package se.ifmo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import se.ifmo.ConfProperties;
import se.ifmo.util.CookiesHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private final String baseUrl = "https://spb.superjob.ru/";
    private String loginFromUrl;


    public LoginPage(WebDriver driver, String baseUrl) {
        loginFromUrl = baseUrl;
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfProperties.getProperty("duration"))));
    }

    public void confirmFieldButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'f-test-button-Prodolzhit')]"))).click();
    }

    public void inputLogin(String login) {
        WebElement loginField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'f-test-input-login')]")));
        loginField.clear();
        loginField.sendKeys(login);
        confirmFieldButton();
    }

    public void inputPasswd(String passwd) {
        WebElement passwrdField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'f-test-input-password')]")));
        passwrdField.sendKeys(passwd);
        confirmFieldButton();
    }

    public boolean isIncorrectPasswd() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'f-test-info-block-title')]"))).isDisplayed();
    }

    public void clickLoginBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'f-test-button-Voĭti')]/.."))).click();
    }

    public boolean checkLoginBtn() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'f-test-button-Voĭti')]/.."))).isDisplayed();
    }

    public void loginOnce(String login, String password) {
        if (checkLoginBtn()) {
            clickLoginBtn();
            inputLogin(login);
            inputPasswd(password);
        }
    }

    public void loginAndSaveCookie(String login, String password) {
        driver.get(loginFromUrl);
        loginOnce(login, password);
        CookiesHelper.saveCookies(driver);
    }

    // При логине - сохраняем куки в файл (проверка на логин осуществляется по содержимому в файле)
    // Logout выходит и удаляет содержимое в файле
}