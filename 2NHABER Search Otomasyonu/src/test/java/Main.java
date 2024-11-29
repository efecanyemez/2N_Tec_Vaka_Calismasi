import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://2nhaber.com");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".elementor-widget-cmsmasters-search__popup-trigger-inner"))).click();

        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[placeholder=\"Keşfet...\"]")));
        searchInput.sendKeys("İstanbul");
        searchInput.sendKeys(Keys.RETURN);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//article")));

        int index = 8; // Arama sonuçlarında kaçıncı sıradaki haberi istiyorsanız burayı değiştirin.

        String articleTitle = "";
        try {
            WebElement linkElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h3[@class='entry-title cmsmasters-widget-title__heading']/a)[" + index + "]")));
            articleTitle = linkElement.getText(); // Başlığı al
        } catch (Exception e) {
            System.out.println("Hata: Başlık alınamadı. Detay: " + e.getMessage());
        }

        System.out.println("Arama Sonucundaki Başlık: " + articleTitle);

        WebElement linkElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h3[@class='entry-title cmsmasters-widget-title__heading']/a)[" + index + "]")));
        String href = linkElement.getAttribute("href");
        driver.get(href);

        WebElement detailTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(@class, 'entry-title')]")));
        String detailPageTitle = detailTitle.getText();
        System.out.println("Detay Sayfasındaki Başlık: " + detailPageTitle);

        if (normalizeText(articleTitle).equals(normalizeText(detailPageTitle))) {
            System.out.println("Kanıtlama Başarılı: Doğru habere gidildi.");
        } else {
            System.out.println("Kanıtlama Hatası: Farklı bir habere gidildi.");
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500);");

        Thread.sleep(5000);
        driver.quit();
    }

    private static String normalizeText(String text) {
        return text.trim().replaceAll("\\s+", " ").toLowerCase();
    }
}
