package com.example.seguridad_calidad.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.example.seguridad_calidad.Model.Receta;
import com.example.seguridad_calidad.services.RecetaService;

import jakarta.servlet.http.HttpSession;


@Controller
public class HomeController {
    
     private final RecetaService recetaService;

    public HomeController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        List<Receta> recetas = recetaService.obtenerRecetasPublicas();
        model.addAttribute("recetas", recetas);

        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);

        Integer rolId = (Integer) session.getAttribute("rolId"); // Manejar valores nulos
        model.addAttribute("rolId", rolId != null ? rolId : 0);

        return "Home";
    }

    @GetMapping("/mantenedor-usuarios")
    public String listarUsuarios(Model model) {
        String url = "http://localhost:8082/usuario";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        Map<String, Object> responseBody = response.getBody();

        if (responseBody != null && responseBody.containsKey("_embedded")) {
            Map<String, Object> embedded = (Map<String, Object>) responseBody.get("_embedded");
            List<Map<String, Object>> usuarios = (List<Map<String, Object>>) embedded.get("usuarioList");
            
            // Pasar la lista de usuarios al modelo
            model.addAttribute("usuarios", usuarios);
        } else {
            model.addAttribute("usuarios", List.of()); // Lista vacía si no hay datos
        }

        return "mantenedor-usuarios";
    }
}

