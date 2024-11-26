package com.example.seguridad_calidad.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecetaDetailTest {

    @Test
    void testGettersAndSetters() {
        // Crear instancia vacía
        RecetaDetail recetaDetail = new RecetaDetail();

        // Configurar valores usando setters
        recetaDetail.setDetalleId(1);
        recetaDetail.setIngredientes("Harina, agua, sal");
        recetaDetail.setInstrucciones("Mezclar todo y hornear");
        recetaDetail.setTiempoCoccion("30 minutos");
        recetaDetail.setDificultad("Fácil");

        // Configurar relación con Receta
        Receta receta = new Receta(
                1L, 
                "Pan casero", 
                "Panadería", 
                "Harina, agua, sal", 
                "Chile", 
                "Media", 
                null, 
                null, 
                null
        );
        recetaDetail.setReceta(receta);

        // Verificar valores con getters
        assertEquals(1, recetaDetail.getDetalleId());
        assertEquals("Harina, agua, sal", recetaDetail.getIngredientes());
        assertEquals("Mezclar todo y hornear", recetaDetail.getInstrucciones());
        assertEquals("30 minutos", recetaDetail.getTiempoCoccion());
        assertEquals("Fácil", recetaDetail.getDificultad());
        assertEquals(receta, recetaDetail.getReceta());
    }

    @Test
    void testRecetaRelation() {
        // Crear una instancia de Receta
        Receta receta = new Receta(
                2L,
                "Pizza",
                "Comida rápida",
                "Harina, queso, tomate",
                "Italia",
                "Fácil",
                null,
                null,
                null
        );

        // Crear instancia de RecetaDetail
        RecetaDetail recetaDetail = new RecetaDetail();
        recetaDetail.setReceta(receta);

        // Verificar que la relación se haya configurado correctamente
        assertEquals(receta, recetaDetail.getReceta());
        assertEquals("Pizza", recetaDetail.getReceta().getNombre());
        assertEquals("Comida rápida", recetaDetail.getReceta().getCategoria());
    }
}
