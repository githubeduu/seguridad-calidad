package com.example.seguridad_calidad.Model;

public class RecetaMedia {
    private Long recetaId;
    private String tipo;
    private String contenido; // Base64
    private String esPortada;

    // Constructor
    public RecetaMedia(Long recetaId, String tipo, String contenido, String esPortada) {
        this.recetaId = recetaId;
        this.tipo = tipo;
        this.contenido = contenido;
        this.esPortada = esPortada;
    }

    // Getters y Setters
    public Long getRecetaId() {
        return recetaId;
    }

    public void setRecetaId(Long recetaId) {
        this.recetaId = recetaId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getEsPortada() {
        return esPortada;
    }

    public void setEsPortada(String esPortada) {
        this.esPortada = esPortada;
    }
}
