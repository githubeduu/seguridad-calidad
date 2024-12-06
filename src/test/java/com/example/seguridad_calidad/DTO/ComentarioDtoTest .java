package com.example.seguridad_calidad.DTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComentarioDtoTest {

    @Test
    void testEmptyConstructor() {
        // Crear instancia con el constructor vacío
        ComentarioDto comentario = new ComentarioDto();

        // Verificar valores iniciales
        assertNull(comentario.getUsuario());
        assertNull(comentario.getComentario());
        assertEquals(0, comentario.getPuntuacion());
    }

    @Test
    void testParameterizedConstructor() {
        // Crear instancia con el constructor con parámetros
        ComentarioDto comentario = new ComentarioDto("user123", "Este es un comentario", 5);

        // Verificar valores inicializados
        assertEquals("user123", comentario.getUsuario());
        assertEquals("Este es un comentario", comentario.getComentario());
        assertEquals(5, comentario.getPuntuacion());
    }

    @Test
    void testGettersAndSetters() {
        // Crear instancia
        ComentarioDto comentario = new ComentarioDto();

        // Configurar valores usando setters
        comentario.setUsuario("user456");
        comentario.setComentario("Otro comentario");
        comentario.setPuntuacion(4);

        // Verificar valores con getters
        assertEquals("user456", comentario.getUsuario());
        assertEquals("Otro comentario", comentario.getComentario());
        assertEquals(4, comentario.getPuntuacion());
    }

    @Test
    void testToString() {
        // Crear instancia con valores
        ComentarioDto comentario = new ComentarioDto("user789", "Comentario final", 3);

        // Verificar representación en cadena
        String expected = "ComentarioDto{usuario='user789', comentario='Comentario final', puntuacion=3}";
        assertEquals(expected, comentario.toString());
    }
}
