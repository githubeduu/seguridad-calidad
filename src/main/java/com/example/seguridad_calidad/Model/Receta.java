package com.example.seguridad_calidad.Model;

public class Receta {
    private String nombre;
    private String tipoCocina;
    private String ingredientes;
    private String paisOrigen;
    private String dificultad;
    private String imagen;

    // Constructor
    public Receta(String nombre, String tipoCocina, String ingredientes, String paisOrigen, String dificultad, String imagen) {
        this.nombre = nombre;
        this.tipoCocina = tipoCocina;
        this.ingredientes = ingredientes;
        this.paisOrigen = paisOrigen;
        this.dificultad = dificultad;
        this.imagen = imagen;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    public String getImagen(){
        return imagen;
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
