import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class basicBrowserTest {

    @Test
    public void runBasicTest() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.youtube.com");
        System.out.println("Page Title is: " + driver.getTitle());
        Thread.sleep(3000); // Short wait to verify browser display

        driver.quit();
    }
}