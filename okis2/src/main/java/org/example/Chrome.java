package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class Chrome {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            driver.get("https://testng.org");
            wait.until(ExpectedConditions.titleContains("TestNG"));
            String title = driver.getTitle();
            System.out.println("Заголовок сайта: " + title);
            System.out.println("Список названий: ");
            List<WebElement> toc = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".toc-link")));
            for(int i = 0; i < toc.size(); i++){
                WebElement elements = toc.get(i);
                String text = elements.getText().trim();
                if(!text.isEmpty()){
                    System.out.println(text);
                }
            }
            WebElement Download = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='#_download']")));
            Download.click();
            Thread.sleep(2000);
            WebElement Documentation = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='3. TestNG Documentation']")));
            Documentation.click();
        } finally {
            driver.quit();
        }
    }
}