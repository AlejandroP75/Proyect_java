package com.campuslands.services;

import java.util.Scanner;

public class ServiceReportes {
    public static Scanner leer = new Scanner(System.in);

    public static void menuReportes(){
        int op = 0;
        while (true) {
            System.out.println("=======================================");
            System.out.println("1.Listar los estudiantes matriculados por programa");
            System.out.println("2.Calcular el costo del semestre de un estudiante según las asignaturas matriculadas");
            System.out.println("3.Calcular los ingreso de la universidad por semestre");
            System.out.println("4.Imprimir el horario de un estudiante. Simplemente en forma de lista");
            System.out.println("5.Imprimir en forma descendente el número de matriculados por programa");
            System.out.println("=======================================");
            System.out.print("Ingrese la opcion que desea--> ");
            op = leer.nextInt();

            if(op < 1 || op > 5){
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
        
            default:
                System.out.println("ERROR");
                break;
        }
    }
    public static void listarEstuiantes(){

    }
    public static void calcularCostoSemestre(){
        
    }
    public static void calcularIngresoSemestre(){
        
    }
    public static void imprimirHorarioEstudiante(){
        
    }
    public static void imprimirMatriculadosPrograma(){
        
    }
}
