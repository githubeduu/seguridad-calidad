package com.example.seguridad_calidad.services;

import com.example.seguridad_calidad.DTO.ComentarioDto;
import com.example.seguridad_calidad.Model.Receta;
import com.example.seguridad_calidad.DTO.ComentarioResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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

}
