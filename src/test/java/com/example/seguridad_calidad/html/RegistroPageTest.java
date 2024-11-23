package com.example.seguridad_calidad.html;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
public class RegistroPageTest {
    



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
    public void testRegistrationPage() {
        // Open the registration page
        driver.get("http://localhost:8080/registro"); // Replace with your registration page URL

        // Check if the page has loaded and contains the correct header
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        assertEquals("Registro de Usuario", header.getText());

        // Check if the registration form is present
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement nombreField = driver.findElement(By.id("nombre"));
        WebElement rutField = driver.findElement(By.id("rut"));
        WebElement direccionField = driver.findElement(By.id("direccion"));
        WebElement comunaField = driver.findElement(By.id("comuna"));
        WebElement registerButton = driver.findElement(By.xpath("//button[contains(text(), 'Registrar')]"));

        assertNotNull(usernameField);
        assertNotNull(passwordField);
        assertNotNull(nombreField);
        assertNotNull(rutField);
        assertNotNull(direccionField);
        assertNotNull(comunaField);
        assertNotNull(registerButton);

        // Fill out the form with test data
        usernameField.sendKeys("newuser");
        passwordField.sendKeys("password123");
        nombreField.sendKeys("Juan Pérez");
        rutField.sendKeys("12345678-9");
        direccionField.sendKeys("Calle Ficticia 123");
        comunaField.sendKeys("Santiago");

        // Submit the form
        registerButton.click();

        // Wait for a potential redirect (e.g., to the login page after successful registration)
        wait.until(ExpectedConditions.urlContains("/login")); // Modify to match the expected URL after registration

        // Verify that the user is redirected to the login page (or login page, adjust as necessary)
        assertEquals("http://localhost:8080/login", driver.getCurrentUrl()); // Modify with your expected URL
    }

    @AfterEach
    public void tearDown() {
        // Close the browser after the test is finished
        if (driver != null) {
            driver.quit();
        }
    }
}
