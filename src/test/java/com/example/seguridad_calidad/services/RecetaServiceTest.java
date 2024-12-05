package com.example.seguridad_calidad.services;

import com.example.seguridad_calidad.DTO.ComentarioDto;
import com.example.seguridad_calidad.Model.Receta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RecetaServiceTest {

    private RecetaService recetaService;
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        recetaService = new RecetaService(restTemplate);
    }

    @Test
    void testObtenerRecetasPublicas() {
        // Datos simulados
        Receta recetaMock = new Receta(1L, "Receta de prueba", "Postres", "Harina, azúcar", "Chile", "Fácil", null, null, null);
        Receta[] recetasArray = new Receta[]{recetaMock};
        Map<String, List<String>> mediaMock = Map.of("imagenes", List.of("imagen1.jpg"), "videos", List.of("video1.mp4"));
        List<Map<String, Object>> comentariosMock = List.of(Map.of("usuario", "testuser", "comentario", "Muy buena", "puntuacion", 5));

        // Configurar mocks
        when(restTemplate.getForObject(eq("http://localhost:8087/recetas"), eq(Receta[].class))).thenReturn(recetasArray);
        when(restTemplate.getForObject(eq("http://localhost:8087/recetas/1/media"), eq(Map.class))).thenReturn(mediaMock);
        when(restTemplate.exchange(
                eq("http://localhost:8087/recetas/1/comentarios"),
                eq(HttpMethod.GET),
                isNull(),
                eq(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
        )).thenReturn(ResponseEntity.ok(comentariosMock));

        // Ejecutar el método
        List<Receta> recetas = recetaService.obtenerRecetasPublicas();

        // Validar resultados
        assertNotNull(recetas);
        assertEquals(1, recetas.size());
        Receta receta = recetas.get(0);
        assertEquals("Receta de prueba", receta.getNombre());
        assertEquals(mediaMock, receta.getMedia());
        assertEquals(1, receta.getComentarios().size());
        assertEquals("testuser", receta.getComentarios().get(0).getUsuario());

        // Verificar interacciones
        verify(restTemplate, times(1)).getForObject(eq("http://localhost:8087/recetas"), eq(Receta[].class));
        verify(restTemplate, times(1)).getForObject(eq("http://localhost:8087/recetas/1/media"), eq(Map.class));
    }


    @Test
    void testBuscarRecetas() {
        // Datos simulados
        Receta recetaMock = new Receta(1L, "Receta de prueba", "Postres", "Harina, azúcar", "Chile", "Fácil", null, null, null);
        Receta[] recetasArray = new Receta[]{recetaMock};

        // Simular obtenerRecetasPublicas
        when(restTemplate.getForObject(eq("http://localhost:8087/recetas"), eq(Receta[].class))).thenReturn(recetasArray);

        // Ejecutar el método con filtros
        List<Receta> recetas = recetaService.buscarRecetas("Receta", null, null, null, null);

        // Validar resultados
        assertNotNull(recetas);
        assertEquals(1, recetas.size());
        assertEquals("Receta de prueba", recetas.get(0).getNombre());
    }


    @Test
    void testAgregarComentario() {
        // Datos simulados
        ComentarioDto comentarioDto = new ComentarioDto("testuser", "Buenísima receta", 5);
        ResponseEntity<Void> responseEntityMock = ResponseEntity.ok().build();

        // Configurar mocks
        when(restTemplate.postForEntity(
            eq("http://localhost:8087/recetas/1/comentarios"),
            eq(comentarioDto),
            eq(Void.class)
        )).thenReturn(responseEntityMock);

        // Ejecutar el método
        assertDoesNotThrow(() -> recetaService.agregarComentario(1, comentarioDto));

        // Verificar interacciones
        verify(restTemplate, times(1)).postForEntity(
            eq("http://localhost:8087/recetas/1/comentarios"),
            eq(comentarioDto),
            eq(Void.class)
        );
    }

    @Test
    void testObtenerMediosPorReceta() {
        // Datos simulados
        int recetaId = 1;
        Map<String, List<String>> mediosMock = Map.of(
            "imagenes", List.of("http://localhost:8087/recetas/1/media/2/imagen"),
            "videos", List.of(),
            "youtubeLinks", List.of("https://www.youtube.com/embed/Yx6rQ0J0ZaY")
        );

        String url = "http://localhost:8087/recetas/" + recetaId + "/media";

        // Configurar mock
        when(restTemplate.getForObject(eq(url), eq(Map.class))).thenReturn(mediosMock);

        // Ejecutar método
        Map<String, List<String>> medios = recetaService.obtenerMediosPorReceta(recetaId);

        // Validar resultados
        assertNotNull(medios);
        assertTrue(medios.containsKey("imagenes"));
        assertTrue(medios.containsKey("youtubeLinks"));
        assertEquals(1, medios.get("imagenes").size());
        assertEquals(0, medios.get("videos").size());
        assertEquals(1, medios.get("youtubeLinks").size());
        assertEquals("http://localhost:8087/recetas/1/media/2/imagen", medios.get("imagenes").get(0));
        assertEquals("https://www.youtube.com/embed/Yx6rQ0J0ZaY", medios.get("youtubeLinks").get(0));

        // Verificar interacción
        verify(restTemplate, times(1)).getForObject(eq(url), eq(Map.class));
    }


    @Test
    void testSubirMediaConYoutubeUrl() {
        // Datos simulados
        int recetaId = 1;
        String tipo = "youtube";
        String youtubeUrl = "https://www.youtube.com/embed/Yx6rQ0J0ZaY";

        String url = "http://localhost:8087/recetas/" + recetaId + "/media";
        ResponseEntity<String> responseEntityMock = ResponseEntity.ok("URL subida correctamente");

        // Configurar mock
        when(restTemplate.postForEntity(
                eq(url),
                any(HttpEntity.class),
                eq(String.class))
        ).thenReturn(responseEntityMock);

        // Ejecutar método
        String response = recetaService.subirMedia(recetaId, tipo, null, youtubeUrl);

        // Validar resultados
        assertNotNull(response);
        assertEquals("URL subida correctamente", response);

        // Verificar interacciones
        verify(restTemplate, times(1)).postForEntity(eq(url), any(HttpEntity.class), eq(String.class));
    }

    @Test
    void testSubirMediaConArchivo() throws Exception {
        // Datos simulados
        int recetaId = 1;
        String tipo = "foto";
        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenReturn("file-content".getBytes());
        when(file.getOriginalFilename()).thenReturn("imagen.jpg");

        String url = "http://localhost:8087/recetas/" + recetaId + "/media";
        ResponseEntity<String> responseEntityMock = ResponseEntity.ok("Archivo subido correctamente");

        // Configurar mock
        when(restTemplate.postForEntity(
                eq(url),
                any(HttpEntity.class),
                eq(String.class))
        ).thenReturn(responseEntityMock);

        // Ejecutar método
        String response = recetaService.subirMedia(recetaId, tipo, file, null);

        // Validar resultados
        assertNotNull(response);
        assertEquals("Archivo subido correctamente", response);

        // Verificar interacciones
        verify(restTemplate, times(1)).postForEntity(eq(url), any(HttpEntity.class), eq(String.class));
    }

    @Test
    void testSubirMediaConError() throws Exception {
        // Datos simulados
        int recetaId = 1;
        String tipo = "foto";
        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenReturn("file-content".getBytes());
        when(file.getOriginalFilename()).thenReturn("imagen.jpg");

        String url = "http://localhost:8087/recetas/" + recetaId + "/media";

        // Configurar mock para lanzar una excepción
        when(restTemplate.postForEntity(
                eq(url),
                any(HttpEntity.class),
                eq(String.class))
        ).thenThrow(new RuntimeException("Error en el servidor"));

        // Ejecutar y verificar la excepción
        Exception exception = assertThrows(RuntimeException.class, () -> {
            recetaService.subirMedia(recetaId, tipo, file, null);
        });

        assertEquals("Error al subir el archivo o enlace: Error en el servidor", exception.getMessage());

        // Verificar interacción
        verify(restTemplate, times(1)).postForEntity(eq(url), any(HttpEntity.class), eq(String.class));
    }


    @Test
    void testObtenerImagenes() {
        // Datos simulados
        int recetaId = 1;
        String[] imagenesMock = {
            "http://localhost:8087/recetas/1/media/imagen1.jpg",
            "http://localhost:8087/recetas/1/media/imagen2.jpg"
        };

        // Configurar el mock de RestTemplate
        String url = "http://localhost:8087/recetas/" + recetaId + "/imagen";
        when(restTemplate.getForObject(eq(url), eq(String[].class))).thenReturn(imagenesMock);

        // Ejecutar el método
        List<String> imagenes = recetaService.obtenerImagenes(recetaId);

        // Validar resultados
        assertNotNull(imagenes);
        assertEquals(2, imagenes.size());
        assertEquals("http://localhost:8087/recetas/1/media/imagen1.jpg", imagenes.get(0));
        assertEquals("http://localhost:8087/recetas/1/media/imagen2.jpg", imagenes.get(1));

        // Verificar que RestTemplate se llamó correctamente
        verify(restTemplate, times(1)).getForObject(eq(url), eq(String[].class));
    }


    @Test
    void testAgregarComentarioConExcepcion() {
        int recetaId = 1;
        ComentarioDto comentarioDto = new ComentarioDto("user", "Comentario", 5);

        doThrow(new RuntimeException("Error al enviar el comentario"))
            .when(restTemplate).postForEntity(anyString(), eq(comentarioDto), eq(Void.class));

        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> recetaService.agregarComentario(recetaId, comentarioDto)
        );

        assertTrue(exception.getMessage().contains("Error al enviar el comentario"));
        verify(restTemplate, times(1)).postForEntity(anyString(), eq(comentarioDto), eq(Void.class));
    }


    @Test
    void testBuscarRecetasSinFiltros() {
        Receta recetaMock = new Receta(1L, "Receta de prueba", "Postres", "Harina, azúcar", "Chile", "Fácil", null, null, null);
        when(restTemplate.getForObject(eq("http://localhost:8087/recetas"), eq(Receta[].class)))
            .thenReturn(new Receta[]{recetaMock});

        List<Receta> recetas = recetaService.buscarRecetas(null, null, null, null, null);

        assertNotNull(recetas);
        assertEquals(1, recetas.size());
        assertEquals("Receta de prueba", recetas.get(0).getNombre());
    }


    @Test
    void testBuscarRecetasConVariosFiltros() {
        Receta recetaMock = new Receta(1L, "Receta de prueba", "Postres", "Harina, azúcar", "Chile", "Fácil", null, null, null);
        when(restTemplate.getForObject(eq("http://localhost:8087/recetas"), eq(Receta[].class)))
            .thenReturn(new Receta[]{recetaMock});

        List<Receta> recetas = recetaService.buscarRecetas("Receta", "Postres", "Harina", "Chile", "Fácil");

        assertNotNull(recetas);
        assertEquals(1, recetas.size());
        assertEquals("Receta de prueba", recetas.get(0).getNombre());
    }


    @Test
    void testBuscarRecetasSinResultados() {
        Receta recetaMock = new Receta(1L, "Receta de prueba", "Postres", "Harina, azúcar", "Chile", "Fácil", null, null, null);
        when(restTemplate.getForObject(eq("http://localhost:8087/recetas"), eq(Receta[].class)))
            .thenReturn(new Receta[]{recetaMock});

        List<Receta> recetas = recetaService.buscarRecetas("No existe", null, null, null, null);

        assertNotNull(recetas);
        assertTrue(recetas.isEmpty());
    }

    @Test
    void testObtenerImagenesConNulo() {
        int recetaId = 1;

        // Simular respuesta nula
        when(restTemplate.getForObject(eq("http://localhost:8087/recetas/" + recetaId + "/imagen"), eq(String[].class)))
            .thenReturn(null);

        List<String> imagenes = recetaService.obtenerImagenes(recetaId);

        assertNotNull(imagenes);
        assertTrue(imagenes.isEmpty());
    }

}
