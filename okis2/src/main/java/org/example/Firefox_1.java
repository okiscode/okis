package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class Firefox_1 {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.gecko.driver", "C:\\Browser drivers\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            driver.get("https://www.championat.com/");
            String title = driver.getTitle();
            System.out.println("Заголовок сайта: " + title);
            System.out.println("Список названий: ");
            List<WebElement> toc = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".header-bottom__item")));
            for(int i = 0; i < toc.size(); i++){
                WebElement elements = toc.get(i);
                String text = elements.getText().trim();
                if(!text.isEmpty()){
                    System.out.println(text);
                }
            }
            WebElement Match = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='\n" +
                            "                        Матч-центр                    ']")));
            Match.click();
            Thread.sleep(2000);
            WebElement News = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-champ='header_menu::item_3::Новости']")));
            News.click();
        }
        finally {
            driver.quit();
        }
    }
}