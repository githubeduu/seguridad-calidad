package com.example.seguridad_calidad.DTO;

public class ComentarioResponseDto {

    private String usuario;
    private String comentario;
    private int puntuacion;

    // Constructor
    public ComentarioResponseDto(String usuario, String comentario, int puntuacion) {
        this.usuario = usuario;
        this.comentario = comentario;
        this.puntuacion = puntuacion;
    }

    // Getters y setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
}
