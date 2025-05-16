package se.ifmo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    public WebDriver driver;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
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
        loginField.sendKeys(login);
        confirmFieldButton.click();
    }

    public void inputPasswd(String passwd) {
        passwdField.sendKeys(passwd);
        confirmFieldButton.click();
    }

    public boolean isIncorrectPasswd(){
        return incorrectPasswdField.isEnabled();
    }

    public void clickLoginBtn() {
        loginBtn.click();
    }
    public boolean checkLoginBtn(){
        return loginBtn.isEnabled();
    }
}