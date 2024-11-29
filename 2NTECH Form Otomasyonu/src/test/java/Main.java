import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.2ntech.com.tr/hr");
        Thread.sleep(2000);

        driver.findElement(By.id("name")).sendKeys("denemekad"); // veriler deneme amaçlı rastgele girilmiştir.
        driver.findElement(By.id("birth")).sendKeys("01.01.1900");
        driver.findElement(By.id("tcKimlik")).sendKeys("12511111118");
        driver.findElement(By.id("phone")).sendKeys("05415555555");
        driver.findElement(By.id("email")).sendKeys("denemekmail@hotmail.com");
        String filePath = "C:\\Users\\EFE\\Downloads\\Documents\\deneme.pdf";
        WebElement uploadInput = driver.findElement(By.id("cv_field"));
        uploadInput.sendKeys(filePath);
        //Varsayılan öğrenim durumu lisans olarak ayarlanmıştır. Değiştirmek için Önlisans, Yüksek lisans, Doktora veya Lise olarak düzeltilebilir.
        WebElement lisansButton = driver.findElement(By.xpath("//button[text()='Lisans']"));
        lisansButton.click();


        WebElement checkbox = driver.findElement(By.id("pdp1"));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }

        WebElement nextButton = driver.findElement(By.xpath("//button[@type='submit']"));
        nextButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


        WebElement testEngineerSpan = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[text()='Test Engineer']")
        ));
        testEngineerSpan.click();

        WebElement gonderButton = driver.findElement(By.xpath("//div[text()='Gönder']"));
        gonderButton.click();

        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Form Başarı ile gönderildi')]")));
        if (successMessage.isDisplayed()) {
            System.out.println("Form başarılı bir şekilde gönderildi.");
        }
    }
}