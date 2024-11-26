package com.example.seguridad_calidad.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioService = new UsuarioService(restTemplate);
    }

    @Test
    void testObtenerUsuarios_Exitoso() {
        // Datos simulados
        Map<String, Object> usuario = Map.of("id", 1, "nombre", "Usuario Test");
        Map<String, Object> embedded = Map.of("usuarioList", List.of(usuario));
        Map<String, Object> responseBody = Map.of("_embedded", embedded);

        ResponseEntity<Map> responseEntity = ResponseEntity.ok(responseBody);

        when(restTemplate.getForEntity(eq("http://localhost:8082/usuario"), eq(Map.class)))
            .thenReturn(responseEntity);

        // Ejecutar el método
        List<Map<String, Object>> usuarios = usuarioService.obtenerUsuarios();

        // Validar resultados
        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
        assertEquals("Usuario Test", usuarios.get(0).get("nombre"));

        // Verificar interacciones
        verify(restTemplate, times(1)).getForEntity(eq("http://localhost:8082/usuario"), eq(Map.class));
    }

    @Test
    void testEliminarUsuario_Exitoso() {
        Long userId = 1L;
        String token = "test-token";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Void> responseEntity = ResponseEntity.noContent().build();

        when(restTemplate.exchange(
            eq("http://localhost:8082/usuario/" + userId),
            eq(HttpMethod.DELETE),
            eq(requestEntity),
            eq(Void.class)
        )).thenReturn(responseEntity);

        // Ejecutar el método
        assertDoesNotThrow(() -> usuarioService.eliminarUsuario(userId, token));

        // Verificar interacciones
        verify(restTemplate, times(1)).exchange(
            eq("http://localhost:8082/usuario/" + userId),
            eq(HttpMethod.DELETE),
            eq(requestEntity),
            eq(Void.class)
        );
    }

    @Test
    void testEliminarUsuario_Error() {
        Long userId = 1L;
        String token = "test-token";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.NOT_FOUND, "Usuario no encontrado");

        when(restTemplate.exchange(
            eq("http://localhost:8082/usuario/" + userId),
            eq(HttpMethod.DELETE),
            eq(requestEntity),
            eq(Void.class)
        )).thenThrow(exception);

        // Ejecutar el método
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> usuarioService.eliminarUsuario(userId, token));

        // Validar mensaje de error
        assertTrue(thrown.getMessage().contains("Error al eliminar el usuario"));

        // Verificar interacciones
        verify(restTemplate, times(1)).exchange(
            eq("http://localhost:8082/usuario/" + userId),
            eq(HttpMethod.DELETE),
            eq(requestEntity),
            eq(Void.class)
        );
    }

    @Test
    void testActualizarUsuario_Exitoso() {
        Long userId = 1L;
        String contrasena = "123456";
        String direccion = "Calle Falsa 123";
        String comuna = "Springfield";
        int rolId = 2;

        Map<String, Object> requestBody = Map.of(
            "contrasena", contrasena,
            "direccion", direccion,
            "comuna", comuna,
            "rolId", rolId
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Void> responseEntity = ResponseEntity.ok().build();

        when(restTemplate.exchange(
            eq("http://localhost:8082/usuario/" + userId),
            eq(HttpMethod.PUT),
            eq(requestEntity),
            eq(Void.class)
        )).thenReturn(responseEntity);

        // Ejecutar el método
        assertDoesNotThrow(() -> usuarioService.actualizarUsuario(userId, contrasena, direccion, comuna, rolId));

        // Verificar interacciones
        verify(restTemplate, times(1)).exchange(
            eq("http://localhost:8082/usuario/" + userId),
            eq(HttpMethod.PUT),
            eq(requestEntity),
            eq(Void.class)
        );
    }

    @Test
    void testActualizarUsuario_Error() {
        Long userId = 1L;
        String contrasena = "123456";
        String direccion = "Calle Falsa 123";
        String comuna = "Springfield";
        int rolId = 2;

        Map<String, Object> requestBody = Map.of(
            "contrasena", contrasena,
            "direccion", direccion,
            "comuna", comuna,
            "rolId", rolId
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        when(restTemplate.exchange(
            eq("http://localhost:8082/usuario/" + userId),
            eq(HttpMethod.PUT),
            eq(requestEntity),
            eq(Void.class)
        )).thenThrow(new RuntimeException("Error al actualizar el usuario"));

        // Ejecutar el método
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> usuarioService.actualizarUsuario(userId, contrasena, direccion, comuna, rolId));

        // Validar mensaje de error
        assertTrue(thrown.getMessage().contains("Error al actualizar el usuario"));

        // Verificar interacciones
        verify(restTemplate, times(1)).exchange(
            eq("http://localhost:8082/usuario/" + userId),
            eq(HttpMethod.PUT),
            eq(requestEntity),
            eq(Void.class)
        );
    }
}
