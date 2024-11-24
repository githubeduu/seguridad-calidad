package com.example.seguridad_calidad.controller;

import com.example.seguridad_calidad.services.RecetaService;
import com.example.seguridad_calidad.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.security.test.context.support.WithMockUser;

@WebMvcTest(HomeController.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecetaService recetaService;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"}) // Simula un usuario autenticado
    public void testHome() throws Exception {
        // Configurar el comportamiento de los servicios mock
        Mockito.when(recetaService.obtenerRecetasPublicas()).thenReturn(Collections.emptyList());

        // Simular sesión con atributos
        mockMvc.perform(get("/home")
                .sessionAttr("username", "testuser")
                .sessionAttr("rolId", 1))
                .andExpect(status().isOk()) // Verificar que la respuesta sea 200 OK
                .andExpect(view().name("Home")) // Verificar que se retorna la vista correcta
                .andExpect(model().attributeExists("recetas")) // Verificar que se añade el atributo "recetas"
                .andExpect(model().attribute("username", "testuser")) // Verificar que "username" está presente y es "testuser"
                .andExpect(model().attribute("rolId", 1)); // Verificar que "rolId" está presente y es 1
    }
}
