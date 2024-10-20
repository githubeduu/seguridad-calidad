package com.example.seguridad_calidad.Model;

public class RecetaDetail {
    private String nombre;
    private String imagen;
    private String ingredientes;
    private String instrucciones;
    private String tiempoCoccion;
    private String dificultad;

    public RecetaDetail(String nombre,
            String imagen,
            String ingredientes, String instrucciones, String tiempoCoccion,
            String dificultad) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.ingredientes = ingredientes;
        this.instrucciones = instrucciones;
        this.tiempoCoccion = tiempoCoccion;
        this.dificultad = dificultad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public String ingredientes() {
        return ingredientes;
    }

    public String instrucciones() {
        return instrucciones;
    }

    public String tiempoCoccion() {
        return tiempoCoccion;
    }

    public String dificultad() {
        return dificultad;
    }

}
