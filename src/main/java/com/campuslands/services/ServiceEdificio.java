package com.campuslands.services;

import java.util.List;
import java.util.Scanner;
import com.campuslands.models.edificio;
import com.campuslands.repositories.edificioImpl;
import com.campuslands.repositories.repository;

public class ServiceEdificio implements Services<edificio> {
    public static Scanner leer = new Scanner(System.in);
    @SuppressWarnings("rawtypes")
    private static final repository edificioRepositorio = new edificioImpl();

    public void limpiar(){
        leer.nextLine();
    }

    @Override
    public edificio datos(){
        edificio c = new edificio();

        System.out.print("Digite el nombre del edificio --> ");
        String nom_edi = leer.nextLine();
        
        System.out.print("Digite el numero de pisos --> ");
        int num_pis = leer.nextInt();

        c.setEdificio_nombre(nom_edi);
        c.setEdificio_pisos(num_pis);

        return c;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void guardar(){
        limpiar();
        edificioRepositorio.guardar(datos());
        System.out.println("\nDatos guardados exitosamente");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void modificar(){
        limpiar();
        System.out.print("Digite el id del edificio que desea modificar --> ");
        int id_edificio = leer.nextInt();
        leer.nextLine();
        edificio c = datos();
        c.setEdificio_id(id_edificio);
        edificioRepositorio.modificar(c);
        System.out.println("\nDatos modificados exitosamente");
    }

    @Override
    public void eliminar(){
        limpiar();
        System.out.print("Digite el id del edificio que desea eliminar --> ");
        int id_edificio = leer.nextInt();
        edificioRepositorio.eliminar(id_edificio);
        System.out.println("\nDatos eliminados exitosamente");
    }

    @Override
    public void buscar(){
        limpiar();
        System.out.print("Digite el id del edificio que desea buscar --> ");
        int id_edificio = leer.nextInt();
        edificio c = (edificio) edificioRepositorio.porCodigo(id_edificio);
        System.out.println("=======================================");
        System.out.println("Id edificio: " + c.getEdificio_id());
        System.out.println("Nombre: " + c.getEdificio_nombre());
        System.out.println("Numero de pisos: " + c.getEdificio_pisos());
        System.out.println("=======================================");
        System.out.println("\nDatos mostrados exitosamente");
    }

    @Override
    public void listar(){
        @SuppressWarnings("unchecked")
        List<edificio> listaedificioes = edificioRepositorio.listar();
        for(edificio c: listaedificioes){
            System.out.println("=======================================");
            System.out.println("Id edificio: " + c.getEdificio_id());
            System.out.println("Nombre: " + c.getEdificio_nombre());
            System.out.println("Numero de pisos: " + c.getEdificio_pisos());
            System.out.println("=======================================");
        }
    }

    @Override
    public void menu(){
        byte d = 0;
        do{
            System.out.println("\n=======================================");
            System.out.println("MENU\n");
            System.out.println("1. Guardar datos de edificio");
            System.out.println("2. Modificar datos de edificio");
            System.out.println("3. Eliminar datos de edificio");
            System.out.println("4. Buscar datos de edificio");
            System.out.println("5. Listar datos de edificio");
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
