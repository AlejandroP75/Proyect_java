package com.campuslands.services;

import java.util.List;
import java.util.Scanner;
import com.campuslands.models.ciudad;
import com.campuslands.repositories.ciudadImpl;
import com.campuslands.repositories.repository;

public class ServiceCiudad implements Services<ciudad> {
    public static Scanner leer = new Scanner(System.in);
    @SuppressWarnings("rawtypes")
    private static final repository ciudadRepositorio = new ciudadImpl();

    public void limpiar(){
        leer.nextLine();
    }

    @Override
    public ciudad datos(){
        ciudad c = new ciudad();
        System.out.print("Digite el nombre de la ciudad --> ");
        String nombre_ciudad = leer.nextLine();
        c.setCiudad_nombre(nombre_ciudad);
        return c;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void guardar(){
        limpiar();
        ciudadRepositorio.guardar(datos());
        System.out.println("\nDatos guardados exitosamente");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void modificar(){
        limpiar();
        System.out.print("Digite el id de la ciudad que desea modificar --> ");
        int id_ciudad = leer.nextInt();
        leer.nextLine();
        ciudad c = datos();
        c.setCiudad_id(id_ciudad);
        ciudadRepositorio.modificar(c);
        System.out.println("\nDatos modificados exitosamente");
    }

    @Override
    public void eliminar(){
        limpiar();
        System.out.print("Digite el id de la ciudad que desea eliminar --> ");
        int id_ciudad = leer.nextInt();
        ciudadRepositorio.eliminar(id_ciudad);
        System.out.println("\nDatos eliminados exitosamente");
    }

    @Override
    public void buscar(){
        limpiar();
        System.out.print("Digite el id de la ciudad que desea buscar --> ");
        int id_ciudad = leer.nextInt();
        ciudad c = (ciudad) ciudadRepositorio.porCodigo(id_ciudad);
        System.out.println("=======================================");
        System.out.println("Id ciudad: " + c.getCiudad_id());
        System.out.println("Nombre ciudad: " + c.getCiudad_nombre());
        System.out.println("=======================================");
        System.out.println("\nDatos mostrados exitosamente");
    }

    @Override
    public void listar(){
        @SuppressWarnings("unchecked")
        List<ciudad> listaCiudades = ciudadRepositorio.listar();
        for(ciudad c: listaCiudades){
            System.out.println("=======================================");
            System.out.println("Id ciudad: " + c.getCiudad_id());
            System.out.println("Nombre ciudad: " + c.getCiudad_nombre());
            System.out.println("=======================================");
        }
    }

    @Override
    public void menu(){
        byte d = 0;
        do{
            System.out.println("\n=======================================");
            System.out.println("MENU\n");
            System.out.println("1. Guardar datos de ciudad");
            System.out.println("2. Modificar datos de ciudad");
            System.out.println("3. Eliminar datos de ciudad");
            System.out.println("4. Buscar datos de ciudad");
            System.out.println("5. Listar datos de ciudad");
            System.out.println("6. Salir");
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
