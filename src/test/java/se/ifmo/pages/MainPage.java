package se.ifmo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.ifmo.ConfProperties;

import java.time.Duration;


public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;


    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfProperties.getProperty("duration"))));
    }

    @FindBy(xpath = "//*[contains(@class, 'f-test-input-login')]")
    private WebElement loginField;

    @FindBy(xpath = "//*[contains(@class, 'f-test-button-Vyjti')]")
    private WebElement logoutBtn;

    @FindBy(xpath = "//*[contains(@class, 'f-test-input-password')]")
    private WebElement passwdField;

    @FindBy(xpath = "//*[contains(@class, 'f-test-tooltip-Nastrojki_Vyjti')]")
    private WebElement avatarBtn;

    @FindBy(xpath = "/html/body/div[1]/div/div/div[1]/div[3]/div[1]/div[3]/div/div/div[3]/div/div/div[2]/div[1]/div/div/div[1]/div/div/div[2]/div/span")
    private WebElement nameField;

    public String getName() {
        avatarBtn.click();
        return nameField.getText();
    }

    public boolean isLoggedIn() {
        return avatarBtn.isDisplayed();
    }

    public void clickAvatar() {
        wait.until(d -> avatarBtn.isDisplayed());
        avatarBtn.click();
    }

    public void clickLogoutBtn() {
        wait.until(d -> logoutBtn.isDisplayed());
        logoutBtn.click();
    }

}
