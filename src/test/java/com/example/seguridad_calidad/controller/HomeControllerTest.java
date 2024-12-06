package com.example.seguridad_calidad.controller;

import com.example.seguridad_calidad.config.ThymeleafMockConfig;
import com.example.seguridad_calidad.security.TestSecurityConfig;
import com.example.seguridad_calidad.services.RecetaService;
import com.example.seguridad_calidad.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.security.test.context.support.WithMockUser;

@WebMvcTest(HomeController.class)
@Import({TestSecurityConfig.class, ThymeleafMockConfig.class})
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


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Simula un usuario autenticado con rol de administrador
    public void testEliminarUsuario_SinToken() throws Exception {
        mockMvc.perform(post("/eliminar-usuario")
                .param("id", "361")) // Enviar un ID de prueba
                .andExpect(status().is3xxRedirection()) // Verificar redirección
                .andExpect(redirectedUrl("/mantenedor-usuarios")) // Verificar la URL de redirección
                .andExpect(flash().attribute("error", "No estás autenticado")); // Verificar el mensaje de error
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Simula un usuario autenticado con rol de administrador
    public void testEliminarUsuario_Exito() throws Exception {
        // Mockear el comportamiento del servicio para eliminar un usuario
        Mockito.doNothing().when(usuarioService).eliminarUsuario(Mockito.eq(361L), Mockito.anyString());

        mockMvc.perform(post("/eliminar-usuario")
                .param("id", "361") // ID del usuario a eliminar
                .sessionAttr("token", "test-token")) // Simular sesión con un token
                .andExpect(status().is3xxRedirection()) // Verificar redirección
                .andExpect(redirectedUrl("/mantenedor-usuarios")) // Verificar la URL de redirección
                .andExpect(flash().attribute("success", "Usuario eliminado exitosamente")); // Verificar el mensaje de éxito
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Simula un usuario autenticado con rol de administrador
    public void testEliminarUsuario_Error() throws Exception {
        // Mockear el comportamiento del servicio para lanzar una excepción
        Mockito.doThrow(new RuntimeException("Error al eliminar usuario"))
            .when(usuarioService).eliminarUsuario(Mockito.eq(361L), Mockito.anyString());

        mockMvc.perform(post("/eliminar-usuario")
                .param("id", "361") // ID del usuario a eliminar
                .sessionAttr("token", "test-token")) // Simular sesión con un token
                .andExpect(status().is3xxRedirection()) // Verificar redirección
                .andExpect(redirectedUrl("/mantenedor-usuarios")) // Verificar la URL de redirección
                .andExpect(flash().attribute("error", "Error al eliminar el usuario: Error al eliminar usuario")); // Verificar el mensaje de error
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Simula un usuario autenticado con rol de administrador
    public void testListarUsuarios() throws Exception {
        // Configurar el comportamiento del servicio mock
        List<Map<String, Object>> usuariosMock = Collections.singletonList(
                Map.of("id", 1L, "username", "testuser", "rol", "ADMIN")
        );
        Mockito.when(usuarioService.obtenerUsuarios()).thenReturn(usuariosMock);

        // Realizar la solicitud GET y verificar la respuesta
        mockMvc.perform(get("/mantenedor-usuarios"))
                .andExpect(status().isOk()) // Verificar que la respuesta sea 200 OK
                .andExpect(view().name("mantenedor-usuarios")) // Verificar que se retorna la vista correcta
                .andExpect(model().attributeExists("usuarios")) // Verificar que se añade el atributo "usuarios"
                .andExpect(model().attribute("usuarios", usuariosMock)); // Verificar que "usuarios" tiene el valor esperado
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Simula un usuario autenticado con rol de administrador
    public void testActualizarUsuario_Success() throws Exception {
        // Simula el comportamiento exitoso del servicio
        Mockito.doNothing().when(usuarioService).actualizarUsuario(
                Mockito.eq(1L),
                Mockito.eq("Duocuc#2610"),
                Mockito.eq("Nueva Direccion"),
                Mockito.eq("Nueva Comuna"),
                Mockito.eq(2)
        );

        mockMvc.perform(post("/actualizar-usuario")
                        .param("id", "1")
                        .param("direccion", "Nueva Direccion")
                        .param("comuna", "Nueva Comuna")
                        .param("rolId", "2"))
                .andExpect(status().is3xxRedirection()) // Verifica que redirige
                .andExpect(redirectedUrl("/mantenedor-usuarios")) // Verifica la URL de redirección
                .andExpect(flash().attribute("success", "Usuario actualizado exitosamente")); // Verifica el mensaje de éxito
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Simula un usuario autenticado con rol de administrador
    public void testActualizarUsuario_Error() throws Exception {
        // Simula un error en el servicio
        Mockito.doThrow(new RuntimeException("Error al actualizar el usuario")).when(usuarioService).actualizarUsuario(
                Mockito.eq(1L),
                Mockito.eq("Duocuc#2610"),
                Mockito.eq("Nueva Direccion"),
                Mockito.eq("Nueva Comuna"),
                Mockito.eq(2)
        );

        mockMvc.perform(post("/actualizar-usuario")
                        .param("id", "1")
                        .param("direccion", "Nueva Direccion")
                        .param("comuna", "Nueva Comuna")
                        .param("rolId", "2"))
                .andExpect(status().is3xxRedirection()) // Verifica que redirige
                .andExpect(redirectedUrl("/mantenedor-usuarios")) // Verifica la URL de redirección
                .andExpect(flash().attribute("error", "Error al actualizar el usuario: Error al actualizar el usuario")); // Verifica el mensaje de error
    }



}
