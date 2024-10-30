package com.example.seguridad_calidad.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.seguridad_calidad.Model.Receta;
import com.example.seguridad_calidad.Model.RecetaDetail;
import com.example.seguridad_calidad.services.RecetaDetailService;
import com.example.seguridad_calidad.services.RecetaService;

@Controller
public class RecetaController {

    private List<RecetaDetail> recetaDetails = new ArrayList<>();

    private final RecetaService recetaService;
    private final RecetaDetailService recetaDetailService;

    public RecetaController(RecetaService recetaService, RecetaDetailService recetaDetailService) {
        this.recetaService = recetaService;
        this.recetaDetailService = recetaDetailService;

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
        return "recetas";
    }

    @GetMapping("/recetas/{nombre}")
    public String MostrarDetalleRecetas(@PathVariable String nombre, Model model) {
        RecetaDetail recetaEncontrada = recetaDetailService.getRecetasDetail(nombre);
    
        if (recetaEncontrada != null) {
            model.addAttribute("recetas", recetaEncontrada);
            return "recetas";
        }
    
        return "error";
    }

}
