package com.example.seguridad_calidad.Model;

public class Receta {
    private String nombre;
    private String tipoCocina;
    private String ingredientes;
    private String paisOrigen;
    private String dificultad;

    // Constructor
    public Receta(String nombre, String tipoCocina, String ingredientes, String paisOrigen, String dificultad) {
        this.nombre = nombre;
        this.tipoCocina = tipoCocina;
        this.ingredientes = ingredientes;
        this.paisOrigen = paisOrigen;
        this.dificultad = dificultad;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public String getTipoCocina() {
        return tipoCocina;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public String getDificultad() {
        return dificultad;
    }
}
