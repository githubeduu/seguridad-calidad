package com.example.seguridad_calidad.html;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MantenedorUsuariosTest {
    private WebDriver driver;

    @BeforeEach
    void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\seba\\Desktop\\chromedriver-win64\\chromedriver.exe");
        
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8080/mantenedor"); // Cambia la URL según tu entorno
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testUserTableRendering() {
        WebElement table = driver.findElement(By.cssSelector(".custom-table"));
        assertTrue(table.isDisplayed(), "La tabla de usuarios debería mostrarse");
        
        // Verificar que al menos un usuario está en la tabla
        WebElement firstRow = table.findElement(By.cssSelector("tbody tr"));
        assertTrue(firstRow.isDisplayed(), "La primera fila de la tabla debería mostrarse");
    }

    @Test
    void testOpenDeleteModal() {
        // Buscar el botón de eliminación
        WebElement deleteButton = driver.findElement(By.cssSelector(".btn-danger"));
        deleteButton.click();

        // Verificar que el modal se abre
        WebElement deleteModal = driver.findElement(By.id("deleteModal"));
        assertTrue(deleteModal.isDisplayed(), "El modal de eliminación debería abrirse");

        // Verificar que el id del usuario se pasa correctamente
        WebElement userIdInput = driver.findElement(By.id("deleteUserId"));
        String userId = userIdInput.getAttribute("value");
        assertTrue(!userId.isEmpty(), "El ID del usuario debería pasarse al modal");
    }

    @Test
    void testOpenUpdateModal() {
        // Buscar el botón de actualización
        WebElement updateButton = driver.findElement(By.cssSelector(".btn-primary"));
        updateButton.click();

        // Verificar que el modal se abre
        WebElement updateModal = driver.findElement(By.id("updateModal"));
        assertTrue(updateModal.isDisplayed(), "El modal de actualización debería abrirse");

        // Verificar que los campos del usuario se llenan correctamente
        WebElement direccionInput = driver.findElement(By.id("updateDireccion"));
        assertEquals("Dirección esperada", direccionInput.getAttribute("value"), "La dirección debería llenarse correctamente");

        WebElement comunaInput = driver.findElement(By.id("updateComuna"));
        assertEquals("Comuna esperada", comunaInput.getAttribute("value"), "La comuna debería llenarse correctamente");
    }
}
