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
import org.springframework.web.client.RestTemplate;

import com.example.seguridad_calidad.security.JwtAuthenticationFilter;
import com.example.seguridad_calidad.security.TestSecurityConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistroController.class)
@Import(TestSecurityConfig.class)
public class RegistroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean // Mock del filtro para deshabilitarlo en los tests
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
        public void testRegisterUser_Success() throws Exception {
        // Configurar el mock para devolver una respuesta exitosa
        Mockito.when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(),
                Mockito.<Class<String>>any()
        )).thenReturn(new ResponseEntity<>("Usuario creado exitosamente", HttpStatus.CREATED));

        // Ejecutar la solicitud y validar que retorna código 200
        mockMvc.perform(post("/registro")
                        .param("username", "testuser")
                        .param("password", "12345")
                        .param("nombre", "Test User")
                        .param("rut", "12345678-9")
                        .param("direccion", "Test Address")
                        .param("comuna", "Test Comuna"))
                .andExpect(status().isOk()); // Validar que el estado es 200 (SUCCESSFUL)
        }



        @Test
        public void testRegisterUser_Failure() throws Exception {
        // Configurar el comportamiento del mock para devolver una excepción
        Mockito.when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(),
                Mockito.<Class<String>>any()
        )).thenThrow(new org.springframework.web.client.HttpClientErrorException(HttpStatus.BAD_REQUEST));

        // Ejecutar la solicitud sin validar los detalles de la respuesta
        mockMvc.perform(post("/registro")
                        .param("username", "testuser")
                        .param("password", "12345")
                        .param("nombre", "Test User")
                        .param("rut", "12345678-9")
                        .param("direccion", "Test Address")
                        .param("comuna", "Test Comuna"))
                .andExpect(status().isOk()); // Solo validar que el estado sea 200 OK
        }       


}
