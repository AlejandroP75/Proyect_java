package com.campuslands.services;

import java.util.List;
import java.util.Scanner;
import com.campuslands.models.matricula;
import com.campuslands.repositories.matriculaImpl;
import com.campuslands.repositories.repository;

public class ServiceMatricula implements Services<matricula> {
    public static Scanner leer = new Scanner(System.in);
    @SuppressWarnings("rawtypes")
    private static final repository matriculaRepositorio = new matriculaImpl();

    public void limpiar(){
        leer.nextLine();
    }

    @Override
    public matricula datos(){
        matricula c = new matricula();

        System.out.print("Digite el id del alumno --> ");
        int id_alu = leer.nextInt();
        
        System.out.print("Digite el id de la asignatura --> ");
        int id_asi = leer.nextInt();

        System.out.print("Digite el id del periodo --> ");
        int id_per = leer.nextInt();

        c.setAlumno_id(id_alu);
        c.setAsignatura_id(id_asi);
        c.setPeriodo_id(id_per);

        return c;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void guardar(){
        limpiar();
        matriculaRepositorio.guardar(datos());
        System.out.println("\nDatos guardados exitosamente");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void modificar(){
        limpiar();
        System.out.print("Digite el id de la matricula que desea modificar --> ");
        int id_matricula = leer.nextInt();
        leer.nextLine();
        matricula c = datos();
        c.setMatricula_id(id_matricula);
        matriculaRepositorio.modificar(c);
        System.out.println("\nDatos modificados exitosamente");
    }

    @Override
    public void eliminar(){
        limpiar();
        System.out.print("Digite el id de la matricula que desea eliminar --> ");
        int id_matricula = leer.nextInt();
        matriculaRepositorio.eliminar(id_matricula);
        System.out.println("\nDatos eliminados exitosamente");
    }

    @Override
    public void buscar(){
        limpiar();
        System.out.print("Digite el id de la matricula que desea buscar --> ");
        int id_matricula = leer.nextInt();
        matricula c = (matricula) matriculaRepositorio.porCodigo(id_matricula);
        System.out.println("=======================================");
        System.out.println("Id matricula: " + c.getMatricula_id());
        System.out.println("Id alumno: " + c.getAlumno_id());
        System.out.println("Id asignatura: " + c.getAsignatura_id());
        System.out.println("Id periodo: " + c.getPeriodo_id());
        System.out.println("=======================================");
        System.out.println("\nDatos mostrados exitosamente");
    }

    @Override
    public void listar(){
        @SuppressWarnings("unchecked")
        List<matricula> listamatriculaes = matriculaRepositorio.listar();
        for(matricula c: listamatriculaes){
            System.out.println("=======================================");
            System.out.println("Id matricula: " + c.getMatricula_id());
            System.out.println("Id alumno: " + c.getAlumno_id());
            System.out.println("Id asignatura: " + c.getAsignatura_id());
            System.out.println("Id periodo: " + c.getPeriodo_id());
            System.out.println("=======================================");
        }
    }

    @Override
    public void menu(){
        byte d = 0;
        do{
            System.out.println("\n=======================================");
            System.out.println("MENU\n");
            System.out.println("1. Guardar datos de matricula");
            System.out.println("2. Modificar datos de matricula");
            System.out.println("3. Eliminar datos de matricula");
            System.out.println("4. Buscar datos de matricula");
            System.out.println("5. Listar datos de matricula");
            System.out.println("======================================="); 
            System.out.print("\nDigite la opción deseada --> ");
            d = leer.nextByte();
            
            switch (d) {
                case 1:
                    guardar();
                    break;
                case 2:
                    modificar();
                    break;
                case 3:
                    eliminar();
                    break;
                case 4:
                    buscar();
                    break;
                case 5:
                    listar();
                    break;
                default:
                    System.out.println("ERROR, opción no valida");
                    break;
            }
        }while(d != 0);
        
    }
}