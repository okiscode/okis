package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SearchResultsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By highRateButton = By.xpath("//div[@class='border___X13bZ']");
    private By JewelryButton = By.xpath("//span[contains(text(), 'Украшения и бижутерия')]");
    private By WeddingButton = By.xpath("//span[contains(text(), 'Украшения на свадьбу и помолвку')]");
    private By WeddingRingsButton = By.xpath("//span[contains(text(), 'Свадебные кольца')]");
    private By DiscardFilterButton = By.xpath("//a[contains(text(), 'Сбросить фильтры')]");
    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    public SearchResultsPage sortJewelryButton(){
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(JewelryButton));
        button.click();
        return  this;
    }
    public SearchResultsPage sortWeddingButton(){
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(WeddingButton));
        button.click();
        return  this;
    }
    public SearchResultsPage sortWeddingRingsButton(){
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(WeddingRingsButton));
        button.click();
        return  this;
    }

    public SearchResultsPage sortByRating() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(highRateButton));
        button.click();
        return this;
    }
    public SearchResultsPage sortDiscardFilterButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(DiscardFilterButton));
        button.click();
        return this;
    }

}