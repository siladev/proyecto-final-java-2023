package com.proyecto.clases;

// CLASE PROGRAMADA POR: Acosta Silvina

public class Partido {
    // Atributos de la clase
    private Equipo equipo1;
    private Equipo equipo2;
    private int golesEquipo1;
    private int golesEquipo2;
    private int nroRonda;

    // Método Constructor vacío
    public Partido() { }

    // Método Constructor con parámetros
    public Partido( Equipo equipo1, Equipo equipo2) {
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
    }
    public Partido(int nroRonda, Equipo equipo1, Equipo equipo2, int golesEquipo1, int golesEquipo2) {
        this.nroRonda = nroRonda;
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.golesEquipo1 = golesEquipo1;
        this.golesEquipo2 = golesEquipo2;
    }
    // Este método devuelve el resultado del partido para un equipo dado
    public ResultadoEnum resultado(Equipo equipo){
        // RESULTADO: EMPATE
        if (golesEquipo1==golesEquipo2) {
            return ResultadoEnum.empate;
        }
        // Resultado Equipo 1
        if(equipo1 != null && equipo1.equals(equipo)) {
            if (golesEquipo1 > golesEquipo2) {
                return ResultadoEnum.ganador;
            } else {
                return ResultadoEnum.perdedor;
            }
        }
        // Resultado Equipo 2
        if(equipo2 != null && equipo2.equals(equipo)) {
            if (golesEquipo2 > golesEquipo1) {
                return ResultadoEnum.ganador;
            } else {
                return ResultadoEnum.perdedor;
            }
        }
        return null;
    }

    //Métodos Getters y Setters
    public Equipo getEquipo1() {
        return equipo1;
    }
    public void setEquipo1(Equipo equipo1) {
        this.equipo1 = equipo1;
    }
    public Equipo getEquipo2() {
        return equipo2;
    }
    public void setEquipo2(Equipo equipo2) {
        this.equipo2 = equipo2;
    }
    public int getGolesEquipo1() {
        return golesEquipo1;
    }
    public void setGolesEquipo1(int golesEquipo1) {
        this.golesEquipo1 = golesEquipo1;
    }
    public int getGolesEquipo2() {
        return golesEquipo2;
    }
    public void setGolesEquipo2( int golesEquipo2) {
        this.golesEquipo2 = golesEquipo2;
    }
}