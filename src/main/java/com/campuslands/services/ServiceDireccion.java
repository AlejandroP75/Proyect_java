package com.campuslands.services;

import java.util.List;
import java.util.Scanner;
import com.campuslands.models.direccion;
import com.campuslands.repositories.direccionImpl;
import com.campuslands.repositories.repository;

public class ServiceDireccion implements Services<direccion> {
    public static Scanner leer = new Scanner(System.in);
    @SuppressWarnings("rawtypes")
    private static final repository direccionRepositorio = new direccionImpl();

    public void limpiar(){
        leer.nextLine();
    }

    @Override
    public direccion datos(){
        direccion c = new direccion();
        System.out.print("Digite la direccion --> ");
        String direccion = leer.nextLine();
        c.setDireccion(direccion);
        return c;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void guardar(){
        limpiar();
        direccionRepositorio.guardar(datos());
        System.out.println("\nDatos guardados exitosamente");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void modificar(){
        limpiar();
        System.out.print("Digite el id de la direccion que desea modificar --> ");
        int id_direccion = leer.nextInt();
        leer.nextLine();
        direccion c = datos();
        c.setDireccion_id(id_direccion);
        direccionRepositorio.modificar(c);
        System.out.println("\nDatos modificados exitosamente");
    }

    @Override
    public void eliminar(){
        limpiar();
        System.out.print("Digite el id de la direccion que desea eliminar --> ");
        int id_direccion = leer.nextInt();
        direccionRepositorio.eliminar(id_direccion);
        System.out.println("\nDatos eliminados exitosamente");
    }

    @Override
    public void buscar(){
        limpiar();
        System.out.print("Digite el id de la direccion que desea buscar --> ");
        int id_direccion = leer.nextInt();
        direccion c = (direccion) direccionRepositorio.porCodigo(id_direccion);
        System.out.println("=======================================");
        System.out.println("Id direccion: " + c.getDireccion_id());
        System.out.println("Direccion: " + c.getDireccion());
        System.out.println("=======================================");
        System.out.println("\nDatos mostrados exitosamente");
    }

    @Override
    public void listar(){
        @SuppressWarnings("unchecked")
        List<direccion> listadirecciones = direccionRepositorio.listar();
        for(direccion c: listadirecciones){
            System.out.println("=======================================");
            System.out.println("Id direccion: " + c.getDireccion_id());
            System.out.println("Nombre direccion: " + c.getDireccion());
            System.out.println("=======================================");
        }
    }

    @Override
    public void menu(){
        byte d = 0;
        do{
            System.out.println("\n=======================================");
            System.out.println("MENU\n");
            System.out.println("1. Guardar datos de direccion");
            System.out.println("2. Modificar datos de direccion");
            System.out.println("3. Eliminar datos de direccion");
            System.out.println("4. Buscar datos de direccion");
            System.out.println("5. Listar datos de direccion");
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
