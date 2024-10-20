package com.example.seguridad_calidad.services;

import org.springframework.stereotype.Service;

import com.example.seguridad_calidad.Model.RecetaDetail;

import java.util.List;
import java.util.ArrayList;

@Service
public class RecetaDetailService {
    private final List<RecetaDetail> recetaDetail = new ArrayList<>();

    public RecetaDetailService() {

        recetaDetail.add(
                new RecetaDetail("Receta 1", "imagen1.jpg", "Ingredientes 1", "Instrucciones 1",
                 "30 min", "Fácil"));
        recetaDetail.add(
                new RecetaDetail("Receta 2", "imagen2.jpg", "Ingredientes 2", "Instrucciones 2", "45 min", "Media"));
        recetaDetail.add(
                new RecetaDetail("Receta 3", "imagen3.jpg", "Ingredientes 3", "Instrucciones 3", "60 min", "Difícil"));

    }

    public RecetaDetail getRecetasDetail(String nombre) {
        System.out.println("Buscando receta con nombre: " + nombre);
        for (RecetaDetail receta : recetaDetail) {
            if (receta.getNombre().equals(nombre)) {
                return receta;  // Retorna la receta encontrada
            }
        }
        return null;  // Retorna null si no se encuentra
    }

}
