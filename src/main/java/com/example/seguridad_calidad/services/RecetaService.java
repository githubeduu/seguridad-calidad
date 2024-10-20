package com.example.seguridad_calidad.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.seguridad_calidad.Model.Receta;

@Service
public class RecetaService {

    private final List<Receta> recetas = new ArrayList<>(); // Tu lista de recetas estáticas

    public RecetaService() {
        // Inicializa tus recetas aquí
        recetas.add(new Receta("Receta 1", "Italiana", "Ingredientes 1", "Italia", "Fácil"));
        recetas.add(new Receta("Receta 2", "Mexicana", "Ingredientes 2", "México", "Media"));
        recetas.add(new Receta("Receta 3", "Japonesa", "Ingredientes 3", "Japón", "Difícil"));
        // Agrega más recetas si es necesario
    }

    

    public List<Receta> obtenerRecetasPublicas() {
       
        List<Receta> recetasPublicas = new ArrayList<>();
        
        // Agrega las recetas que quieres mostrar
        recetasPublicas.add(new Receta("Receta 1", "Italiana", "Ingredientes 1", "Italia", "Fácil"));
        recetasPublicas.add(new Receta("Receta 2", "Mexicana", "Ingredientes 2", "México", "Media"));
        recetasPublicas.add(new Receta("Receta 3", "Japonesa", "Ingredientes 3", "Japón", "Difícil"));
        
        return recetasPublicas;
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
