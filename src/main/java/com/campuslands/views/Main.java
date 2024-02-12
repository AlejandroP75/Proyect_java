package com.campuslands.views;

import java.util.Scanner;
import com.campuslands.services.Services;

public class Main {
    public static Scanner leer = new Scanner(System.in);
    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {
        conexion_fabrica fabrica = new conexion_fabrica();

        int op = 0;
        while (true) {
            System.out.println("=======================================");
            System.out.println("1.Alumno");
            System.out.println("2.Asignatura");
            System.out.println("3.Ciudad");
            System.out.println("4.Curso");
            System.out.println("5.Departamento");
            System.out.println("6.Direccion");
            System.out.println("7.Edificio");
            System.out.println("8.Horario");
            System.out.println("9.Matricula");
            System.out.println("10.Periodo");
            System.out.println("11.Persona");
            System.out.println("12.Profesor");
            System.out.println("13.Programa");
            System.out.println("14.Salon");
            System.out.println("15.Tarifa");
            System.out.println("16.reportes");
            System.out.println("=======================================");
            System.out.print("Ingrese la opcion que desea--> ");
            op = leer.nextInt();

            if(op < 1 || op > 15){
                System.out.println("Seleccione una opcion valida, vuelva a intentarlo");
                continue;
            }
            break;
        }
        switch (op) {
            case 1 -> {
                Services alumno = fabrica.getConexion("alumnos");
                alumno.menu();
            }
            case 2 -> {
                Services asignatura = fabrica.getConexion("asignaturas");
                asignatura.menu();
            }
            case 3 -> {
                Services ciudad = fabrica.getConexion("ciudades");
                ciudad.menu();
            }
            case 4 -> {
                Services curso = fabrica.getConexion("cursos");
                curso.menu();
            }
            case 5 -> {
                Services departamento = fabrica.getConexion("departamentos");
                departamento.menu();
            }
            case 6 -> {
                Services direccion = fabrica.getConexion("direcciones");
                direccion.menu();
            }
            case 7 -> {
                Services edificio = fabrica.getConexion("edificios");
                edificio.menu();
            }
            case 8 -> {
                Services horario = fabrica.getConexion("horarios");
                horario.menu();
            }
            case 9 -> {
                Services matricula = fabrica.getConexion("matriculas");
                matricula.menu();
            }
            case 10 -> {
                Services periodo = fabrica.getConexion("periodos");
                periodo.menu();
            }
            case 11 -> {
                Services persona = fabrica.getConexion("personas");
                persona.menu();
            }
            case 12 -> {
                Services profesor = fabrica.getConexion("profesores");
                profesor.menu();
            }
            case 13 -> {
                Services programa = fabrica.getConexion("programas");
                programa.menu();
            }
            case 14 -> {
                Services salon = fabrica.getConexion("salones");
                salon.menu();
            }
            case 15 -> {
                Services tarifa = fabrica.getConexion("tarifas");
                tarifa.menu();
            }
            default -> fabrica.getConexion("Se equivoco");
        };      

    }
}