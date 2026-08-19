import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeleniumTest {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        // Selenium 4 natively handles ChromeDriver management automatically
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @Test
    @Order(1)
    @DisplayName("Valid Login Test Case")
    public void validLoginTest() {
        // Locate Username using CSS Selector
        WebElement usernameInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']"))
        );
        usernameInput.sendKeys("Admin");

        // Locate Password using Name locator
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("admin123");

        // Locate Login Button using XPath
        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']"))
        );
        loginButton.click();

        // Assertion: Verify URL contains dashboard
        wait.until(ExpectedConditions.urlContains("dashboard"));
        assertTrue(driver.getCurrentUrl().contains("dashboard"), "Dashboard URL should be loaded upon valid login.");
        System.out.println("VALID LOGIN TEST PASSED");
    }

    @Test
    @Order(2)
    @DisplayName("Invalid Login Test Case")
    public void invalidLoginTest() {
        // Locate Username
        WebElement usernameInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']"))
        );
        usernameInput.sendKeys("Admin");

        // Locate Password with incorrect value
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("wrongpass123");

        // Click Login Button
        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']"))
        );
        loginButton.click();

        // Wait for Invalid credentials message and assert using JUnit
        WebElement alertMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(@class, 'oxd-alert-content-text')]"))
        );

        String errorMessage = alertMessage.getText();
        Assertions.assertEquals("Invalid credentials", errorMessage, "Invalid credentials message was not displayed");
        System.out.println("INVALID LOGIN TEST PASSED");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}