package com.example.seguridad_calidad.Model;

import com.example.seguridad_calidad.DTO.ComentarioResponseDto;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RecetaTest {

    @Test
    void testConstructor() {
        // Configurar datos de prueba
        Long recetaId = 1L;
        String nombre = "Receta de prueba";
        String categoria = "Postres";
        String ingredientes = "Azúcar, harina, huevos";
        String origen = "Chile";
        String dificultad = "Fácil";
        Date fechaCreacion = new Date();
        Map<String, List<String>> media = new HashMap<>();
        media.put("imagenes", Arrays.asList("imagen1.jpg", "imagen2.jpg"));
        List<ComentarioResponseDto> comentarios = Collections.singletonList(new ComentarioResponseDto("usuario1", "Excelente receta", 5));

        // Crear instancia usando el constructor
        Receta receta = new Receta(recetaId, nombre, categoria, ingredientes, origen, dificultad, fechaCreacion, media, comentarios);

        // Verificar valores iniciales
        assertEquals(recetaId, receta.getRecetaId());
        assertEquals(nombre, receta.getNombre());
        assertEquals(categoria, receta.getCategoria());
        assertEquals(ingredientes, receta.getIngredientes());
        assertEquals(origen, receta.getOrigen());
        assertEquals(dificultad, receta.getDificultad());
        assertEquals(fechaCreacion, receta.getFechaCreacion());
        assertEquals(media, receta.getMedia());
        assertEquals(comentarios, receta.getComentarios());
    }

    @Test
    void testGettersAndSetters() {
        // Crear instancia vacía
        Receta receta = new Receta(null, null, null, null, null, null, null, null, null);

        // Configurar valores usando setters
        receta.setRecetaId(2L);
        receta.setNombre("Nueva receta");
        receta.setCategoria("Entradas");
        receta.setIngredientes("Verduras, sal");
        receta.setOrigen("Argentina");
        receta.setDificultad("Media");
        Date nuevaFecha = new Date();
        receta.setFechaCreacion(nuevaFecha);
        Map<String, List<String>> nuevaMedia = new HashMap<>();
        nuevaMedia.put("videos", Arrays.asList("video1.mp4"));
        receta.setMedia(nuevaMedia);
        List<ComentarioResponseDto> nuevosComentarios = Arrays.asList(new ComentarioResponseDto("usuario2", "Buena receta", 4));
        receta.setComentarios(nuevosComentarios);

        // Verificar valores con getters
        assertEquals(2L, receta.getRecetaId());
        assertEquals("Nueva receta", receta.getNombre());
        assertEquals("Entradas", receta.getCategoria());
        assertEquals("Verduras, sal", receta.getIngredientes());
        assertEquals("Argentina", receta.getOrigen());
        assertEquals("Media", receta.getDificultad());
        assertEquals(nuevaFecha, receta.getFechaCreacion());
        assertEquals(nuevaMedia, receta.getMedia());
        assertEquals(nuevosComentarios, receta.getComentarios());
    }
}
