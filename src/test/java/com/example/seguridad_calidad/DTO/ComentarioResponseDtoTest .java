package com.example.seguridad_calidad.DTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComentarioResponseDtoTest {

    @Test
    void testConstructor() {
        // Crear instancia con el constructor con parámetros
        ComentarioResponseDto responseDto = new ComentarioResponseDto("user123", "Este es un comentario", 5);

        // Verificar que los valores fueron inicializados correctamente
        assertEquals("user123", responseDto.getUsuario());
        assertEquals("Este es un comentario", responseDto.getComentario());
        assertEquals(5, responseDto.getPuntuacion());
    }

    @Test
    void testGettersAndSetters() {
        // Crear instancia con valores iniciales
        ComentarioResponseDto responseDto = new ComentarioResponseDto("user456", "Comentario inicial", 4);

        // Cambiar valores usando setters
        responseDto.setUsuario("user789");
        responseDto.setComentario("Comentario actualizado");
        responseDto.setPuntuacion(3);

        // Verificar que los valores actualizados son correctos
        assertEquals("user789", responseDto.getUsuario());
        assertEquals("Comentario actualizado", responseDto.getComentario());
        assertEquals(3, responseDto.getPuntuacion());
    }
}
