package com.example.seguridad_calidad.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Controller
public class RegistroController {

    @GetMapping("/registro")
    public String showRegistrationForm() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String nombre,
            @RequestParam String rut,
            @RequestParam String direccion,
            @RequestParam String comuna,
            Model model) {

        String registerUrl = "http://localhost:8082/usuario";

        Map<String, Object> userRegistration = new HashMap<>();
        userRegistration.put("username", username);
        userRegistration.put("password", password);
        userRegistration.put("nombre", nombre);
        userRegistration.put("rut", rut);
        userRegistration.put("direccion", direccion);
        userRegistration.put("comuna", comuna);
        userRegistration.put("rolId", 2);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(registerUrl, userRegistration, String.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                return "redirect:/login";
            } else {
                model.addAttribute("error", "Error en el registro");
                return "registro";
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.CONFLICT) {
                model.addAttribute("error", "El usuario ya existe");
            } else {
                model.addAttribute("error", "Error en el registro: " + e.getMessage());
            }
            return "registro";
        } catch (Exception e) {
            model.addAttribute("error", "Error inesperado: " + e.getMessage());
            return "registro";
        }
    }


}
