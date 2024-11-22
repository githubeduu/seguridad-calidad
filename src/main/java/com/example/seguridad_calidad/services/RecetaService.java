package com.example.seguridad_calidad.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.seguridad_calidad.DTO.ComentarioDto;
import com.example.seguridad_calidad.DTO.ComentarioResponseDto;
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
                // Obtener medios asociados
                String mediaUrl = "http://localhost:8087/recetas/" + receta.getRecetaId() + "/media";
                try {
                    Map<String, List<String>> media = restTemplate.getForObject(mediaUrl, Map.class);
                    receta.setMedia(media);
                } catch (Exception e) {
                    receta.setMedia(Map.of(
                            "imagenes", List.of(),
                            "videos", List.of(),
                            "youtubeLinks", List.of()
                    ));
                }

                // Obtener comentarios asociados
                String comentariosUrl = "http://localhost:8087/recetas/" + receta.getRecetaId() + "/comentarios";
                try {
                    ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                            comentariosUrl,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<List<Map<String, Object>>>() {}
                    );

                    List<ComentarioResponseDto> comentarios = response.getBody().stream()
                            .map(data -> new ComentarioResponseDto(
                                    (String) data.get("usuario"),
                                    (String) data.get("comentario"),
                                    (Integer) data.get("puntuacion")
                            ))
                            .collect(Collectors.toList());

                    receta.setComentarios(comentarios);
                } catch (Exception e) {
                    receta.setComentarios(List.of());
                }
            }
        }

        return recetasArray != null ? Arrays.asList(recetasArray) : List.of();
    }
    

        public List<String> obtenerImagenes(int recetaId) {
            String url = "http://localhost:8087/recetas/" + recetaId + "/imagen";
            String[] imagenesArray = restTemplate.getForObject(url, String[].class);
            return imagenesArray != null ? Arrays.asList(imagenesArray) : new ArrayList<>();
        }



        public String subirMedia(int recetaId, String tipo, MultipartFile file, String youtubeUrl) {
            String url = "http://localhost:8087/recetas/" + recetaId + "/media";
        
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        
                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                body.add("tipo", tipo);
        
                if ("youtube".equalsIgnoreCase(tipo)) {
                    // Añadir el enlace de YouTube
                    body.add("youtubeUrl", youtubeUrl);
                } else {
                    // Añadir archivo (foto o video)
                    body.add("file", new ByteArrayResource(file.getBytes()) {
                        @Override
                        public String getFilename() {
                            return file.getOriginalFilename();
                        }
                    });
                }
        
                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
                ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        
                return response.getBody();
            } catch (Exception e) {
                throw new RuntimeException("Error al subir el archivo o enlace: " + e.getMessage());
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

    public void agregarComentario(int recetaId, ComentarioDto comentarioDto) {
        String url = "http://localhost:8087/recetas/" + recetaId + "/comentarios";
    
        try {
            // Realiza una solicitud POST para enviar el comentario
            restTemplate.postForEntity(url, comentarioDto, Void.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el comentario: " + e.getMessage(), e);
        }
    }
    
   
}

