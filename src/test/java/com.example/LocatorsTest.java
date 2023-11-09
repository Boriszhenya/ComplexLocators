package com.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocatorsTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Получаем последнюю версию драйвера браузера Chrome
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        Map<String, Object> profile = new HashMap<String, Object>();
        Map<String, Object> contentSettings = new HashMap<String, Object>();

        //contentSettings.put("cookies",2);
        profile.put("managed_default_content_settings",contentSettings);
        prefs.put("profile",profile);
        options.setExperimentalOption("prefs",prefs);

        // Создаём новый объект класса ChromeDriver
        driver = new ChromeDriver(options);

        String baseUrl = "https://selectorshub.com/xpath-practice-page/";
        // Открываем главную страницу демо-сайта
        driver.get(baseUrl);
        // Открываем бразуер на полный экран
        driver.manage().window().maximize();

        // Ждём пока страница появится - этот способ подходит только для демонстрации
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testComplexLocators() {
        //Выберите дату начала наших курсов в календаре, чтобы проверка прошла
        WebElement dataInput = driver.findElement(By.xpath("//input[@type='date']"));
        dataInput.sendKeys("06062023");

        assertEquals("2023-06-06", driver.findElement(By.xpath("/html/body/div[1]/main/div/div[1]/section[2]/div/div[2]/div/div/div/input[2]")).getAttribute("value"));

        //После этого переходим к таблице
        WebElement elementH2 = driver.findElement(By.xpath("//h2[.='System Distribution Details']"));
        new Actions(driver).scrollToElement(elementH2).perform();

        WebElement showEntries = driver.findElement(By.xpath("//select[@name='tablepress-1_length']"));
        showEntries.sendKeys("25");

        WebElement checkBox = driver.findElement(By.xpath("/html/body/div[1]/main/div/div[1]/section[3]/div/div/div/div[3]/div/div/div/table/tbody/tr[22]/td[1]/input"));

        //с этим локатором не хочет работать, хотя если его ввести в на сайте то нуэный элемент находит ;(
        //WebElement checkBox = driver.findElement(By.xpath("//input[@control-id='ControlID-52']"));
        checkBox.click();


        //Нужно переключить количество строк в таблице на любое другое значение кроме 10
        //И нажать чекбокс на первой серой строке с данными "windows	Edge	Samsun	India" чтобы проверка прошла

        assertTrue(driver.findElement(By.xpath("/html/body/div[1]/main/div/div[1]/section[3]/div/div/div/div[3]/div/div/div/table/tbody/tr[22]/td[1]/input")).isSelected());
    }

    @AfterEach
    public void tearDown() {
        // Закрываем браузер
        driver.quit();
    }
}
