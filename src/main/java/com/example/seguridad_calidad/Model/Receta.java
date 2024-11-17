package com.example.seguridad_calidad.Model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Receta {
    private Long recetaId;
    private String nombre;
    private String categoria; // Anteriormente "tipoCocina"
    private String ingredientes;
    private String origen; // Anteriormente "paisOrigen"
    private String dificultad;
    private Date fechaCreacion;
    private Map<String, List<String>> media; // Agregado: Lista de medios

    // Constructor
    public Receta(Long recetaId, String nombre, String categoria, String ingredientes, String origen, String dificultad, Date fechaCreacion, Map<String, List<String>> media) {
        this.recetaId = recetaId;
        this.nombre = nombre;
        this.categoria = categoria;
        this.ingredientes = ingredientes;
        this.origen = origen;
        this.dificultad = dificultad;
        this.fechaCreacion = fechaCreacion;
        this.media = media;
    }

    // Getters y Setters
    public Long getRecetaId() {
        return recetaId;
    }

    public void setRecetaId(Long recetaId) {
        this.recetaId = recetaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Map<String, List<String>> getMedia() {
        return media;
    }

    public void setMedia(Map<String, List<String>> media) {
        this.media = media;
    }
}
