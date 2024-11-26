package com.example.seguridad_calidad.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecetaMediaTest {

    @Test
    void testConstructor() {
        // Crear instancia con el constructor
        RecetaMedia recetaMedia = new RecetaMedia(1L, "foto", "contenidoBase64", "S");

        // Verificar valores iniciales
        assertEquals(1L, recetaMedia.getRecetaId());
        assertEquals("foto", recetaMedia.getTipo());
        assertEquals("contenidoBase64", recetaMedia.getContenido());
        assertEquals("S", recetaMedia.getEsPortada());
    }

    @Test
    void testGettersAndSetters() {
        // Crear instancia vacía
        RecetaMedia recetaMedia = new RecetaMedia(null, null, null, null);

        // Configurar valores usando setters
        recetaMedia.setRecetaId(2L);
        recetaMedia.setTipo("video");
        recetaMedia.setContenido("nuevoContenidoBase64");
        recetaMedia.setEsPortada("N");

        // Verificar valores con getters
        assertEquals(2L, recetaMedia.getRecetaId());
        assertEquals("video", recetaMedia.getTipo());
        assertEquals("nuevoContenidoBase64", recetaMedia.getContenido());
        assertEquals("N", recetaMedia.getEsPortada());
    }
}
