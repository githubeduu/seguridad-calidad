package com.example.seguridad_calidad.controller;

import com.example.seguridad_calidad.DTO.ComentarioDto;
import com.example.seguridad_calidad.Model.Receta;
import com.example.seguridad_calidad.Model.RecetaDetail;
import com.example.seguridad_calidad.config.ThymeleafMockConfig;
import com.example.seguridad_calidad.security.TestSecurityConfig;
import com.example.seguridad_calidad.services.RecetaDetailService;
import com.example.seguridad_calidad.services.RecetaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecetaController.class)
@Import({TestSecurityConfig.class, ThymeleafMockConfig.class}) // Importa configuraciones necesarias
class RecetaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecetaService recetaService;

    @MockBean
    private RecetaDetailService recetaDetailService;

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"}) // Simula un usuario autenticado
    void testMostrarRecetas() throws Exception {
        Receta recetaMock = new Receta(1L, "Receta Test", "Postres", "Ingredientes", "Chile", "Fácil", null, null, null);
        when(recetaService.obtenerRecetasPublicas()).thenReturn(List.of(recetaMock));

        mockMvc.perform(get("/recetas"))
                .andExpect(status().isOk())
                .andExpect(view().name("recetas"))
                .andExpect(model().attributeExists("recetas"))
                .andExpect(model().attribute("recetas", List.of(recetaMock)));

        verify(recetaService, times(1)).obtenerRecetasPublicas();
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testMostrarDetalleRecetas() throws Exception {
        Receta recetaMock = new Receta(1L, "Receta Test", "Postres", "Ingredientes", "Chile", "Fácil", null, null, null);
        RecetaDetail detalleMock = new RecetaDetail();
        detalleMock.setReceta(recetaMock);
        Map<String, List<String>> mediaMock = Map.of("imagenes", List.of("imagen1.jpg"), "videos", List.of("video1.mp4"));

        when(recetaDetailService.getRecetasDetail(1)).thenReturn(detalleMock);
        when(recetaService.obtenerMediosPorReceta(1)).thenReturn(mediaMock);

        mockMvc.perform(get("/recetas/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recetas"))
                .andExpect(model().attributeExists("recetaDetalle", "media", "facebookUrl", "twitterUrl", "instagramUrl"))
                .andExpect(model().attribute("recetaDetalle", detalleMock))
                .andExpect(model().attribute("media", mediaMock));

        verify(recetaDetailService, times(1)).getRecetasDetail(1);
        verify(recetaService, times(1)).obtenerMediosPorReceta(1);
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testSubirMedia_Exitoso() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "file",                        // Nombre del campo en el formulario
            "testfile.jpg",                // Nombre del archivo
            "image/jpeg",                  // Tipo de contenido
            "test content".getBytes()      // Contenido simulado del archivo
        );

        mockMvc.perform(multipart("/recetas/1/media")
                .file(file)                // Enviar el archivo simulado
                .param("tipo", "foto"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/recetas/1"));

        verify(recetaService, times(1)).subirMedia(eq(1), eq("foto"), any(), isNull());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testAgregarComentario_Exitoso() throws Exception {
        mockMvc.perform(post("/recetas/1/comentarios")
                        .param("usuario", "testuser")
                        .param("comentario", "Muy buena receta")
                        .param("puntuacion", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/recetas/1"));

        verify(recetaService, times(1)).agregarComentario(eq(1), any(ComentarioDto.class));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testAgregarComentario_Error() throws Exception {
        doThrow(new RuntimeException("Error de prueba")).when(recetaService).agregarComentario(eq(1), any(ComentarioDto.class));

        mockMvc.perform(post("/recetas/1/comentarios")
                        .param("usuario", "testuser")
                        .param("comentario", "Muy buena receta")
                        .param("puntuacion", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/recetas/1"));

        verify(recetaService, times(1)).agregarComentario(eq(1), any(ComentarioDto.class));
    }
}
