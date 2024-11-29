import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {
            driver.get("https://2nhaber.com");

            Thread.sleep(2000);

            checkAndVerify(driver);
        } catch (Exception e) {
            System.out.println("[HATA] Genel bir hata oluştu: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }


    private static void checkAndVerify(WebDriver driver) throws InterruptedException {
        List<WebElement> navbarLinks = driver.findElements(By.cssSelector("nav a"));

        for (int index = 0; index < navbarLinks.size(); index++) {
            WebElement currentLink = navbarLinks.get(index);

            if (!currentLink.isDisplayed() || !currentLink.isEnabled()) {
                System.out.println("[BİLGİ] Tüm navbar elementleri test edildi.");
                break;
            }

            try {
                System.out.println("[BİLGİ] Navbar öğesi " + (index + 1) + " kontrol ediliyor...");

                currentLink.click();
                Thread.sleep(1000);

                String currentTitle = driver.getTitle();
                System.out.println("[BAŞARILI] Navbar öğesi " + (index + 1) + " başarıyla açıldı. Sayfa başlığı: " + currentTitle);

                driver.navigate().back();
                Thread.sleep(1000);
                navbarLinks = driver.findElements(By.cssSelector("nav a"));
            } catch (Exception e) {
                System.out.println("[BAŞARISIZ] Navbar öğesi " + (index + 1) + " bir hata ile karşılaştı: " + e.getMessage());
                break;
            }
        }
    }
}
