package com.proyecto.clases;

// CLASE PROGRAMADA POR: Acosta Silvina

public class Apostador implements Comparable<Apostador>{
    // Atributos de la clase
    private String nombre;
    private Pronostico[] pronosticos;

    // Método Constructor sin vacio
    public Apostador() { }

    // Métodos Constructores con parámetros
    public Apostador(String nombre) {
        this.nombre = nombre;
    }
    public Apostador(String nombre, Pronostico[] pronostico) {
        this.nombre = nombre;
        this.pronosticos = pronostico;
    }

    //Método para obtener los puntos del apostador
    public int totalPuntos() {
        int cantidadPuntos = 0;
        if (pronosticos != null) {
            for (Pronostico pronostico : pronosticos) {
                cantidadPuntos += pronostico.puntosPronosticos();
            }
        }
        return cantidadPuntos;
    }

    //Métodos Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Pronostico[] getPronosticos() { return pronosticos; }
    public void setPronosticos(Pronostico[] pronosticos) { this.pronosticos = pronosticos; }

    @Override
    public int compareTo(Apostador otroApostador) {
        return Integer.compare(otroApostador.totalPuntos(), this.totalPuntos());
    }

}


