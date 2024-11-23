package com.example.seguridad_calidad.html;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class RecetasPageTest {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Configura el controlador de Selenium
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\seba\\Desktop\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testPageLoad() {
        driver.get("http://localhost:8080/receta/detalle");

        // Verifica que el título se cargue correctamente
        WebElement title = driver.findElement(By.tagName("h1"));
        Assert.assertEquals(title.getText(), "Detalle de la Receta");

        // Verifica que el nombre de la receta sea dinámico
        WebElement recipeName = driver.findElement(By.tagName("h2"));
        Assert.assertFalse(recipeName.getText().isEmpty(), "El nombre de la receta no se cargó correctamente.");
    }

    @Test
    public void testCarrusel() {
        List<WebElement> carouselItems = driver.findElements(By.cssSelector(".carousel-item"));
        if (carouselItems.size() > 0) {
            // Verifica que al menos un elemento esté activo
            boolean isActiveFound = carouselItems.stream().anyMatch(item -> item.getAttribute("class").contains("active"));
            Assert.assertTrue(isActiveFound, "Ningún elemento del carrusel está activo.");
        } else {
            // Verifica mensaje de "No hay medios"
            WebElement noMediaMessage = driver.findElement(By.xpath("//p[contains(text(), 'No hay medios disponibles')]"));
            Assert.assertTrue(noMediaMessage.isDisplayed(), "El mensaje de 'No hay medios disponibles' no apareció.");
        }
    }

    @Test
    public void testUploadModal() {
        WebElement uploadButton = driver.findElement(By.cssSelector("button[data-bs-target='#uploadModal']"));
        uploadButton.click();

        WebElement modal = driver.findElement(By.id("uploadModal"));
        Assert.assertTrue(modal.isDisplayed(), "El modal para subir medios no se abrió.");

        // Verifica el formulario de subida
        WebElement fileInput = driver.findElement(By.id("file"));
        WebElement youtubeInput = driver.findElement(By.id("youtubeUrl"));
        Select tipoSelect = new Select(driver.findElement(By.id("tipo")));

        // Cambia entre opciones y verifica visibilidad
        tipoSelect.selectByValue("youtube");
        Assert.assertTrue(youtubeInput.isDisplayed(), "El campo de enlace de YouTube no se mostró.");
        Assert.assertFalse(fileInput.isDisplayed(), "El campo de archivo no debería mostrarse.");

        tipoSelect.selectByValue("foto");
        Assert.assertTrue(fileInput.isDisplayed(), "El campo de archivo no se mostró para foto.");
    }

    @Test
    public void testComentarioModal() {
        WebElement commentButton = driver.findElement(By.cssSelector("button[data-toggle='modal']"));
        commentButton.click();

        WebElement modal = driver.findElement(By.id("comentarioModal"));
        Assert.assertTrue(modal.isDisplayed(), "El modal de comentarios no se abrió.");

        // Completa el formulario
        WebElement nombreInput = driver.findElement(By.id("usuario"));
        WebElement comentarioInput = driver.findElement(By.id("comentario"));
        Select puntuacionSelect = new Select(driver.findElement(By.id("puntuacion")));

        nombreInput.sendKeys("Usuario de Prueba");
        comentarioInput.sendKeys("¡Me encantó esta receta!");
        puntuacionSelect.selectByValue("5");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Verifica que el modal se cierre (puede requerir mock de backend)
        Assert.assertFalse(modal.isDisplayed(), "El modal no se cerró tras enviar el comentario.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
