package com.campuslands.views;

import java.util.Scanner;

import com.campuslands.services.ServiceReportes;
import com.campuslands.services.Services;

public class Main extends ServiceReportes{
    public static Scanner leer = new Scanner(System.in);
    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {
        conexion_fabrica fabrica = new conexion_fabrica();

        int op = 0;
        while (true) {
            clearScreen();
            System.out.println("=======================================");
            System.out.println("MENU PRINCIPAL\n");
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
            System.out.println("16.Reportes");
            System.out.println("=======================================");
            System.out.print("Ingrese la opcion que desea--> ");
            op = leer.nextInt();

            if(op < 1 || op > 16){
                System.out.println("Seleccione una opcion valida, vuelva a intentarlo");
                continue;
            }
            break;
        }
        switch (op) {
            case 1 -> {
                Services alumno = fabrica.getConexion("alumnos");
                clearScreen();
                alumno.menu();
            }
            case 2 -> {
                Services asignatura = fabrica.getConexion("asignaturas");
                clearScreen();
                asignatura.menu();
            }
            case 3 -> {
                Services ciudad = fabrica.getConexion("ciudades");
                clearScreen();
                ciudad.menu();
            }
            case 4 -> {
                Services curso = fabrica.getConexion("cursos");
                clearScreen();
                curso.menu();
            }
            case 5 -> {
                Services departamento = fabrica.getConexion("departamentos");
                clearScreen();
                departamento.menu();
            }
            case 6 -> {
                Services direccion = fabrica.getConexion("direcciones");
                clearScreen();
                direccion.menu();
            }
            case 7 -> {
                Services edificio = fabrica.getConexion("edificios");
                clearScreen();
                edificio.menu();
            }
            case 8 -> {
                Services horario = fabrica.getConexion("horarios");
                clearScreen();
                horario.menu();
            }
            case 9 -> {
                Services matricula = fabrica.getConexion("matriculas");
                clearScreen();
                matricula.menu();
            }
            case 10 -> {
                Services periodo = fabrica.getConexion("periodos");
                clearScreen();
                periodo.menu();
            }
            case 11 -> {
                Services persona = fabrica.getConexion("personas");
                clearScreen();
                persona.menu();
            }
            case 12 -> {
                Services profesor = fabrica.getConexion("profesores");
                clearScreen();
                profesor.menu();
            }
            case 13 -> {
                Services programa = fabrica.getConexion("programas");
                clearScreen();
                programa.menu();
            }
            case 14 -> {
                Services salon = fabrica.getConexion("salones");
                clearScreen();
                salon.menu();
            }
            case 15 -> {
                Services tarifa = fabrica.getConexion("tarifas");
                clearScreen();
                tarifa.menu();
            }
            case 16 -> {
                clearScreen();
                menuReportes();
            }
            default -> fabrica.getConexion("Se equivoco");
        };      

    }
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    } 
}