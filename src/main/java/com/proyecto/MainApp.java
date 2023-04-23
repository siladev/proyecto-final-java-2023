package com.proyecto;
import com.proyecto.clases.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;

// CLASE PROGRAMADA POR: Acosta Silvina
/**
 * Clase principal que ejecuta el programa de pronósticos deportivos.
 */
public class MainApp {
    public static void main(String[] args) {
        // Rutas de los archivos
        String archivoResultados = "src/main/java/com/proyecto/archivos/resultados.csv";
        String archivoPronosticos = "src/main/java/com/proyecto/archivos/pronosticos.csv";

        List<Apostador> apostadores = new ArrayList<>(); // Crea una lista de apostadores vacía
        // Agrega 3 objetos apostadores
        apostadores.add(new Apostador("Gonzalo"));
        apostadores.add(new Apostador("José"));
        apostadores.add(new Apostador("Silvina"));

        // Carga las rondas de resultados desde el archivo resultados.csv y las almacena en un array de objetos Ronda.
        Ronda[] rondas = cargarRonda(archivoResultados);
        // Carga los pronósticos de los apostadores desde el archivo pronosticos.csv, utilizando la información de las rondas y la lista de apostadores previamente cargados.
        Pronostico[] pronostico = cargarPronostico(archivoPronosticos, rondas, apostadores);
        // El método asigna los pronósticos cargados a cada apostador
        asignarPronosticos(apostadores, pronostico);

        //Salida que muestra el programa en pantalla
        System.out.println("********************************");
        System.out.println("     PRONÓSTICOS DEPORTIVOS    ");
        System.out.println("********************************");

        System.out.println("\nRESULTADOS DE LA COMPETENCIA:");
        mostrarPuntos(pronostico, apostadores);
        System.out.println("***************************************");
        System.out.println(" DETALLE DE LOS PRONÓSTICOS REALIZADOS");
        System.out.println("***************************************\n");
        mostrarPronostico(apostadores, rondas, pronostico);
        System.out.println("\n********************************");
        System.out.println("PROGRAMADO POR GRUPO N° 4: ");
        System.out.println("Integrantes:");
        System.out.println("* Acosta Silvina Fernanda");
        System.out.println("* Gonzalez Armando Jose Alejandro");
        System.out.println("* Rozas Gonzalo");
        System.out.println("********************************");
    }
    /**
     * Muestra los puntos totales de cada apostador en orden descendente.
     * @param pronosticos Un array de Pronostico con los pronósticos realizados.
     * @param apostadores Una lista de Apostador con los apostadores a mostrar.
     */
    public static void mostrarPuntos(Pronostico[] pronosticos, List<Apostador> apostadores) {
        System.out.format("%-10s %-10s\n", "Nombre", "Puntos");
        System.out.println("--------------------");
        // Ordena la lista de apostadores por el total de puntos de mayor a menor
        Collections.sort(apostadores, (a1, a2) -> Integer.compare(a2.totalPuntos(), a1.totalPuntos()));
        // Recorre la lista de apostadores y para c/u, obtiene el total de puntos y los imprime junto con su nombre.
        for (Apostador apostador : apostadores) {
            int totalpuntosapostador = 0;
            totalpuntosapostador += apostador.totalPuntos();
            // El operador ternario se utiliza para mostrar "punto" en lugar de "puntos" en caso de que el puntaje sea igual a 1
            System.out.format("%-10s: %d %s\n", apostador.getNombre(), totalpuntosapostador, totalpuntosapostador == 1 ? "punto" : "puntos");
        }
        System.out.println("--------------------\n");
    }
    /**
     * Muestra el pronóstico detallado de cada apostador para cada partido de cada ronda.
     * @param apostadores La lista de apostadores.
     * @param rondas Las rondas de partidos.
     * @param pronostico Los pronósticos de los apostadores.
     */
    public static void mostrarPronostico(List<Apostador> apostadores, Ronda[] rondas, Pronostico[] pronostico) {
        //  Recorre todos los apostadores y para c/u de ellos se muestra una tabla detallada del pronóstico.
        for (Apostador apostador : apostadores) {
            System.out.println("**************************************************************************************");
            System.out.println("Pronóstico de: " + apostador.getNombre());
            System.out.format("%-11s %-30s %-24s %-10s %-6s\n", "Parido N°", "        Equipos", "Resultado del Partido", "Pronóstico", "Puntos");
            for (Ronda ronda : rondas) {
                int num = 0;
                for (Pronostico pronosticoPartido : pronostico) {
                    if (pronosticoPartido.getApostador().equals(apostador)) {
                        num++;
                        System.out.format("%-11s %-30s %-24s %-10s %-6s\n", "     " + num, pronosticoPartido.getPartido().getEquipo1().getNombre() + " vs " + pronosticoPartido.getPartido().getEquipo2().getNombre(), pronosticoPartido.getEquipo().getNombre() + " " + pronosticoPartido.getPartido().resultado(pronosticoPartido.getEquipo()), pronosticoPartido.getResultado(), "  " + pronosticoPartido.puntosPronosticos());
                    }
                }
            }
        }
    }
    /**
     * Carga las rondas de partidos a partir de un archivo de resultados.
     * @param archivoResultados El archivo de resultados a cargar.
     * @return Un array de Ronda con las rondas cargadas.
     */
    private static Ronda[] cargarRonda(String archivoResultados) {
        // Crea lista resultados para almacenar los datos del archivo resultados.csv
        List<String> resultados = new ArrayList<String>();
        // Crea la lista rondas  para almacenar las rondas creadas
        List<Ronda> rondas = new ArrayList<Ronda>();

        //El bloque try-catch se utiliza para manejar una excepción (IOException) durante la lectura del archivo
        try {
            resultados = Files.readAllLines(Paths.get(archivoResultados), Charset.forName("ISO-8859-1")); //Lee el archivo resultados.csv y lo almacena en una lista
            resultados.remove(0); //se elimina el primer elemento de la lista, que contiene los encabezados del archivo.
        } catch(IOException e) {
            System.out.println("Error al leer el archivo resultados.csv");
            e.printStackTrace();
        }
        //Crea una lista de partidos para ir agregando los partidos de cada ronda
        List <Partido> partidosRonda = new ArrayList<Partido>();
        String nroRonda = ""; // almacena el número de ronda actual

        //Recorre ada uno de los elementos de la lista resultados
        for (String resultado : resultados) {
            //Valida que el resultado sea correcto
            if(validarArchivoResultados(resultado)) {
                String[] vectorResultado = resultado.split(";"); //Separa los datos con el carácter ";" y los guarda en un array

                //Si nroRonda está vacío, le asigna el número de ronda actual
                if(nroRonda.isEmpty()) { nroRonda = vectorResultado[0];}

                //Si el nro de ronda es diferente al actual, crea una nueva ronda y agrega los partidos de la ronda
                if(!vectorResultado[0].equals(nroRonda)) {
                    Ronda ronda = new Ronda(); // Crea un objeto de tipo "Ronda"
                    Partido[] partidos = new Partido[partidosRonda.size()]; // Crea un array de tipo "Partido"
                    ronda.setNro(nroRonda); // Se asigna el número de ronda al objeto tipo "Ronda"
                    ronda.setPartidos(partidosRonda.toArray(partidos)); //Se asigna los partidos al objeto tipo "Ronda"
                    rondas.add(ronda); //Agrega el objeto "Ronda" a la lista de rondas
                    partidosRonda.clear(); //Borra el contenido del array list de partidos para la siguiente ronda
                    nroRonda = vectorResultado[0]; //Asigna el número de ronda de la siguiente ronda
                }
                //Crea los objetos equipos y el partido con los datos de la línea del archivo
                Equipo equipo1 = new Equipo();
                equipo1.setNombre(vectorResultado[1]);
                Equipo equipo2 = new Equipo();
                equipo2.setNombre(vectorResultado[4]);
                Partido partido = new Partido();
                partido.setEquipo1(equipo1);
                partido.setEquipo2(equipo2);
                partido.setGolesEquipo1(Integer.parseInt(vectorResultado[2])); //Convierte los goles del equipo 1 que es de tipo String a int
                partido.setGolesEquipo2(Integer.parseInt(vectorResultado[3])); //Convierte los goles del equipo 2 que es de tipo String a int
                partidosRonda.add(partido); //Agrega el partido a la lista de partidos de la ronda actual
            }
        }
        //Crea la última ronda y agregamos los partidos de la misma
        Ronda ronda = new Ronda();
        Partido[] partidos = new Partido[partidosRonda.size()];
        ronda.setNro(nroRonda);
        ronda.setPartidos(partidosRonda.toArray(partidos));
        rondas.add(ronda);

        //Retorna la lista de rondas como un array de Ronda
        return rondas.toArray(new Ronda[rondas.size()]);
    }
    /**
     * Método validarArchivoResultados
     * Valida que una línea del archivo de resultados tenga el formato correcto.
     * @param resultado La línea del archivo de resultados a validar.
     * @return true si la línea es válida, false si no lo es.
     */
    private static boolean validarArchivoResultados(String resultado) {
        String[] contenidoLinea = resultado.split(";"); //separa la línea en un array de strings usando el separador ";"
        if (contenidoLinea.length != 5) { //verifica que la línea tenga 5 columnas
            System.out.println("**ERROR** Cantidad de columnas incorrectas del archivo Resultados");
            return false; //si no tiene 5 columnas, retorna false
        }
        try {
            Integer.parseInt(contenidoLinea[2]); //convierte goles equipo 1 a un entero
            Integer.parseInt(contenidoLinea[3]); //convierte goles equipo 2 a un entero
        } catch (Exception e) { //si ocurre una excepción al convertir a entero
            System.out.println("**ERROR** validación de goles incorrecta");
            return false; //retorna false
        }
        return true; //si todo está bien, retorna true
    }
    /**
     * Carga los pronósticos de los apostadores para cada partido de cada ronda.
     * @param archivoPronosticos El archivo de pronósticos a cargar.
     * @param rondas Las rondas de partidos.
     * @param apostadores Los apostadores que realizaron los pronósticos.
     * @return Un array de Pronostico con los pronósticos cargados.
     */
    private static Pronostico[] cargarPronostico(String archivoPronosticos, Ronda[] rondas, List<Apostador> apostadores) {
        List<String> pronosticos = new ArrayList<String>();
        try {
            pronosticos = Files.readAllLines(Paths.get(archivoPronosticos), Charset.forName("ISO-8859-1")); //Lee el archivo pronósticos.csv y lo almacena en una lista
            pronosticos.remove(0); //Se elimina el primer elemento de la lista, que contiene los encabezados del archivo.
        } catch (IOException e) {
            System.out.println("Error al leer el archivo");
            e.printStackTrace();
        }
        List<Pronostico> pronosticosRonda = new ArrayList<Pronostico>();
        int x = 0;
        for (Ronda ronda : rondas) { //recorre cada ronda de partidos

            for (Apostador apostador : apostadores) { //recorre cada apostador

                Partido[] partidosRonda = ronda.getPartidos(); //obtiene los partidos de la ronda actual
                for (Partido partido : partidosRonda) { //recorre cada partido de la ronda actual

                    if (validarArchivoPronosticos(pronosticos.get(x))) { //valida que el pronóstico sea correcto
                        Pronostico pronostico = new Pronostico(); //crea un nuevo pronóstico
                        ResultadoEnum resultadoPronostico; //crea una variable para almacenar el resultado del pronóstico

                        if (!pronosticos.get(x).split(";")[3].isEmpty()) { //si el pronóstico tiene un ganador
                            resultadoPronostico = ResultadoEnum.ganador;
                        } else {
                            if (!pronosticos.get(x).split(";")[5].isEmpty()) { //si el pronóstico tiene un perdedor
                                resultadoPronostico = ResultadoEnum.perdedor;
                            } else { //si el pronóstico es un empate
                                resultadoPronostico = ResultadoEnum.empate;
                            }
                        }
                        pronostico.setPartido(partido); //asigna el partido al pronóstico
                        pronostico.setApostador(apostador); //asigna el apostador al pronóstico
                        pronostico.setEquipo(partido.getEquipo1()); //asigna el equipo 1 del partido al pronóstico
                        pronostico.setResultado(resultadoPronostico); //asigna el resultado del pronóstico al pronóstico
                        pronosticosRonda.add(pronostico); //agrega el pronóstico a la lista de pronósticos de la ronda actual
                    }
                    x++; //incrementa el contador de pronósticos
                }
            }
        }
        Pronostico[] pronosticosArray = new Pronostico[pronosticosRonda.size()]; //crea un array de Pronostico con el tamaño de la lista de pronósticos de la ronda actual
        return pronosticosRonda.toArray(pronosticosArray); //convierte la lista de pronósticos de la ronda actual en un array de Pronostico y lo retorna
    }
    /**
     * Asigna los pronósticos correspondientes a cada apostador.
     * @param apostadores Una lista de Apostador con los apostadores a los que se les asignarán los pronósticos.
     * @param pronosticos Un array de Pronostico con los pronósticos a asignar.
     */
    private static void asignarPronosticos(List<Apostador> apostadores, Pronostico[] pronosticos) {
        // Crea una lista vacía para guardar los pronósticos de cada apostador.
        List<Pronostico> pronosticoApostadores = new ArrayList<Pronostico>();
        // Recorre la lista de apostadores.
        for (Apostador apostador : apostadores) {
            // Recorre el array de pronósticos.
            for (Pronostico pronostico : pronosticos) {
                // Si el apostador del pronóstico es igual al apostador actual, agrega el pronóstico a la lista de pronósticos del apostador.
                if (pronostico.getApostador().equals(apostador)) {
                    pronosticoApostadores.add(pronostico);
                }
            }
            // Convierte la lista de pronósticos del apostador en un array y se lo asigna al apostador.
            Pronostico[] pronosticosApostador = new Pronostico[pronosticoApostadores.size()];
            apostador.setPronosticos(pronosticoApostadores.toArray(pronosticosApostador));
            // Limpia la lista de pronósticos del apostador para el siguiente ciclo.
            pronosticoApostadores.clear();
        }
    }
    /**
     * Valida que el archivo de pronósticos tenga la cantidad correcta de columnas.
     * @param linea El contenido de una línea del archivo de pronósticos.
     * @return true si la cantidad de columnas es correcta, false si no lo es.
     */
    private static boolean validarArchivoPronosticos(String linea) {
        // Separa el contenido de la línea por el carácter ";" y lo guarda en un array.
        String[] contenidoLinea = linea.split(";");
        // Si la cantidad de elementos en el array no es 7, muestra un mensaje de error.
        if(contenidoLinea.length !=7) {
            System.out.println("**ERROR** Cantidad de columnas incorrectas del archivo Pronóstico");
        }
        // Retorna true si la cantidad de columnas es correcta, false si no lo es.
        return true;
    }
}
