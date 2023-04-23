package com.proyecto.clases;

// CLASE PROGRAMADA POR: Rozas Gonzalo
public class Pronostico {

    // Atributos de Clase
    private Partido partido;
    private Equipo equipo;
    private ResultadoEnum resultado;
    private Apostador apostador;

    // Método Constructor vacío
    public Pronostico() {}

    // Método Constructor con parámetros
    public Pronostico(Partido partido, Equipo equipo, ResultadoEnum resultado) {
        this.partido = partido;
        this.equipo = equipo;
        this.resultado = resultado;
    }

    // Método para los puntos que tiene el pronóstico
    public int puntosPronosticos() {
        ResultadoEnum pronosticoPartido = this.partido.resultado(this.equipo);
        if (this.resultado != null && this.resultado.equals(pronosticoPartido)) {
            return 1;
        } else {
            return 0;
        }

    }
    //Métodos Getters y Setters
    public Partido getPartido() { return partido; }
    public void setPartido(Partido partido) { this.partido = partido; }
    public Equipo getEquipo() { return equipo; }
    public void setEquipo(Equipo equipo) { this.equipo = equipo; }
    public ResultadoEnum getResultado() { return resultado; }
    public void setResultado(ResultadoEnum resultado) { this.resultado = resultado; }
    public Apostador getApostador() { return apostador; }
    public void setApostador(Apostador apostador) { this.apostador = apostador; }
}
