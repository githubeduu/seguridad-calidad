package com.example.seguridad_calidad.controller;

import org.springframework.http.HttpStatus;
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

    private static final String LOGIN = "login";

    @GetMapping("/login")
    public String showLoginPage() {
        return LOGIN;
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
            ResponseEntity<Map<String, Object>> response = restTemplate.postForEntity(loginUrl, credentials, (Class<Map<String, Object>>) (Class<?>) Map.class);
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null) {
                Map<String, Object> userMap = (Map<String, Object>) responseBody.get("user");
                String token = (String) responseBody.get("token");
    
                // Almacenar el token en sesión
                session.setAttribute("token", token);
                session.setAttribute("username", userMap.get("nombre"));
                session.setAttribute("rolId", userMap.get("rolId"));

                
                return "redirect:/home"; 
            } else {
                model.addAttribute("error", "Respuesta vacía del servidor");
                return LOGIN;
            }
        } catch (HttpClientErrorException e) {
            String errorMessage = "Credenciales incorrectas";
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                Map<String, Object> errorBody = e.getResponseBodyAs(Map.class);
                errorMessage = (String) errorBody.getOrDefault("message", errorMessage);
            }
            model.addAttribute("error", errorMessage);
            return LOGIN;
        }
    }
    

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        return ResponseEntity.ok("Logout successful");
    }
}
