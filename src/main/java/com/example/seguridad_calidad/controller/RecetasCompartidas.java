package com.example.seguridad_calidad.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class RecetasCompartidas {
      @GetMapping("/recetas-compartidas")
    public String showUpload() {
        return "recetas-compartidas";
    }
}