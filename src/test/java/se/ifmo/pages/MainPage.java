package se.ifmo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class MainPage {
    public WebDriver driver;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//*[contains(@class, 'f-test-input-login')]")
    private WebElement loginField;

    @FindBy(xpath = "//*[contains(@class, 'f-test-button-Vyjti')]")
    private WebElement logoutBtn;

    @FindBy(xpath = "//*[contains(@class, 'f-test-input-password')]")
    private WebElement passwdField;

    @FindBy(xpath = "/html/body/div[1]/div/div/div[1]/div[3]/div[1]/div[3]/div/div/div[3]/div/div/div[1]/div")
    private WebElement interactAreaField;

    @FindBy(xpath = "/html/body/div[1]/div/div/div[1]/div[3]/div[1]/div[3]/div/div/div[3]/div/div/div[2]/div[1]/div/div/div[1]/div/div/div[2]/div/span")
    private WebElement nameField;

    public String getName(){
        interactAreaField.click();
        return nameField.getText();
    }

    public boolean isLoggedIn(){
        return interactAreaField.isDisplayed();
    }
    public void clickLogoutBtn() {
        logoutBtn.click();
    }

}
