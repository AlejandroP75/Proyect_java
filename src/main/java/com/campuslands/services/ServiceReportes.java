package com.campuslands.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.campuslands.models.alumno;
import com.campuslands.models.periodo;
import com.campuslands.models.persona;
import com.campuslands.models.programa;
import com.campuslands.repositories.alumnoImpl;
import com.campuslands.repositories.periodoImpl;
import com.campuslands.repositories.personaImpl;
import com.campuslands.repositories.programaImpl;
import com.campuslands.repositories.repository;
import com.campuslands.utils.Conexion;

public class ServiceReportes {
    public static Scanner leer = new Scanner(System.in);
    @SuppressWarnings("rawtypes")
    private static final repository programaRepositorio = new programaImpl();
    @SuppressWarnings("unchecked")
    static List<programa> listarProgramas = programaRepositorio.listar();
    @SuppressWarnings("rawtypes")
    private static final repository alumnoRepositorio = new alumnoImpl();
    @SuppressWarnings("unchecked")
    static List<alumno> listarAlumnos = alumnoRepositorio.listar();
    @SuppressWarnings("rawtypes")
    private static final repository personaRepositorio = new personaImpl();
    @SuppressWarnings("unchecked")
    static List<persona> listarPersonas = personaRepositorio.listar();
    @SuppressWarnings("rawtypes")
    private static final repository periodoRepositorio = new periodoImpl();
    @SuppressWarnings("unchecked")
    static List<periodo> listarPeriodos = periodoRepositorio.listar();

    private static final Connection connection = Conexion.getInstance().conectar();

    public static void menuReportes(){
        int op = 0;
        while (true) {
            System.out.println("=======================================");
            System.out.println("1.Listar los estudiantes matriculados por programa");
            System.out.println("2.Calcular el costo del semestre de un estudiante según las asignaturas matriculadas");
            System.out.println("3.Calcular los ingreso de la universidad por semestre");
            System.out.println("4.Imprimir el horario de un estudiante. Simplemente en forma de lista");
            System.out.println("5.Imprimir en forma descendente el número de matriculados por programa");
            System.out.println("6.Salir");
            System.out.println("=======================================");
            System.out.print("Ingrese la opcion que desea--> ");
            op = leer.nextInt();

            if(op < 1 || op > 6){
                System.out.println("Seleccione una opcion valida, vuelva a intentarlo");
                continue;
            }
            break;
        }
        switch (op) {
            case 1:
                listarEstuiantes();
                break;
            case 2:
                calcularCostoSemestre();
                break;
            case 3:
                calcularIngresoSemestre();
                break;
            case 4:
                imprimirHorarioEstudiante();
                break;
            case 5:
                imprimirMatriculadosPrograma();
                break;
            case 6:
                return;
            default:
                System.out.println("ERROR");
                break;
        }
    }
    public static void listarEstuiantes(){
        for (programa p : listarProgramas) {
            System.out.println("\n" + p.getPrograma_nombre());
            for (alumno a : listarAlumnos) {
                if (a.getPrograma_id() == p.getPrograma_id()) {
                    for (persona pe : listarPersonas) {
                        if (a.getPersona_id() == pe.getPersona_id()) {
                            System.out.println("Id: " + pe.getPersona_id() + " " + pe.getPersona_nombres() + " " + pe.getPersona_apellidos());
                        }
                    }
                }
            }
        }
    }
    public static void calcularCostoSemestre() {
        try {
            for (alumno a : listarAlumnos) {
                String sql = "SELECT SUM(t.valor_credito * a.numero_creditos) AS costo_semestre "
                        + "FROM Matricula m " + "INNER JOIN Asignatura a ON m.id_asignatura = a.id_asignatura "
                        + "INNER JOIN Tarifa t ON a.id_periodo = t.id_periodo " + "WHERE m.id_alumno = ?";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, a.getAlumno_id());
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            double costoSemestre = resultSet.getDouble("costo_semestre");
                            System.out.println(
                                    "El costo del semestre para el estudiante con ID " + a.getAlumno_id() + " es: $"
                                            + costoSemestre);
                        } else {
                            System.out.println(
                                    "El estudiante con ID " + a.getAlumno_id() + " no está matriculado en ninguna asignatura.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void calcularIngresoSemestre() {
        try {
            for (periodo p : listarPeriodos) {
                String sql = "SELECT SUM(t.valor_credito * a.numero_creditos) AS ingresos_semestre "
                        + "FROM Matricula m " + "INNER JOIN Asignatura a ON m.id_asignatura = a.id_asignatura "
                        + "INNER JOIN Tarifa t ON a.id_periodo = t.id_periodo " + "WHERE m.id_periodo = ?";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, p.getPeriodo_id());
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            double ingresosSemestre = resultSet.getDouble("ingresos_semestre");
                            System.out.println("Los ingresos de la universidad para el periodo con ID " + p.getPeriodo_id() + " son: $" + ingresosSemestre);
                        } else {
                            System.out.println("No hay matrículas registradas para el semestre con ID " + p.getPeriodo_id());
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void imprimirHorarioEstudiante() {

        System.out.print("Ingrese el id del estudiante --> ");
        int idAlumno = leer.nextInt();

        String sql = "SELECT DISTINCT a.nombre_asignatura, h.dia, h.hora_inicio, h.hora_fin " +
        "FROM Matricula m " +
        "INNER JOIN Asignatura a ON m.id_asignatura = a.id_asignatura " +
        "INNER JOIN Horario h ON a.id_asignatura = h.id_asignatura " +
        "WHERE m.id_alumno = ?";
            
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAlumno);
            
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("Horario del alumno con ID " + idAlumno + ":");
                while (rs.next()) {
                    String nombreAsignatura = rs.getString("nombre_asignatura");
                    String dia = rs.getString("dia");
                    String horaInicio = rs.getString("hora_inicio");
                    String horaFin = rs.getString("hora_fin");
                    String horario = dia + ", " + horaInicio + " - " + horaFin;
                    System.out.println(nombreAsignatura + ": " + horario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void imprimirMatriculadosPrograma() {

        String sql = "SELECT p.nombre_programa, COUNT(*) AS num_matriculados " +
                        "FROM Alumno a " +
                        "INNER JOIN Programa p ON a.id_programa = p.id_programa " +
                        "GROUP BY p.nombre_programa " +
                        "ORDER BY num_matriculados";


        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("Número de personas matriculadas en cada programa de menor a mayor:\n");
                while (rs.next()) {
                    String nombrePrograma = rs.getString("nombre_programa");
                    int numMatriculados = rs.getInt("num_matriculados");
                    System.out.println(nombrePrograma + ": " + numMatriculados + " matriculados");
                }
            }
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
