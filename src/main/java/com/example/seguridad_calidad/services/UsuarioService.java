package com.example.seguridad_calidad.services;

import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;

@Service
public class UsuarioService {
    private final RestTemplate restTemplate;
private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    public UsuarioService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> obtenerUsuarios() {
        String url = "http://localhost:8082/usuario";

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        Map<String, Object> responseBody = response.getBody();

        if (responseBody != null && responseBody.containsKey("_embedded")) {
            Map<String, Object> embedded = (Map<String, Object>) responseBody.get("_embedded");
            return (List<Map<String, Object>>) embedded.get("usuarioList");
        } else {
            return List.of();
        }
    }

    public void eliminarUsuario(Long id, String token) {
    log.info("Iniciando el proceso de eliminación para el usuario con ID: {}", id);
    log.info("Iniciando el proceso de eliminación para el usuario con token: {}", token);

    String url = "http://localhost:8082/usuario/" + id;

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + token);

    HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

    try {
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class);
        log.info("Respuesta del backend al eliminar el usuario: {}", response.getStatusCode());
    } catch (HttpClientErrorException e) {
        log.error("Error al eliminar el usuario. Código de estado: {}. Detalles: {}", e.getStatusCode(), e.getResponseBodyAsString());
        throw new RuntimeException("Error al eliminar el usuario: " + e.getMessage());
    } catch (Exception e) {
        log.error("Error inesperado al eliminar el usuario: {}", e.getMessage());
        throw new RuntimeException("Error inesperado al eliminar el usuario.");
    }
}
}
