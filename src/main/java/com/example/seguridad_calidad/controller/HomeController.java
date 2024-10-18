package com.example.seguridad_calidad.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.seguridad_calidad.Model.Receta;
import com.example.seguridad_calidad.services.RecetaService;


@Controller
public class HomeController {
    
     private final RecetaService recetaService;

    public HomeController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("name", "Seguridad y Calidad en el Desarrollo");
        List<Receta> recetas = recetaService.obtenerRecetasPublicas();
        model.addAttribute("recetas", recetas);
        return "Home";
    }
}

