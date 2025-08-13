package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DebitCardTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUpp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @Test
    public void debitCardApplication() {
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79507550000");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button")).click();
        WebElement resalt = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        assertTrue(resalt.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", resalt.getText().trim());
    }

    @Test
    public void CheckingErrorMessage() {
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79507550000");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button")).click();
        WebElement resalt = driver.findElement(By.cssSelector(".input__sub"));
        assertTrue(resalt.isDisplayed());
        assertEquals("Поле обязательно для заполнения", resalt.getText());
    }
}
