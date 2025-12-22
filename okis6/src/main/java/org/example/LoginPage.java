package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By moreVariantsButton = By.xpath("//button[text()='Показать еще варианты']");
    private By emailOption = By.xpath("//a[text()='Эл. почта']");
    private By emailField = By.name("email");
    private By passwordField = By.name("password");
    private By signInButton = By.xpath("//div[@class='caption___L78vz' and text()='Войти']");
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public LoginPage clickMoreVariants() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(moreVariantsButton));
        button.click();
        return this;
    }
    public LoginPage clickEmailOption() {
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(emailOption));
        option.click();
        return this;
    }
    public LoginPage enterEmail(String email) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        input.clear();
        input.sendKeys(email);
        return this;
    }
    public LoginPage enterPassword(String password) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        input.clear();
        input.sendKeys(password);
        return this;
    }
    public HomePage clickSignIn() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(signInButton));
        button.click();
        return new HomePage(driver);
    }
}