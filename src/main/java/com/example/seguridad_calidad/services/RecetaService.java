package com.example.seguridad_calidad.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.seguridad_calidad.Model.Receta;

@Service
public class RecetaService {

    private final List<Receta> recetas = new ArrayList<>();

    public RecetaService() {
        recetas.add(new Receta("Pizza Margherita", "Italiana", "Masa, tomate, mozzarella, albahaca", "Italia", "Fácil"));
        recetas.add(new Receta("Tacos al Pastor", "Mexicana", "Carne de cerdo, piña, cebolla, cilantro, tortillas", "México", "Media"));
        recetas.add(new Receta("Sushi de Salmón", "Japonesa", "Arroz, salmón fresco, alga nori, wasabi, salsa de soja", "Japón", "Difícil"));
    }

    public List<Receta> obtenerRecetasPublicas() {
        // Retorna la lista de recetas ya llenada
        return new ArrayList<>(recetas);
    }

    public List<Receta> buscarRecetas(String nombre, String tipoCocina, String ingredientes, String paisOrigen, String dificultad) {
        // Filtra las recetas según los criterios dados
        return recetas.stream()
                .filter(receta -> (nombre == null || receta.getNombre().toLowerCase().contains(nombre.toLowerCase())) &&
                                  (tipoCocina == null || receta.getTipoCocina().toLowerCase().contains(tipoCocina.toLowerCase())) &&
                                  (ingredientes == null || receta.getIngredientes().toLowerCase().contains(ingredientes.toLowerCase())) &&
                                  (paisOrigen == null || receta.getPaisOrigen().toLowerCase().contains(paisOrigen.toLowerCase())) &&
                                  (dificultad == null || receta.getDificultad().toLowerCase().contains(dificultad.toLowerCase())))
                .toList();
    }
}

