package com.example.seguridad_calidad.html;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class LoginPageTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        // Initialize the ChromeDriver (make sure to set the correct path to your chromedriver executable)
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\seba\\Desktop\\chromedriver-win64\\chromedriver.exe");
    
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testLoginPage() {
        // Open the login page
        driver.get("http://localhost:8080/login"); // Replace with your login page URL

        // Check if the page has loaded and contains the correct header
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        assertEquals("Inicio de Sesión", header.getText());

        // Check if the login form is present
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Iniciar Sesión')]"));

        assertNotNull(usernameField);
        assertNotNull(passwordField);
        assertNotNull(loginButton);

        // Fill out the form with valid credentials (replace these with actual test credentials)
        usernameField.sendKeys("testuser");
        passwordField.sendKeys("password123");

        // Submit the form
        loginButton.click();

        // Wait for a potential redirect or login success
        wait.until(ExpectedConditions.urlContains("/home")); // Assuming a successful login redirects to /home

        // Verify that the user has been redirected to the home page (change to the correct URL)
        assertEquals("http://localhost:8080/home", driver.getCurrentUrl());
    }

    @AfterEach
    public void tearDown() {
        // Close the browser after the test is finished
        if (driver != null) {
            driver.quit();
        }
    }
}
