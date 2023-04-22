package com.proyecto.clases;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// CLASE PROGRAMADA POR: Gonzalez José
public class PronosticoTest {
    @Test
    public void probarPuntosPronosticosGanaEquipo1() {
        // Declaramos los valores/datos necesarios

        // Constructor Equipo(String nombre, String descripcion)
        Equipo equipo1 = new Equipo("Argentina", "el de la 3ra estrella");
        Equipo equipo2 = new Equipo("Mexico", "");

        // Constructor Partido(int ronda, Equipo equipo1, Equipo equipo2, int golesEquipo1, int golesEquipo2)
        Partido partido = new Partido(1, equipo1, equipo2, 2, 0);
        ResultadoEnum resultado = ResultadoEnum.ganador;

        // Constructor Pronostico(Partido partido, Equipo equipo, ResultadoEnum resultado)
        Pronostico pronostico = new Pronostico(partido, equipo1, resultado);

        // probar el metodo puntosPronosticos de la clase Pronostico
        Assertions.assertEquals(1,pronostico.puntosPronosticos());
    }

    @Test
    public void probarPuntosPronosticosNoGanaEquipo2() {
        // Declaramos los valores/datos necesarios

        // Constructor Equipo(String nombre, String descripcion)
        Equipo equipo1 = new Equipo("Argentina", "el de la 3ra estrella");
        Equipo equipo2 = new Equipo("Mexico", "");

        // Constructor Partido(int ronda, Equipo equipo1, Equipo equipo2, int golesEquipo1, int golesEquipo2)
        Partido partido = new Partido(1, equipo1, equipo2, 2, 0);
        ResultadoEnum resultado = ResultadoEnum.ganador;

        // Constructor Pronostico(Partido partido, Equipo equipo, ResultadoEnum resultado)
        Pronostico pronostico = new Pronostico(partido, equipo2, resultado);

        // Probar el método puntosPronosticos de la clase Pronostico
        Assertions.assertEquals(0,pronostico.puntosPronosticos());
    }
}
