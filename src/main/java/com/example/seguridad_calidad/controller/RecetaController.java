package com.example.seguridad_calidad.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.seguridad_calidad.Model.Receta;
import com.example.seguridad_calidad.Model.RecetaDetail;
import com.example.seguridad_calidad.services.RecetaDetailService;
import com.example.seguridad_calidad.services.RecetaService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RecetaController {
    private final RecetaService recetaService;
    private final RecetaDetailService recetaDetailService;

    public RecetaController(RecetaService recetaService, RecetaDetailService recetaDetailService) {
        this.recetaService = recetaService;
        this.recetaDetailService = recetaDetailService;
    }

    @GetMapping("/recetas")
    public String mostrarRecetas(Model model) {
        List<Receta> recetas = recetaService.obtenerRecetasPublicas();
        model.addAttribute("recetas", recetas);
        return "recetas";
    }

    @GetMapping("/recetas/{id}")
    public String mostrarDetalleRecetas(@PathVariable int id, Model model, HttpServletRequest request) {
        // Obtener el detalle de la receta
        RecetaDetail recetaDetalle = recetaDetailService.getRecetasDetail(id);

        // Obtener la URL actual
        String currentUrl = request.getRequestURL().toString();

        // Construir URLs para redes sociales
        String facebookUrl = "https://www.facebook.com/sharer/sharer.php?u=" + currentUrl;
        String twitterUrl = "https://twitter.com/intent/tweet?text=" + recetaDetalle.getReceta().getNombre() + "&url=" + currentUrl;
        String instagramUrl = "https://www.instagram.com/?url=" + currentUrl;

        // Obtener los medios asociados a la receta (imágenes, videos, y enlaces de YouTube)
        Map<String, List<String>> media = recetaService.obtenerMediosPorReceta(id);

        // Agregar atributos al modelo
        model.addAttribute("recetaDetalle", recetaDetalle);
        model.addAttribute("facebookUrl", facebookUrl);
        model.addAttribute("twitterUrl", twitterUrl);
        model.addAttribute("instagramUrl", instagramUrl);
        model.addAttribute("media", media);

        return "recetas";
    }

    @PostMapping("/recetas/{id}/media")
    public String subirMedia(
            @PathVariable int id,
            @RequestParam("tipo") String tipo,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "youtubeUrl", required = false) String youtubeUrl,
            RedirectAttributes redirectAttributes) {
        try {
            if ("youtube".equalsIgnoreCase(tipo) && youtubeUrl != null) {
                // Guardar enlace de YouTube
                recetaService.subirMedia(id, tipo, null, youtubeUrl);
                redirectAttributes.addFlashAttribute("mensaje", "Enlace de YouTube subido con éxito.");
            } else if (file != null && !file.isEmpty()) {
                // Guardar imagen o video
                recetaService.subirMedia(id, tipo, file, null);
                redirectAttributes.addFlashAttribute("mensaje", "Archivo subido con éxito.");
            } else {
                redirectAttributes.addFlashAttribute("error", "Debe proporcionar un archivo o un enlace de YouTube.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al subir el medio: " + e.getMessage());
        }
        return "redirect:/recetas/" + id;
    }
}

