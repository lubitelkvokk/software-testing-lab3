package se.ifmo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.ifmo.ConfProperties;

import java.time.Duration;


public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;
    public static String baseUrl = "https://spb.superjob.ru/";


    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfProperties.getProperty("duration"))));
    }


    @FindBy(xpath = "/html/body/div[1]/div/div/div[1]/div[3]/div[1]/div[3]/div/div/div[3]/div/div/div[2]/div[1]/div/div/div[1]/div/div/div[2]/div/span")
    private WebElement nameField;


    private void fillField(By locator, String value) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        field.clear();
        field.sendKeys(value);
    }

    private void clickButton(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    public String getName() {
        clickAvatar();
        return nameField.getText();
    }

    public void clickAvatar() {
        clickButton(By.xpath("//*[contains(@class, 'f-test-tooltip-Nastrojki_Vyjti')]"));
    }

    public void clickLogoutBtn() {
        clickButton(By.xpath("//*[contains(@class, 'f-test-button-Vyjti')]"));
    }

    public void postResume(){
        clickButton(By.xpath("//*[contains(@class, 'f-test-button-Razmestit_rezyume')]"));
    }

}
