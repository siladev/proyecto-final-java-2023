package com.proyecto.clases;

// CLASE PROGRAMADA POR: Gonzalez Armando Jose Alejandro

public class Ronda {
    // Atributos de la clase
    private String nroRonda;
    private Partido[] partidos;
    private int cantPartidos;

    // Método Constructor vacío
    public Ronda() { }

    // Método Constructor con parámetros

    public Ronda(String nroRonda) {
        this.nroRonda = nroRonda;
    }
    
    public Ronda(String nroRonda, Partido[] partidos, int cantPartidos) {
        this.nroRonda = nroRonda;
        this.partidos = partidos;
        this.cantPartidos = cantPartidos;
    }

    //Métodos getter y setter
    public String getNro() {
        return nroRonda;
    }
    public void setNro(String nroRonda) { this.nroRonda = nroRonda; }
    public Partido[] getPartidos() {
        return partidos;
    }
    public void setPartidos(Partido[] partidos) {
        this.partidos = partidos;
    }
    public int getCantPartidos() { return cantPartidos; }
    public void setCantPartidos(int cantPartidos) { this.cantPartidos = cantPartidos; }

}
