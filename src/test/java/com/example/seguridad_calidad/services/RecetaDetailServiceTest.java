package com.example.seguridad_calidad.services;

import com.example.seguridad_calidad.Model.RecetaDetail;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class RecetaDetailServiceTest {

    @Test
    void testGetRecetasDetail() {
        // Mock de RestTemplate
        RestTemplate restTemplate = mock(RestTemplate.class);

        // Crear instancia del servicio con el mock
        RecetaDetailService recetaDetailService = new RecetaDetailService(restTemplate);

        // Datos simulados de respuesta
        RecetaDetail expectedRecetaDetail = new RecetaDetail();
        expectedRecetaDetail.setDetalleId(1);
        expectedRecetaDetail.setIngredientes("Harina, agua, sal");
        expectedRecetaDetail.setInstrucciones("Mezclar y hornear");
        expectedRecetaDetail.setTiempoCoccion("30 minutos");
        expectedRecetaDetail.setDificultad("Fácil");

        // Configurar el mock para devolver la respuesta simulada
        String url = "http://localhost:8087/recetas/1/detalle";
        when(restTemplate.getForObject(eq(url), eq(RecetaDetail.class))).thenReturn(expectedRecetaDetail);

        // Llamar al método del servicio
        RecetaDetail actualRecetaDetail = recetaDetailService.getRecetasDetail(1);

        // Verificar resultados
        assertNotNull(actualRecetaDetail);
        assertEquals(1, actualRecetaDetail.getDetalleId());
        assertEquals("Harina, agua, sal", actualRecetaDetail.getIngredientes());
        assertEquals("Mezclar y hornear", actualRecetaDetail.getInstrucciones());
        assertEquals("30 minutos", actualRecetaDetail.getTiempoCoccion());
        assertEquals("Fácil", actualRecetaDetail.getDificultad());

        // Verificar que RestTemplate fue llamado una vez
        verify(restTemplate, times(1)).getForObject(eq(url), eq(RecetaDetail.class));
    }
}
