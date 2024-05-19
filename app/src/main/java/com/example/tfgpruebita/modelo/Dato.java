package com.example.tfgpruebita.modelo;

public class Dato {
    private String id;
    private String titulo;
    private String dato;
    private String autor;
    private String imagenUrl;

    public Dato() {
    }

    public Dato(String id, String titulo, String dato, String autor, String imagenUrl) {
        this.id = id;
        this.titulo = titulo;
        this.dato = dato;
        this.autor = autor;
        this.imagenUrl = imagenUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    @Override
    public String toString() {
        return titulo + " - " + dato + " - " + autor;
    }
}