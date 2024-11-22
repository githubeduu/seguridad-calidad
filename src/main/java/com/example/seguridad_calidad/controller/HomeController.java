package com.example.seguridad_calidad.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.seguridad_calidad.Model.Receta;
import com.example.seguridad_calidad.services.RecetaService;
import com.example.seguridad_calidad.services.UsuarioService;

import jakarta.servlet.http.HttpSession;



@Controller
public class HomeController {
    
     private final RecetaService recetaService;
     private final UsuarioService usuarioService;

    public HomeController(RecetaService recetaService, UsuarioService usuarioService) {
        this.recetaService = recetaService;
        this.usuarioService = usuarioService;
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
        List<Map<String, Object>> usuarios = usuarioService.obtenerUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "mantenedor-usuarios";
    }

    @PostMapping("/eliminar-usuario")
    public String eliminarUsuario(@RequestParam Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute("token");

        if (token == null) {
            redirectAttributes.addFlashAttribute("error", "No estás autenticado");
            return "redirect:/mantenedor-usuarios";
        }

        try {
            usuarioService.eliminarUsuario(id, token);
            redirectAttributes.addFlashAttribute("success", "Usuario eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el usuario: " + e.getMessage());
        }

        return "redirect:/mantenedor-usuarios";
    }    
}

