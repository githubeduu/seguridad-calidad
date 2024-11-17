package com.example.seguridad_calidad.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.seguridad_calidad.Model.Receta;



@Service
public class RecetaService {
private final RestTemplate restTemplate;

    public RecetaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Receta> obtenerRecetasPublicas() {
        String url = "http://localhost:8087/recetas";
        Receta[] recetasArray = restTemplate.getForObject(url, Receta[].class);
    
        if (recetasArray != null) {
            for (Receta receta : recetasArray) {
                // URL para obtener los medios asociados a la receta
                String mediaUrl = "http://localhost:8087/recetas/" + receta.getRecetaId() + "/media";
                try {
                    // Obtener medios desde el backend
                    Map<String, List<String>> media = restTemplate.getForObject(mediaUrl, Map.class);
                    if (media != null) {
                        receta.setMedia(media); // Asignar medios a la receta
                    }
                } catch (HttpClientErrorException.NotFound e) {
                    // Si no se encontraron medios, asignar valores vacíos
                    receta.setMedia(Map.of(
                            "imagenes", new ArrayList<>(),
                            "videos", new ArrayList<>(),
                            "youtubeLinks", new ArrayList<>()
                    ));
                }
            }
        }
    
        return recetasArray != null ? Arrays.asList(recetasArray) : new ArrayList<>();
    }
    

        public List<String> obtenerImagenes(int recetaId) {
            String url = "http://localhost:8087/recetas/" + recetaId + "/imagen";
            String[] imagenesArray = restTemplate.getForObject(url, String[].class);
            return imagenesArray != null ? Arrays.asList(imagenesArray) : new ArrayList<>();
        }



    public String subirMedia(int recetaId, String tipo, MultipartFile file) {
    String url = "http://localhost:8087/recetas/" + recetaId + "/media";

    try {
        // Crear una entidad HTTP con multipart/form-data
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Construir el cuerpo de la solicitud
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("tipo", tipo);
        body.add("file", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });

        // Crear la solicitud HTTP
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Enviar la solicitud al backend
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        return response.getBody(); // Retorna el mensaje de éxito o error
    } catch (Exception e) {
        throw new RuntimeException("Error al subir el archivo: " + e.getMessage());
    }
}
   
    public Map<String, List<String>> obtenerMediosPorReceta(int recetaId) {
        String url = "http://localhost:8087/recetas/" + recetaId + "/media";
        return restTemplate.getForObject(url, Map.class);
    }



    public List<Receta> buscarRecetas(String nombre, String tipoCocina, String ingredientes, String paisOrigen, String dificultad) {
        // Obtén todas las recetas desde el backend
        List<Receta> recetas = obtenerRecetasPublicas();

        // Aplica los filtros
        return recetas.stream()
                .filter(receta -> (nombre == null || receta.getNombre().toLowerCase().contains(nombre.toLowerCase())) &&
                                  (tipoCocina == null || receta.getCategoria().toLowerCase().contains(tipoCocina.toLowerCase())) &&
                                  (ingredientes == null || receta.getIngredientes().toLowerCase().contains(ingredientes.toLowerCase())) &&
                                  (paisOrigen == null || receta.getOrigen().toLowerCase().contains(paisOrigen.toLowerCase())) &&
                                  (dificultad == null || receta.getDificultad().toLowerCase().contains(dificultad.toLowerCase())))
                .toList();
    }

    public void subirEnlaceYoutube(int recetaId, String youtubeUrl) {
    try {
        // Crear una entidad HTTP para enviar el enlace
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Crear el cuerpo de la solicitud
        Map<String, String> body = new HashMap<>();
        body.put("tipo", "youtube");
        body.put("youtubeUrl", youtubeUrl);

        // Crear la solicitud HTTP
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);

        // URL del endpoint del backend para subir enlaces de YouTube
        String url = "http://localhost:8087/recetas/" + recetaId + "/media";

        // Enviar la solicitud
        restTemplate.postForEntity(url, requestEntity, String.class);
    } catch (Exception e) {
        throw new RuntimeException("Error al subir el enlace de YouTube: " + e.getMessage());
    }
}
}

