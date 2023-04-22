package com.proyecto.clases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

// CLASE PROGRAMADA POR: Gonzalez José
public class PartidoTest {
    @Test
    public void probarResultadoGanaEquipo1(){
        //Crear datos de prueba
        Equipo equipo1 = new Equipo("Argentina", "");
        Equipo equipo2 = new Equipo("Mexico", "");
        int golesEquipo1, golesEquipo2;
        golesEquipo1=2;
        golesEquipo2=0;

        //Constructor Partido(int ronda, Equipo equipo1, Equipo equipo2, int golesEquipo1, int golesEquipo2)
        Partido partido=new Partido(1,equipo1,equipo2,golesEquipo1,golesEquipo2);

        ResultadoEnum resultado = ResultadoEnum.ganador;
        //Probar el método resultado de la clase Partido
        Assertions.assertEquals(resultado, partido.resultado(equipo1));
    }

    @Test
    public void probarResultadoPierdeEquipo2(){
        //Crear datos de prueba
        Equipo equipo1 = new Equipo("Argentina", "");
        Equipo equipo2 = new Equipo("Mexico", "");
        int golesEquipo1, golesEquipo2;
        golesEquipo1=2;
        golesEquipo2=0;

        //Constructor Partido(int ronda, Equipo equipo1, Equipo equipo2, int golesEquipo1, int golesEquipo2)
        Partido partido=new Partido(1, equipo1, equipo2, golesEquipo1, golesEquipo2);

        ResultadoEnum resultado = ResultadoEnum.perdedor;
        //Probar el método resultado de la clase Partido
        Assertions.assertEquals(resultado, partido.resultado(equipo2));
    }
}