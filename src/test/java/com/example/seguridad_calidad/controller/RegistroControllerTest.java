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
import com.example.seguridad_calidad.config.ThymeleafMockConfig;

import com.example.seguridad_calidad.security.TestSecurityConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistroController.class)
@Import({TestSecurityConfig.class, ThymeleafMockConfig.class})
public class RegistroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @Test
        public void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/registro"))  
                .andExpect(status().isOk())             
                .andExpect(view().name("registro"));
        }


        @Test
        public void testRegisterUser_Success() throws Exception {
            Mockito.when(restTemplate.postForEntity(
                    Mockito.anyString(),
                    Mockito.any(),
                    Mockito.<Class<String>>any()
            )).thenReturn(new ResponseEntity<>("Usuario creado exitosamente", HttpStatus.CREATED));
        
            mockMvc.perform(post("/registro")
                            .param("username", "uniqueuser123")
                            .param("password", "12345")
                            .param("nombre", "Test User")
                            .param("rut", "987654321")
                            .param("direccion", "Test Address")
                            .param("comuna", "Test Comuna")
                            .param("rol", "1"))
                            .andExpect(status().isOk());
        }
        



        @Test
        public void testRegisterUser_Failure() throws Exception {
        // Configurar el mock para simular un error en el backend
        Mockito.when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(),
                Mockito.<Class<String>>any()
        )).thenThrow(new org.springframework.web.client.HttpClientErrorException(HttpStatus.CONFLICT));

        // Simular el formulario con datos no válidos y verificar el comportamiento
        mockMvc.perform(post("/registro")
                        .param("username", "userduoc")
                        .param("password", "12345")
                        .param("nombre", "Test User")
                        .param("rut", "111111111")
                        .param("direccion", "Test Address")
                        .param("comuna", "Test Comuna")
                        .param("rolId", "1"))
                .andExpect(view().name("registro"))
                .andExpect(model().attributeExists("error"));
        }

        @Test
        public void testRegisterUser_Conflict() throws Exception {
            Mockito.when(restTemplate.postForEntity(
                    Mockito.anyString(),
                    Mockito.any(),
                    Mockito.<Class<String>>any()
            )).thenThrow(new org.springframework.web.client.HttpClientErrorException(HttpStatus.CONFLICT));
        
            mockMvc.perform(post("/registro")
                            .param("username", "existinguser")
                            .param("password", "12345")
                            .param("nombre", "Test User")
                            .param("rut", "11111111-1")
                            .param("direccion", "Test Address")
                            .param("comuna", "Test Comuna"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("registro"))
                    .andExpect(model().attributeExists("error"));
        }

        @Test
        public void testRegisterUser_OtherHttpError() throws Exception {
        Mockito.when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(),
                Mockito.<Class<String>>any()
        )).thenThrow(new org.springframework.web.client.HttpClientErrorException(HttpStatus.BAD_REQUEST));

        mockMvc.perform(post("/registro")
                        .param("username", "baduser")
                        .param("password", "12345")
                        .param("nombre", "Test User")
                        .param("rut", "22222222-2")
                        .param("direccion", "Test Address")
                        .param("comuna", "Test Comuna"))
                .andExpect(status().isOk())
                .andExpect(view().name("registro"))
                .andExpect(model().attributeExists("error"));
        }


        @Test
        public void testRegisterUser_UnexpectedError() throws Exception {
        Mockito.when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(),
                Mockito.<Class<String>>any()
        )).thenThrow(new RuntimeException("Database connection error"));

        mockMvc.perform(post("/registro")
                        .param("username", "unexpectederror")
                        .param("password", "12345")
                        .param("nombre", "Test User")
                        .param("rut", "33333333-3")
                        .param("direccion", "Test Address")
                        .param("comuna", "Test Comuna"))
                .andExpect(status().isOk())
                .andExpect(view().name("registro"))
                .andExpect(model().attributeExists("error"));
        }




}
