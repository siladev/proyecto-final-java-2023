package com.proyecto.clases;

// CLASE PROGRAMADA POR: Acosta Silvina
public class Equipo {
    // Atributos de la clase
    private String nombre;
    private String descripcion;

    // Método Constructor vacío
    public Equipo() { }

    // Método Constructor con parámetros
    public Equipo(String nombre) {
        this.nombre = nombre;
    }
    public Equipo(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    //Métodos Getters y Setters
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
