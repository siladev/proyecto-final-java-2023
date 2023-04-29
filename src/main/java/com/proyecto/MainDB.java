package com.proyecto;

import com.proyecto.clasesE3.ConfiguracionPuntos;

import static com.proyecto.conexion.ConectorSQL.DB_URL;
import static com.proyecto.conexion.ConectorSQL.USER;
import static com.proyecto.conexion.ConectorSQL.PASS;

import java.sql.*;

public class MainDB {
    private static Connection conexion = null;

    public static void main(String[] args) throws SQLException {

        // Se lee el archivo de configuración
        ConfiguracionPuntos config = ConfiguracionPuntos.cargarConfiguracion();

        System.out.println("Conectando a la base de datos...\n");
        // Se establece la conexión a la base de datos
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.println("Conexión exitosa a la base de datos\n");
            // Ejemplo de consulta para obtener los puntos de los apostadores por ronda
            String sql = "SELECT\n" +
                    "  `p`.`Apostador_Id`,`Apostador_nombre`, COUNT((CASE WHEN (CASE WHEN (`res`.`golesEquipo1` > `res`.`golesEquipo2`) THEN (`p`.`ganaEquipo1` = 'X') WHEN (`res`.`golesEquipo1` = `res`.`golesEquipo2`) THEN (`p`.`empate` = 'X') WHEN (`res`.`golesEquipo1` < `res`.`golesEquipo2`) THEN (`p`.`ganaEquipo2` = 'X') END) THEN 1 ELSE NULL END)) AS 'Aciertos', `r`.`Id` AS 'Ronda', (CASE WHEN (COUNT((CASE WHEN (CASE WHEN (`res`.`golesEquipo1` > `res`.`golesEquipo2`) THEN (`p`.`ganaEquipo1` = 'X') WHEN (`res`.`golesEquipo1` = `res`.`golesEquipo2`) THEN (`p`.`empate` = 'X') WHEN (`res`.`golesEquipo1` < `res`.`golesEquipo2`) THEN (`p`.`ganaEquipo2` = 'X') END) THEN 1 ELSE NULL END)) >= 3) THEN 5 ELSE 0 END) AS 'PuntosExtra'\n" +
                    "FROM\n" +
                    "  ((`pronostico` p\n" +
                    "    JOIN `resultados` res ON (((`p`.`Ronda_Id` = `res`.`Ronda`) AND (`p`.`Equipo1_Id` = `res`.`Equipo1_Id`) AND (`p`.`Equipo2_Id` = `res`.`Equipo2_Id`))))\n" +
                    "    JOIN `ronda` r ON ((`r`.`Id` = `res`.`Ronda`)))\n" +
                    "GROUP BY\n" +
                    "  `p`.`Apostador_Id`, `p`.`Apostador_nombre`,`r`.`Id`\n" +
                    "ORDER BY `p`.`Apostador_Id`, `r`.`Id`;";

            // Imprimir los puntos de los apostadores por ronda
            imprimirPuntosPorRonda(conn, sql);


            // Se lee la tabla de pronósticos
            String sqlPronosticos = "SELECT * FROM pronostico";
            try (PreparedStatement stmtPronosticos = conn.prepareStatement(sqlPronosticos)) {
                ResultSet rsPronosticos = stmtPronosticos.executeQuery();
                while (rsPronosticos.next()) {
                    String apostadorId = rsPronosticos.getString("Apostador_Id");
                    int rondaId = rsPronosticos.getInt("Ronda_Id");
                    int equipo1Id = rsPronosticos.getInt("Equipo1_Id");
                    String ganaEquipo1 = rsPronosticos.getString("GanaEquipo1");
                    String empate = rsPronosticos.getString("Empate");
                    String ganaEquipo2 = rsPronosticos.getString("GanaEquipo2");
                    int equipo2Id = rsPronosticos.getInt("Equipo2_Id");

                    // Se procesa el pronóstico
                    int puntosApostador = 0;
                    int golesEquipo1 = 0; // Variable para almacenar los goles del equipo 1
                    int golesEquipo2 = 0; // Variable para almacenar los goles del equipo 2
                    if (ganaEquipo1 != null && ganaEquipo1.equals("X")) {
                        puntosApostador += config.getPuntosGanador();
                    } else if (ganaEquipo1 != null && ganaEquipo2.equals("X")) {
                        puntosApostador += config.getPuntosGanador();
                    } else if (ganaEquipo1 != null && empate.equals("X")) {
                        puntosApostador += config.getPuntosEmpate();
                    } else {
                        puntosApostador += config.getPuntosPerdedor();
                    }

                    // Se verifica si se acertó toda la ronda
                    boolean acertoTodaLaRonda1 = true;
                    boolean acertoTodaLaRonda2 = true;
                    if (rondaId == 1) {
                        int fase = 1;
                        int ronda = 1;
                        String sqlResultados = "SELECT * FROM resultados WHERE fase = 1 AND Ronda = 1";
                        try (PreparedStatement stmtResultados = conn.prepareStatement(sqlResultados)) {
                            ResultSet rsResultados = stmtResultados.executeQuery();
                            while (rsResultados.next()) {
                                int equipo1IdResultado = rsResultados.getInt("Equipo1_Id");
                                int golesEquipo1Resultado = rsResultados.getInt("golesEquipo1");
                                int golesEquipo2Resultado = rsResultados.getInt("golesEquipo2");
                                int equipo2IdResultado = rsResultados.getInt("Equipo2_Id");

                                String ganaEquipo1Resultado = golesEquipo1Resultado > golesEquipo2Resultado ? "S" : "N";
                                String empateResultado = golesEquipo1Resultado == golesEquipo2Resultado ? "S" : "N";
                                String ganaEquipo2Resultado = golesEquipo2Resultado > golesEquipo1Resultado ? "S" : "N";

                                String sqlPronosticosRonda = "SELECT * FROM pronostico WHERE Ronda_Id = 1 or Ronda_Id = 2 ";
                                try (PreparedStatement stmtPronosticosRonda = conn.prepareStatement(sqlPronosticosRonda)) {
                                    ResultSet rsPronosticosRonda = stmtPronosticosRonda.executeQuery();
                                    while (rsPronosticosRonda.next()) {
                                        String ganaEquipo1Pronostico = rsPronosticosRonda.getString("GanaEquipo1");
                                        String empatePronostico = rsPronosticosRonda.getString("Empate");
                                        String ganaEquipo2Pronostico = rsPronosticosRonda.getString("GanaEquipo2");

                                        if (!ganaEquipo1Resultado.equals(ganaEquipo1Pronostico) ||
                                                !empateResultado.equals(empatePronostico) ||
                                                !ganaEquipo2Resultado.equals(ganaEquipo2Pronostico)) {
                                            acertoTodaLaRonda1 = false;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    } else if (rondaId == 2) {
                        int fase = 1;
                        int ronda = 2;
                        String sqlResultados = "SELECT * FROM resultados WHERE fase = 1 AND ronda = 2";
                        try (PreparedStatement stmtResultados = conn.prepareStatement(sqlResultados)) {
                            ResultSet rsResultados = stmtResultados.executeQuery();
                            while (rsResultados.next()) {
                                int equipo1IdResultado = rsResultados.getInt("Equipo1_Id");
                                int golesEquipo1Resultado = rsResultados.getInt("golesEquipo1");
                                int golesEquipo2Resultado = rsResultados.getInt("golesEquipo2");
                                int equipo2IdResultado = rsResultados.getInt("Equipo2_Id");

                                String ganaEquipo1Resultado = golesEquipo1Resultado > golesEquipo2Resultado ? "S" : "N";
                                String empateResultado = golesEquipo1Resultado == golesEquipo2Resultado ? "S" : "N";
                                String ganaEquipo2Resultado = golesEquipo2Resultado > golesEquipo1Resultado ? "S" : "N";

                                String sqlPronosticosRonda = "SELECT * FROM pronostico WHERE Ronda_Id = 2";
                                try (PreparedStatement stmtPronosticosRonda = conn.prepareStatement(sqlPronosticosRonda)) {
                                    ResultSet rsPronosticosRonda = stmtPronosticosRonda.executeQuery();
                                    while (rsPronosticosRonda.next()) {
                                        String ganaEquipo1Pronostico = rsPronosticosRonda.getString("GanaEquipo1");
                                        String empatePronostico = rsPronosticosRonda.getString("Empate");
                                        String ganaEquipo2Pronostico = rsPronosticosRonda.getString("GanaEquipo2");

                                        if (!ganaEquipo1Resultado.equals(ganaEquipo1Pronostico) ||
                                                !empateResultado.equals(empatePronostico) ||
                                                !ganaEquipo2Resultado.equals(ganaEquipo2Pronostico)) {
                                            acertoTodaLaRonda2 = false;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (acertoTodaLaRonda1) {
                        puntosApostador += config.getPuntosExtrasRonda();
                    }
                    if (acertoTodaLaRonda2) {
                        puntosApostador += config.getPuntosExtrasRonda();
                    }
                    // Se imprime el resultado del pronóstico
                    /*System.out.println("Apostador: " + apostadorId);
                    System.out.println("Puntos obtenidos: " + puntosApostador);

                    Se verifica si se acertó toda la fase sobre un equipo
                    boolean acertoTodaLaFase = true;
                    int faseId = 1;
                    String sqlRondasFase = "SELECT DISTINCT Ronda_Id FROM pronostico WHERE fase = 1";
                    try (PreparedStatement stmtRondasFase = conn.prepareStatement(sqlRondasFase)) {
                        ResultSet rsRondasFase = stmtRondasFase.executeQuery();
                        while (rsRondasFase.next()) {
                            int rondaIdFase = rsRondasFase.getInt("Ronda_Id");

                            String sqlResultadosFase = "SELECT * FROM resultados WHERE fase = 1 AND ronda = 1";
                            int fase = 1;
                            try (PreparedStatement stmtResultadosFase = conn.prepareStatement(sqlResultadosFase)) {
                                ResultSet rsResultadosFase = stmtResultadosFase.executeQuery();
                                while (rsResultadosFase.next()) {
                                    int equipo1IdResultadoFase = rsResultadosFase.getInt("Equipo1_Id");
                                    int golesEquipo1ResultadoFase = rsResultadosFase.getInt("golesEquipo1");
                                    int golesEquipo2ResultadoFase = rsResultadosFase.getInt("golesEquipo2");
                                    int equipo2IdResultadoFase = rsResultadosFase.getInt("Equipo2_Id");

                                    String ganaEquipo1ResultadoFase = golesEquipo1ResultadoFase > golesEquipo2ResultadoFase ? "S" : "N";
                                    String empateResultadoFase = golesEquipo1ResultadoFase == golesEquipo2ResultadoFase ? "S" : "N";
                                    String ganaEquipo2ResultadoFase = golesEquipo2ResultadoFase > golesEquipo1ResultadoFase ? "S" : "N";

                                    String sqlPronosticosRondaFase = "SELECT * FROM pronostico WHERE Ronda_Id = 1";
                                    try (PreparedStatement stmtPronosticosRondaFase = conn.prepareStatement(sqlPronosticosRondaFase)) {
                                        stmtPronosticosRondaFase.setInt(1, rondaIdFase);
                                        ResultSet rsPronosticosRondaFase = stmtPronosticosRondaFase.executeQuery();
                                        while (rsPronosticosRondaFase.next()) {
                                            int equipo1IdPronosticoFase = rsPronosticosRondaFase.getInt("Equipo1_Id");
                                            String ganaEquipo1PronosticoFase = rsPronosticosRondaFase.getString("GanaEquipo1");
                                            String empatePronosticoFase = rsPronosticosRondaFase.getString("Empate");
                                            int equipo2IdPronosticoFase = rsPronosticosRondaFase.getInt("Equipo2_Id");
                                            String ganaEquipo2PronosticoFase = rsPronosticosRondaFase.getString("GanaEquipo2");

                                            if (equipo1IdResultadoFase == equipo1IdPronosticoFase &&
                                                    equipo2IdResultadoFase == equipo2IdPronosticoFase &&
                                                    !ganaEquipo1ResultadoFase.equals(ganaEquipo1PronosticoFase) ||
                                                    !empateResultadoFase.equals(empatePronosticoFase) ||
                                                    !ganaEquipo2ResultadoFase.equals(ganaEquipo2PronosticoFase)) {
                                                acertoTodaLaFase = false;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }*/
                }
            }
        } catch (SQLException e) {
             System.out.println("Error al conectar con la base de datos");
             e.printStackTrace();
        }
    }

    private static void imprimirPuntosPorRonda(Connection conn, String sql) {
        System.out.println("Puntos de los apostadores por ronda:");
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String apostador = rs.getString("Apostador_Id");
                String apostadorNombre = rs.getString("Apostador_nombre");
                int ronda = rs.getInt("Ronda");
                int aciertos = rs.getInt("Aciertos");
                int puntos = rs.getInt("PuntosExtra");
                if (aciertos == 4) {
                    System.out.println(apostador + "-"+ apostadorNombre+" obtuvo 5 puntos extras en la ronda " + ronda);
                } else {
                    System.out.println(apostador + "-"+ apostadorNombre+ " obtuvo " + puntos + " puntos en la ronda " + ronda);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta");
            e.printStackTrace();
        }
    }
}