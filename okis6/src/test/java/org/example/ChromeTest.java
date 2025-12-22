package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class ChromeTest {
    private WebDriver driver;
    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
    }
@Test(groups = "positive")
public void testSuccessfulSearch() {
    HomePage homePage = new HomePage(driver);
    String searchQuery = "Кольцо";
    String encodedQuery = "%D0%9A%D0%BE%D0%BB%D1%8C%D1%86%D0%BE";
    homePage.searchForItem(searchQuery);
    String currentUrl = driver.getCurrentUrl();
    Assert.assertTrue(currentUrl.contains("/search/"));
    Assert.assertTrue(currentUrl.contains("q." + encodedQuery));
    Assert.assertFalse(currentUrl.contains("c."));
}
@Test(groups = "positive")
public void testSearchWithCategoryFilter() {
    HomePage homePage = new HomePage(driver);
    SearchResultsPage resultsPage = homePage.searchForItem("Кольцо");
    resultsPage.sortJewelryButton();
    String currentUrl = driver.getCurrentUrl();
    Assert.assertTrue(currentUrl.contains("c."));
    Assert.assertTrue(currentUrl.contains("q.%D0%9A%D0%BE%D0%BB%D1%8C%D1%86%D0%BE"));
    Assert.assertTrue(currentUrl.matches(".*/search/c\\.\\d+-.*/q\\..*"));
}
@Test(groups = "positive")
public void testCascadeFiltersWithRatingAndReset() throws InterruptedException {
    HomePage homePage = new HomePage(driver);
    SearchResultsPage resultsPage = homePage.searchForItem("Кольцо");
    Thread.sleep(3000);
    resultsPage.sortJewelryButton();
    Thread.sleep(3000);
    resultsPage.sortWeddingButton();
    Thread.sleep(3000);
    resultsPage.sortWeddingRingsButton();
    Thread.sleep(3000);
    resultsPage.sortByRating();
    Thread.sleep(3000);
    String urlWithAllFilters = driver.getCurrentUrl();
    Assert.assertTrue(urlWithAllFilters.contains("c."));
    Assert.assertTrue(urlWithAllFilters.contains("f.highRating.checkbox.on"));
    resultsPage.sortDiscardFilterButton();
    Thread.sleep(3000);
    String urlAfterReset = driver.getCurrentUrl();
    Assert.assertTrue(urlAfterReset.contains("c."));
    Assert.assertFalse(urlAfterReset.contains("f.highRating.checkbox.on"));
    Assert.assertTrue(urlAfterReset.contains("q.%D0%9A%D0%BE%D0%BB%D1%8C%D1%86%D0%BE"));
    Assert.assertTrue(urlAfterReset.matches(".*/search/c\\.\\d+-.*/q\\..*"));
}
@Test(groups = "positive")
public void testResetFilters() {
    HomePage homePage = new HomePage(driver);
    SearchResultsPage resultsPage = homePage.searchForItem("Кольцо");
    resultsPage.sortByRating();
    String urlWithRatingFilter = driver.getCurrentUrl();
    Assert.assertTrue(urlWithRatingFilter.contains("f.highRating.checkbox.on"));
    resultsPage.sortDiscardFilterButton();
    String urlAfterReset = driver.getCurrentUrl();
    Assert.assertNotEquals(urlWithRatingFilter, urlAfterReset);
    Assert.assertFalse(urlAfterReset.contains("f.highRating.checkbox.on"));
    Assert.assertTrue(urlAfterReset.contains("q.%D0%9A%D0%BE%D0%BB%D1%8C%D1%86%D0%BE"));
    Assert.assertTrue(urlAfterReset.matches(".*/search/q\\..*"));
}
@Test(groups = "negative")
public void testEmptySearch() throws InterruptedException {
    HomePage homePage = new HomePage(driver);
    homePage.enterSearchItem("");
    homePage.clickSearchButton();
    Thread.sleep(3000);
    String currentUrl = driver.getCurrentUrl();
    Assert.assertTrue(currentUrl.equals("https://www.joom.ru/ru/search"));
}
@Test(groups = "negative")
public void testSearchWithOnlySpaces() throws InterruptedException {
    HomePage homePage = new HomePage(driver);
    homePage.searchForItem("   ");
    Thread.sleep(3000);
    String currentUrl = driver.getCurrentUrl();
    Thread.sleep(10000);
    boolean isValid = currentUrl.equals("https://www.joom.ru/ru/search/q.");
    Assert.assertTrue(isValid);
}
@Test(groups = "negative")
public void testLoginWithNonExistentEmail() throws InterruptedException {
    HomePage homePage = new HomePage(driver);
    LoginPage loginPage = homePage.clickLoginIcon();
    Thread.sleep(3000);
    loginPage.clickMoreVariants();
    Thread.sleep(2000);
    loginPage.clickEmailOption();
    Thread.sleep(2000);
    loginPage.enterEmail("йыыпафыпывпыпфыпв@test.com");
    Thread.sleep(2000);
    loginPage.clickSignIn();
    Thread.sleep(2000);
    WebElement passwordField = driver.findElement(By.name("password"));
    passwordField.clear();
    passwordField.sendKeys("test123");
    String enteredText = passwordField.getAttribute("value");
    Assert.assertTrue(enteredText.contains("test123"));
}
@Test(groups = "negative")
public void testLoginWithWrongPassword() throws InterruptedException {
    HomePage homePage = new HomePage(driver);
    LoginPage loginPage = homePage.clickLoginIcon();
    Thread.sleep(3000);
    loginPage.clickMoreVariants();
    Thread.sleep(2000);
    loginPage.clickEmailOption();
    Thread.sleep(2000);
    loginPage.enterEmail("testtk9111@gmail.com");
    Thread.sleep(3000);
    loginPage.enterPassword("zbbcbzbbzbxb");
    Thread.sleep(3000);
    loginPage.clickSignIn();
    Thread.sleep(5000);
    HomePage homePageAfterAttempt = new HomePage(driver);
    Assert.assertFalse(homePageAfterAttempt.isUserLoggedIn());
}
@AfterMethod
public void tearDown() {
    driver.quit();
    }
}