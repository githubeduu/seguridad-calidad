package com.example.seguridad_calidad.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        String loginUrl = "http://localhost:8085/auth/login";
        
        // Crear el cuerpo de la solicitud
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);

        // Realizar la solicitud POST al backend
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(loginUrl, credentials, Map.class);
            Map<String, Object> userMap = (Map<String, Object>) response.getBody().get("user");
            String token = (String) response.getBody().get("token");

            // Almacenar el token en sesión
            session.setAttribute("token", token);
            session.setAttribute("username", userMap.get("nombre"));
            
            return "redirect:/home"; 
        } catch (HttpClientErrorException e) {
            model.addAttribute("error", "Credenciales incorrectas");
            return "login";
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return ResponseEntity.ok("Logout successful");
    }
}
