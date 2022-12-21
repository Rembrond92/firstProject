package ru.deliveryClub;

import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WebDriverSettings {

    public static ChromeDriver driver;
    public static WebDriverWait wait;
    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver_108.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        //System.out.println("Test start!");
    }

    //Дождаться элемента и кликнуть по нему
    public static void click(By path) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(path));
            driver.findElement(path).click();
            //Если клик по элементу вызывает ошибку, кликаем по его родителю
        } catch (ElementClickInterceptedException exception) {
            driver.findElement(path).findElement(By.xpath(".//..")).click();
        }
    }

    //Дождаться элемента и проверить его текст
    public static void checkText(String text, By path) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(path));
        Assert.assertEquals(text, driver.findElement(path).getText());
    }

    //Дождаться поисковую строку и ввести текст
    public static void inputText(String text, By path) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(path));
        WebElement element = driver.findElement(path);
        element.sendKeys(text);
        element.sendKeys(Keys.ENTER);
    }



/*
    @After
    public void close() {
        driver.quit();
        //System.out.println("Test close!");
    }


 */
}
