package com.proyecto.clases;

// CLASE PROGRAMADA POR: Gonzalez Armando Jose Alejandro
public class Ronda {
    // Atributos de la clase
    private String nro;
    private Partido[] partidos;
    private int cantPartidos;

    // Método Constructor vacío
    public Ronda() { }

    // Método Constructor con parámetros
    public Ronda(String nro, Partido[] partidos, int cantPartidos) {
        this.nro = nro;
        this.partidos = partidos;
        this.cantPartidos = cantPartidos;
    }

    //Métodos getter y setter
    public String getNro() {
        return nro;
    }
    public void setNro(String nro) {
        this.nro = nro;
    }
    public Partido[] getPartidos() {
        return partidos;
    }
    public void setPartidos(Partido[] partidos) {
        this.partidos = partidos;
    }
    public int getCantPartidos() { return cantPartidos; }
    public void setCantPartidos(int cantPartidos) { this.cantPartidos = cantPartidos; }

}
