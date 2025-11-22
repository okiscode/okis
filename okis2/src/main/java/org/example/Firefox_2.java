package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class Firefox_2 {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.gecko.driver", "C:\\Browser drivers\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            driver.get("https://lzt.market/");
            Thread.sleep(5000);
            String title = driver.getTitle();
            System.out.println("Заголовок сайта: " + title);
            System.out.println("Список названий: ");
            List<WebElement> toc = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".marketSidebar")));
            for(int i = 0; i < toc.size(); i++){
                WebElement elements = toc.get(i);
                String text = elements.getText().trim();
                if(!text.isEmpty()){
                    System.out.println(text);
                }
            }
            WebElement currency = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='currency']")));
            currency.click();
            Thread.sleep(2000);
            WebElement close = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".OverlayCloser")));
            close.click();
            Thread.sleep(2000);
            WebElement Guides = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='\n" +
                    "\n" +
                    "\t\t\t\t\tСоветы и гайды\n" +
                    "\t\t\t\t']")));
            Guides.click();
            Thread.sleep(2000);
        }
        finally {
            driver.quit();
        }
    }
}
