package com.example.tfgpruebita.modelo;

public class Jugador {

    private int id;
    private String Nombre;
    private String Equipo;
    private int goles;
    private int asistencias;
    private String estadoSalud;
    private boolean mvp;
    private String Posicion;
    private String valor;
    private int puntosJornada;
    private int puntosTotales;

    public Jugador() {
    }

    public Jugador(String Nombre, String Equipo, int goles, int asistencias, String estadoSalud, boolean mvp, String Posicion, String valor, int puntosJornada, int puntosTotales) {
        this.Nombre = Nombre;
        this.Equipo = Equipo;
        this.goles = goles;
        this.asistencias = asistencias;
        this.estadoSalud = estadoSalud;
        this.mvp = mvp;
        this.Posicion = Posicion;
        this.valor = valor;
        this.puntosJornada = puntosJornada;
        this.puntosTotales = puntosTotales;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public String getEquipo() {
        return Equipo;
    }

    public void setEquipo(String equipo) {
        this.Equipo = equipo;
    }

    public int getGoles() {
        return goles;
    }

    public void setGoles(int goles) {
        this.goles = goles;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(int asistencias) {
        this.asistencias = asistencias;
    }

    public String getEstadoSalud() {
        return estadoSalud;
    }

    public void setEstadoSalud(String estadoSalud) {
        this.estadoSalud = estadoSalud;
    }

    public boolean isMvp() {
        return mvp;
    }

    public void setMvp(boolean mvp) {
        this.mvp = mvp;
    }

    public String getPosicion() {
        return Posicion;
    }

    public void setPosicion(String posicion) {
        this.Posicion = posicion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getPuntosJornada() {
        return puntosJornada;
    }

    public void setPuntosJornada(int puntosJornada) {
        this.puntosJornada = puntosJornada;
    }

    public int getPuntosTotales() {
        return puntosTotales;
    }

    public void setPuntosTotales(int puntosTotales) {
        this.puntosTotales = puntosTotales;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nombre='" + Nombre + '\'' +
                ", equipo='" + Equipo + '\'' +
                ", goles=" + goles +
                ", asistencias=" + asistencias +
                ", estadoSalud='" + estadoSalud + '\'' +
                ", mvp=" + mvp +
                ", posicion='" + Posicion + '\'' +
                ", valor='" + valor + '\'' +
                ", puntosJornada=" + puntosJornada +
                ", puntosTotales=" + puntosTotales +
            '}';
}
}
