package com.example.seguridad_calidad.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.seguridad_calidad.Model.Receta;
import com.example.seguridad_calidad.services.RecetaService;

@Controller
public class RecetaController {

    private final RecetaService recetaService;

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    @GetMapping("/recetas")
    public String mostrarRecetas(@RequestParam(required = false) String nombre,
                                  @RequestParam(required = false) String tipoCocina,
                                  @RequestParam(required = false) String ingredientes,
                                  @RequestParam(required = false) String paisOrigen,
                                  @RequestParam(required = false) String dificultad,
                                  Model model) {
        
        List<Receta> recetas = recetaService.buscarRecetas(nombre, tipoCocina, ingredientes, paisOrigen, dificultad);
        model.addAttribute("recetas", recetas);
        return "home";
    }
}
