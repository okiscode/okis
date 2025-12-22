package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By searchField = By.cssSelector("input[placeholder='Поиск']");
    private By searchButton = By.xpath("//button[contains(text(), 'Найти')]");
    private By userMenu = By.xpath("//*[normalize-space()='Профиль']");
    private By loginIcon = By.name("bar-user-linear-24");
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public HomePage enterSearchItem(String item) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(searchField));
        input.click();
        input.clear();
        input.sendKeys(item);
        return this;
    }
    public SearchResultsPage clickSearchButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        button.click();
        return new SearchResultsPage(driver);
    }
    public SearchResultsPage searchForItem(String item) {
        enterSearchItem(item);
        return clickSearchButton();
    }
    public LoginPage clickLoginIcon() {
        WebElement icon = wait.until(ExpectedConditions.elementToBeClickable(loginIcon));
        icon.click();
        return new LoginPage(driver);
    }
    public boolean isUserLoggedIn() {
        return !driver.findElements(userMenu).isEmpty();
    }
}