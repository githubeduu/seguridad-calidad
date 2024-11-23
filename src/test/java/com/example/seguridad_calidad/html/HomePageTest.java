package com.example.seguridad_calidad.html;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePageTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Configura el path del ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\seba\\Desktop\\chromedriver-win64\\chromedriver.exe");
        

        driver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testPageLoad() {
        // Carga el archivo HTML localmente

        try {
            // Navegar al login
            driver.get("http://localhost:8080/login");

            // Ingresar credenciales
            WebElement usernameField = driver.findElement(By.name("username")); // Ajusta el name si es distinto
            WebElement passwordField = driver.findElement(By.name("password"));
            WebElement loginButton = driver.findElement(By.tagName("button"));

            usernameField.sendKeys("testuser");
            passwordField.sendKeys("testpassword");
            loginButton.click();

            // Esperar a que redirija correctamente
            Thread.sleep(2000); // Mejora esto con WebDriverWait si es necesario

            // Acceder a la página de recetas
            driver.get("http://localhost:8080/recetas");

            // Validar contenido
            String pageTitle = driver.getTitle();
            if (pageTitle.equals("Recetas")) {
                System.out.println("Test passed: Página de recetas accesible.");
            } else {
                System.out.println("Test failed: Página de recetas no accesible.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testLoginLinkExists() {
        WebDriver driver = new ChromeDriver();

        // 1. Accede a la página de login
        driver.get("http://localhost:8080/login");

        // 2. Encuentra y completa el formulario de login
        WebElement usernameField = driver.findElement(By.id("username")); // Cambia el id según tu formulario
        WebElement passwordField = driver.findElement(By.id("password")); // Cambia el id según tu formulario
        // Find the button using its text content
        WebElement loginButton = driver.findElement(By.tagName("button"));



        usernameField.sendKeys("usuarioPrueba");
        passwordField.sendKeys("contraseñaPrueba");
        loginButton.click();

        // 3. Espera a que la redirección se complete
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000)); // Aquí se usa Duration
        wait.until(ExpectedConditions.urlContains("/recetas"));

        // 4. Verifica que el enlace de inicio de sesión exista después de la
        // redirección
        WebElement recetasPage = driver.findElement(By.id("recetasPage"));
        assertNotNull(recetasPage);
    }
}
