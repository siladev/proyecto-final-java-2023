package com.proyecto.clases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApostadorTest {

    @Test
    @DisplayName("Debería devolver cero cuando no hay pronósticos")
    void totalPuntosConCeroPronosticos() { // Crear datos de prueba
        Apostador apostador = new Apostador("Sabrina");
        assertEquals(
                0,
                apostador.totalPuntos(),
                "El total de puntos debería ser cero cuando no hay pronósticos");
    }

    @Test
    @DisplayName("Debería devolver el total de puntos correcto cuando solo hay un pronóstico")
    void totalPuntosConUnPronostico() { // Crear datos de prueba
        Equipo equipo1 = new Equipo("Argentina", "Seleccionado");
        Equipo equipo2 = new Equipo("Arabia Saudita", "Seleccionado");
        Partido partido = new Partido(1, equipo1, equipo2, 1, 2);
        Pronostico pronostico = new Pronostico(partido, equipo1, ResultadoEnum.perdedor);

        // Crear Apostador con un solo Pronostico
        Apostador apostador = new Apostador("José", new Pronostico[]{pronostico});

        // Probar el método totalPuntos
        int totalPuntos = apostador.totalPuntos();

        // Verificar el resultado
        assertEquals(1, totalPuntos, "El total de puntos debería ser 1");
    }

    @Test
    @DisplayName("Debería devolver el total de puntos cuando hay múltiples pronósticos")
    void totalPuntosWithMultiplePronosticos() {// Crear datos de prueba
        Equipo equipo1 = new Equipo("Argentina", "Seleccionado");
        Equipo equipo2 = new Equipo("Arabia Saudita", "Seleccionado");
        Partido partido1 = new Partido(1, equipo1, equipo2, 1, 2);
        Equipo equipo3 = new Equipo("Polonia", "Seleccionado");
        Equipo equipo4 = new Equipo("Mexico", "Seleccionado");
        Partido partido2 = new Partido(1, equipo1, equipo2, 0, 0);
        // Crear pronósticos
        Pronostico pronostico1 = new Pronostico(partido1, equipo1, ResultadoEnum.perdedor);
        Pronostico pronostico2 = new Pronostico(partido2, equipo3, ResultadoEnum.empate);
        Pronostico[] pronosticos = {pronostico1, pronostico2};
        // Crear Apostador con varios Pronósticos
        Apostador apostador = new Apostador("Gonzalo", pronosticos);
        // Probar el método totalPuntos
        int totalPuntos = apostador.totalPuntos();
        // Verificar el resultado
        assertEquals(2, totalPuntos, "El total de puntos debería ser 2 si acierta los pronósticos");
    }
}