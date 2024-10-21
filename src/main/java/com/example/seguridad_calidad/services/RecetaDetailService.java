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
                new RecetaDetail("Pizza Margherita", "/img/pizza.png", "asa, tomate, mozzarella, albahaca", "Instrucciones 1",
                 "30 min", "Fácil"));
        recetaDetail.add(
                new RecetaDetail("Tacos al Pastor", "/img/taco.jpg", "Carne de cerdo, piña, cebolla, cilantro, tortillas", "Instrucciones 2", "45 min", "Media"));
        recetaDetail.add(
                new RecetaDetail("Sushi de Salmón", "/img/sushi.png", "Arroz, salmón fresco, alga nori, wasabi, salsa de soja", "Instrucciones 3", "60 min", "Difícil"));

    }

    public RecetaDetail getRecetasDetail(String nombre) {
        System.out.println("Buscando receta con nombre: " + nombre);
        for (RecetaDetail receta : recetaDetail) {
            if (receta.getNombre().equals(nombre)) {
                return receta;
            }
        }
        return null;
    }

}
