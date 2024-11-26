package com.example.seguridad_calidad.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.seguridad_calidad.security.TestSecurityConfig;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
@Import(TestSecurityConfig.class) // Deshabilitar seguridad para los tests
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @Test
        public void testLogin_Success() throws Exception {
        // Configurar el mock para devolver una respuesta exitosa
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("token", "test-token");
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("nombre", "Test User");
        userMap.put("rolId", 1);
        responseBody.put("user", userMap);

        Mockito.when(restTemplate.postForEntity(
                Mockito.eq("http://localhost:8085/auth/login"),
                Mockito.any(),
                Mockito.<Class<Map<String, Object>>>any()
        )).thenReturn(new ResponseEntity<>(responseBody, HttpStatus.OK));

        // Ejecutar la solicitud POST al endpoint /login
        mockMvc.perform(post("/login")
                        .param("username", "userduoc")
                        .param("password", "12345"))
                .andExpect(status().is3xxRedirection()) // Validar que es una redirección (302)
                .andExpect(redirectedUrl("/home")); // Validar que redirige a "/home"
        }


    @Test
        public void testLogin_Failure() throws Exception {
        // Configurar el mock para devolver una excepción
        Mockito.when(restTemplate.postForEntity(
                Mockito.eq("http://localhost:8085/auth/login"),
                Mockito.any(),
                Mockito.<Class<Map<String, Object>>>any()
        )).thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas"));

        // Ejecutar la solicitud POST al endpoint /login
        mockMvc.perform(post("/login")
                        .param("username", "testuser")
                        .param("password", "wrongpassword"))
                .andExpect(status().isOk()) // Validar que el estado es 200 OK
                .andExpect(view().name("login")) // Validar que se renderiza la vista "login"
                .andExpect(model().attributeExists("error")) // Validar que se pasa el atributo "error"
                .andExpect(model().attribute("error", "Credenciales incorrectas")); // Validar el mensaje de error
        }

}
