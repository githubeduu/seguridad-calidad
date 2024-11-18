package com.example.seguridad_calidad.DTO;

public class ComentarioDto {
    private String usuario;
    private String comentario;
    private int puntuacion;

    // Constructor vacío
    public ComentarioDto() {
    }

    // Constructor con parámetros
    public ComentarioDto(String usuario, String comentario, int puntuacion) {
        this.usuario = usuario;
        this.comentario = comentario;
        this.puntuacion = puntuacion;
    }

    // Getter y Setter para 'usuario'
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    // Getter y Setter para 'comentario'
    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    // Getter y Setter para 'puntuacion'
    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    @Override
    public String toString() {
        return "ComentarioDto{" +
                "usuario='" + usuario + '\'' +
                ", comentario='" + comentario + '\'' +
                ", puntuacion=" + puntuacion +
                '}';
    }
}
