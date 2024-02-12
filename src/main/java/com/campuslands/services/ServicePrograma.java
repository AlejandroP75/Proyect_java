package com.campuslands.services;

import java.util.List;
import java.util.Scanner;
import com.campuslands.models.programa;
import com.campuslands.repositories.programaImpl;
import com.campuslands.repositories.repository;

public class ServicePrograma implements Services<programa> {
    public static Scanner leer = new Scanner(System.in);
    @SuppressWarnings("rawtypes")
    private static final repository programaRepositorio = new programaImpl();

    public void limpiar(){
        leer.nextLine();
    }

    @Override
    public programa datos(){
        programa c = new programa();

        System.out.print("Digite el nombre del programa --> ");
        String nom_pro = leer.nextLine();
        
        System.out.print("Digite el nivel del programa --> ");
        String niv_pro = leer.nextLine();

        c.setPrograma_nombre(nom_pro);
        c.setPrograma_nivel(niv_pro);

        return c;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void guardar(){
        limpiar();
        programaRepositorio.guardar(datos());
        System.out.println("\nDatos guardados exitosamente");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void modificar(){
        limpiar();
        System.out.print("Digite el id del programa que desea modificar --> ");
        int id_programa = leer.nextInt();
        leer.nextLine();
        programa c = datos();
        c.setPrograma_id(id_programa);
        programaRepositorio.modificar(c);
        System.out.println("\nDatos modificados exitosamente");
    }

    @Override
    public void eliminar(){
        limpiar();
        System.out.print("Digite el id del programa que desea eliminar --> ");
        int id_programa = leer.nextInt();
        programaRepositorio.eliminar(id_programa);
        System.out.println("\nDatos eliminados exitosamente");
    }

    @Override
    public void buscar(){
        limpiar();
        System.out.print("Digite el id del programa que desea buscar --> ");
        int id_programa = leer.nextInt();
        programa c = (programa) programaRepositorio.porCodigo(id_programa);
        System.out.println("=======================================");
        System.out.println("Id programa: " + c.getPrograma_id());
        System.out.println("Nombre programa: " + c.getPrograma_nombre());
        System.out.println("Nivel programa: " + c.getPrograma_nivel());
        System.out.println("=======================================");
        System.out.println("\nDatos mostrados exitosamente");
    }

    @Override
    public void listar(){
        @SuppressWarnings("unchecked")
        List<programa> listaprogramaes = programaRepositorio.listar();
        for(programa c: listaprogramaes){
            System.out.println("=======================================");
            System.out.println("Id programa: " + c.getPrograma_id());
            System.out.println("Nombre programa: " + c.getPrograma_nombre());
            System.out.println("Nivel programa: " + c.getPrograma_nivel());
            System.out.println("=======================================");
        }
    }

    @Override
    public void menu(){
        byte d = 0;
        do{
            System.out.println("\n=======================================");
            System.out.println("MENU\n");
            System.out.println("1. Guardar datos de programa");
            System.out.println("2. Modificar datos de programa");
            System.out.println("3. Eliminar datos de programa");
            System.out.println("4. Buscar datos de programa");
            System.out.println("5. Listar datos de programa");
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
