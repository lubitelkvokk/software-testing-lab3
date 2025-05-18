package se.ifmo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.ifmo.util.CookiesHelper;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private final String baseUrl = "https://spb.superjob.ru/";
    private String loginFromUrl;


    public LoginPage(WebDriver driver, String baseUrl) {
        loginFromUrl = baseUrl;
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @FindBy(xpath = "//*[contains(@class, 'f-test-input-login')]")
    private WebElement loginField;


    @FindBy(xpath = "//*[contains(@class, 'f-test-button-Prodolzhit')]")
    private WebElement confirmFieldButton;

    @FindBy(xpath = "//*[contains(@class, 'f-test-button-Voĭti')]/..")
    private WebElement loginBtn;

    @FindBy(xpath = "//*[contains(@class, 'f-test-input-password')]")
    private WebElement passwdField;

    @FindBy(xpath = "//*[contains(@class, 'f-test-info-block-title')]")
    private WebElement incorrectPasswdField;

    public void clearLogin() {
        loginField.clear();
    }

    public void inputLogin(String login) {
        loginField.clear();
        loginField.sendKeys(login);
        confirmFieldButton.click();
    }

    public void inputPasswd(String passwd) {
        passwdField.sendKeys(passwd);
        confirmFieldButton.click();
    }

    public boolean isIncorrectPasswd() {
        return wait.until(d -> incorrectPasswdField.isDisplayed());
    }

    public void clickLoginBtn() {
        loginBtn.click();
    }

    public boolean checkLoginBtn() {
        return loginBtn.isEnabled();
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