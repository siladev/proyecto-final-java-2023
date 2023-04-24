package com.proyecto;
import com.proyecto.clases.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal que ejecuta el programa de pronósticos deportivos ETAPA 1.
 */
public class MainEtapa1 {
    public static void main(String[] args) {
        // Rutas de los archivos
        String archivoResultados = "src/main/java/com/proyecto/archivos/etapa1/resultados.csv";
        String archivoPronosticos = "src/main/java/com/proyecto/archivos/etapa1/pronosticos.csv";
        Ronda ronda = cargarRonda(archivoResultados); //creamos la ronda mediante el método cargarRonda
        Pronostico[] pronostico = cargarPronostico(archivoPronosticos, ronda);

        //Salida que muestra el programa en pantalla
        System.out.println("***************************************");
        System.out.println("        PRONÓSTICOS DEPORTIVOS    ");
        System.out.println("***************************************");
        System.out.println("El total de puntos del apostador es: "+contarPuntos(pronostico));
        System.out.println("=======================================\n");
        System.out.println("***************************************");
        System.out.println(" DETALLE DE LOS PRONÓSTICOS REALIZADOS");
        System.out.println("***************************************\n");
        System.out.println("Resultados de partidos y pronósticos realizados:");
        mostrarPartidos(ronda, pronostico);
        System.out.println("\n********************************");
        System.out.println("PROGRAMADO POR GRUPO N° 4: ");
        System.out.println("Integrantes:");
        System.out.println("* Acosta Silvina Fernanda");
        System.out.println("* Gonzalez Armando Jose Alejandro");
        System.out.println("* Rozas Gonzalo");
        System.out.println("********************************");
    }
    /**
     * Muestra los detalles de los partidos y los pronósticos realizados.
     * @param ronda objeto de la clase Ronda que contiene los partidos de la ronda.
     * @param pronostico arreglo de objetos de la clase Pronostico que contiene los pronósticos realizados.
     */
    public static void mostrarPartidos(Ronda ronda, Pronostico[] pronostico) {
        int numPartido=1;
        System.out.format("%-11s %-30s %-24s %-10s %-6s\n", "Parido N°", "        Equipos", "Resultado del Partido", "Pronóstico", "Puntos");
        for(int num=0; num < ronda.getCantPartidos() ; num++) {
            Partido partidoPronostico = ronda.getPartidos()[num];
            Equipo equipoPronostico = partidoPronostico.getEquipo1();
            //for (Pronostico pronosticoPartido : pronostico) {
                //System.out.format("%-11s %-30s %-24s %-10s %-6s\n", "     " + numPartido, partidoPronostico.getEquipo1().getNombre()+" vs "+ partidoPronostico.getEquipo2().getNombre(), pronosticoPartido.getEquipo().getNombre() + " " + pronosticoPartido.getPartido().resultado(pronosticoPartido.getEquipo()), equipoPronostico.getNombre()+" "+partidoPronostico.resultado(equipoPronostico), "  " + partidoPronostico.puntosPronosticos());
                System.out.format("%-11s %-30s %-24s %-10s %-6s\n","    "+numPartido, partidoPronostico.getEquipo1().getNombre()+" vs "+ partidoPronostico.getEquipo2().getNombre(), equipoPronostico.getNombre() + " " + partidoPronostico.resultado(equipoPronostico), pronostico[num].getResultado(), pronostico[num].puntosPronosticos());
                numPartido++;
           // }
        }
    }
    /**
     * Carga los datos de la ronda desde el archivo resultados.csv.
     * @param archivoResultados ruta del archivo resultados.csv.
     * @return objeto de la clase Ronda que contiene los partidos de la ronda.
     */
    public static Ronda cargarRonda(String archivoResultados) {

        Ronda ronda = new Ronda(); // Crea un objeto Ronda vacío
        List<String> resultados = new ArrayList<String>();  // Crea una lista vacía para almacenar los resultados de los partidos

        try {
            resultados = Files.readAllLines(Paths.get(archivoResultados),StandardCharsets.ISO_8859_1); //Lee todas las líneas del archivo resultados.csv y se almacenan en la lista resultados
            resultados.remove(0); //Elimina el primer elemento de la lista, que contiene los encabezados del archivo.
        } catch(IOException e) {
            System.out.println("Error al leer el archivo resultados.csv");
            e.printStackTrace();
        }
        Partido[] partidosRonda = new Partido[resultados.size()]; //Crea un arreglo de objetos Partido con tamaño igual a la cantidad de resultados de partidos
        int numPartidos=0;  // Inicializa el contador de partidos en 0
        for (String resultado : resultados) { //Recorre la lista de resultados de partidos
            String[] vectorResultado = resultado.split(","); //Separa cada resultado en un arreglo de Strings

            Equipo equipo1 = new Equipo(); //Crea un objeto Equipo para el equipo 1
            equipo1.setNombre(vectorResultado[0]); //Asigna el nombre al equipo 1
            Equipo equipo2 = new Equipo(); //Crea un objeto Equipo para el equipo 2
            equipo2.setNombre(vectorResultado[3]); //Asigna el nombre al equipo 2
            // Crea un objeto Partido y se le asignan los equipos y los goles correspondientes
            Partido partido = new Partido();
            partido.setEquipo1(equipo1);
            partido.setEquipo2(equipo2);
            partido.setGolesEquipo1(Integer.parseInt(vectorResultado[1]));
            partido.setGolesEquipo2(Integer.parseInt(vectorResultado[2]));
            partidosRonda[numPartidos] = partido; // agrega el partido al arreglo de partidos de la ronda
            numPartidos++; // Incrementa el contador de partidos
        }

        ronda.setCantPartidos(numPartidos); // Asigna la cantidad de partidos al objeto Ronda
        ronda.setPartidos(partidosRonda);  // asigna el arreglo de partidos al objeto Ronda
        return ronda; // Devuelve el objeto Ronda con los partidos cargados
    }
    /**
     * Carga los pronósticos realizados desde el archivo pronosticos.csv.
     * @param archivoPronosticos ruta del archivo pronosticos.csv.
     * @param ronda objeto de la clase Ronda que contiene los partidos de la ronda.
     * @return arreglo de objetos de la clase Pronostico que contiene los pronósticos realizados.
     */
    public static Pronostico[] cargarPronostico(String archivoPronosticos, Ronda ronda) {
        List<String> pronosticos = new ArrayList<String>(); // Crea una lista vacía para almacenar los pronósticos

        try {
            pronosticos = Files.readAllLines(Paths.get(archivoPronosticos),StandardCharsets.ISO_8859_1);  // Lee todas las líneas del archivo pronósticos.csv y se almacenan en la lista pronósticos
            pronosticos.remove(0); //Se elimina el primer elemento de la lista, que contiene los encabezados del archivo.

        } catch(IOException e) {
            System.out.println("Error al leer el archivo resultados.csv");
            e.printStackTrace();
        }

        Pronostico[] pronosticosRonda = new Pronostico[ronda.getCantPartidos()];  // Crea un arreglo de objetos Pronostico con tamaño igual a la cantidad de partidos de la ronda
        Partido[] partidosRonda = ronda.getPartidos();   // Obtiene los partidos de la ronda

        for(int i=0; i < ronda.getCantPartidos(); i++) { //Recorre la cantidad de partidos de la ronda

            Pronostico pronostico = new Pronostico(); // Crea un objeto Pronostico vacío
            ResultadoEnum resultadoPronostico;
            if(pronosticos.get(i).split(",")[1].isEmpty()) {
                if(pronosticos.get(i).split(",")[3].isEmpty()) {
                    resultadoPronostico = ResultadoEnum.empate; // Asigna el resultado del pronóstico en empate
                } else {
                    resultadoPronostico = ResultadoEnum.ganador; // Asigna el resultado del pronóstico en perdedor
                }
            } else {
                resultadoPronostico = ResultadoEnum.perdedor; // Asigna el resultado del pronóstico en ganador
            }
            // Se asignan los valores correspondientes al objeto Pronostico
            pronostico.setPartido(partidosRonda[i]);
            pronostico.setEquipo(partidosRonda[i].getEquipo1());
            pronostico.setResultado(resultadoPronostico);
            pronosticosRonda[i] = pronostico; //Agrega el pronóstico al arreglo de pronósticos de la ronda
        }
        return pronosticosRonda; // Devuelve el arreglo de pronósticos de la ronda

    }
    /**
     * Cuenta el total de puntos obtenidos por el apostador.
     * @param pronosticos arreglo de objetos de la clase Pronostico que contiene los pronósticos realizados.
     * @return total de puntos obtenidos por el apostador.
     */
    public static int contarPuntos(Pronostico[] pronosticos) {
        int totalpuntos = 0; // Inicializa el contador de puntos en 0
        for (Pronostico pronostico : pronosticos) { // Recorre el arreglo de pronósticos
            totalpuntos += pronostico.puntosPronosticos();  // Llama al método puntosPronosticos() de cada objeto Pronostico y se suma al contador de puntos
        }
        return totalpuntos; //Devuelve el total de puntos obtenidos
    }
}
