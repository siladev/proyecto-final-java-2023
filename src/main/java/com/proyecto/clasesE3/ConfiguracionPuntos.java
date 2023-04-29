package com.proyecto.clasesE3;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfiguracionPuntos {
    // Atributos de la clase
    private int puntosGanador;
    private int puntosPerdedor;
    private int puntosEmpate;
    private int puntosExtrasRonda;
    private int puntosExtrasFase;

    // Método Constructor vacío
    public ConfiguracionPuntos() {}

    // Métodos getter y setter
    public int getPuntosGanador() { return puntosGanador; }
    public void setPuntosGanador(int puntosGanador) {
        this.puntosGanador = puntosGanador;
    }
    public int getPuntosPerdedor() { return puntosPerdedor; }
    public void setPuntosPerdedor(int puntosPerdedor) {
        this.puntosPerdedor = puntosPerdedor;
    }
    public int getPuntosEmpate() { return puntosEmpate; }
    public void setPuntosEmpate(int puntosEmpate) {
        this.puntosEmpate = puntosEmpate;
    }
    public int getPuntosExtrasRonda() { return puntosExtrasRonda; }
    public void setPuntosExtrasRonda(int puntosExtrasRonda) {
        this.puntosExtrasRonda = puntosExtrasRonda;
    }
    public int getPuntosExtrasFase() { return puntosExtrasFase; }
    public void setPuntosExtrasFase(int puntosExtrasFase) {
        this.puntosExtrasFase = puntosExtrasFase;
    }

    public static ConfiguracionPuntos cargarConfiguracion() {
        ConfiguracionPuntos config = new ConfiguracionPuntos();

        try {
            // Se lee el archivo de configuración y se convierte a un objeto JSON
            String rutaArchivoConfiguracion = "src/main/java/com/proyecto/archivos/etapa3/archivoConfiguracion.json";
            JSONObject json = new JSONObject(new String(Files.readAllBytes(Paths.get(rutaArchivoConfiguracion))));

            // Se asignan los valores correspondientes a los atributos de la clase ConfiguracionPuntos
            config.setPuntosGanador(json.getInt("puntos_ganador"));
            config.setPuntosPerdedor(json.getInt("puntos_perdedor"));
            config.setPuntosEmpate(json.getInt("puntos_empate"));
            config.setPuntosExtrasRonda(json.getInt("puntos_extras_ronda"));
            config.setPuntosExtrasFase(json.getInt("puntos_extras_fase"));

        } catch(IOException e) {
            System.out.println("Error al leer el archivo de configuración");
            e.printStackTrace();
        }

        return config;
    }
}
