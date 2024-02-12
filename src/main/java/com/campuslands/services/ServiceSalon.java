package com.campuslands.services;

import java.util.List;
import java.util.Scanner;

import com.campuslands.models.edificio;
import com.campuslands.models.salon;
import com.campuslands.repositories.salonImpl;
import com.campuslands.repositories.edificioImpl;
import com.campuslands.repositories.repository;

public class ServiceSalon implements Services<salon> {
    public static Scanner leer = new Scanner(System.in);
    @SuppressWarnings("rawtypes")
    private static final repository salonRepositorio = new salonImpl();
    @SuppressWarnings("rawtypes")
    private static final repository edificioRepositorio = new edificioImpl();
    @SuppressWarnings("unchecked")
    List<edificio> listarEdificios = edificioRepositorio.listar();
    public void limpiar(){
        leer.nextLine();
    }

    @Override
    public salon datos(){
        salon c = new salon();
        int cap, n_pis, id_edi;

        while (true) {
            System.out.print("Digite la capacidad del salon --> ");
            cap = leer.nextInt();
            if (cap < 1) {
                System.out.println("\nLa capacidad no puede ser 0 o negativa");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("Digite el id del edificio --> ");
            id_edi = leer.nextInt();
            if (!validarId(id_edi)) {
                System.out.println("\nEl id no existe, vuelva a intentarlo");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("Digite el piso del edificio --> ");
            n_pis = leer.nextInt();
            boolean des = true;
            for (edificio e : listarEdificios) {
                if (e.getEdificio_id() == id_edi) {
                    if (e.getEdificio_pisos() < n_pis) {
                        System.out.println("El numero de piso excede el numero de pisos del edificio, vuelva a intentarlo");
                        des = false;
                    }
                }
            }
            if (!des) {
                continue;
            }
            break;
        }
        c.setSalon_capacidad(cap);
        c.setSalon_piso(n_pis);
        c.setEdificio_id(id_edi);

        return c;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void guardar(){
        limpiar();
        salonRepositorio.guardar(datos());
        System.out.println("\nDatos guardados exitosamente");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void modificar(){
        limpiar();
        System.out.print("Digite el id de la salon que desea modificar --> ");
        int id_salon = leer.nextInt();
        leer.nextLine();
        salon c = datos();
        c.setSalon_id(id_salon);
        salonRepositorio.modificar(c);
        System.out.println("\nDatos modificados exitosamente");
    }

    @Override
    public void eliminar(){
        limpiar();
        System.out.print("Digite el id de la salon que desea eliminar --> ");
        int id_salon = leer.nextInt();
        salonRepositorio.eliminar(id_salon);
        System.out.println("\nDatos eliminados exitosamente");
    }

    @Override
    public void buscar(){
        limpiar();
        System.out.print("Digite el id de la salon que desea buscar --> ");
        int id_salon = leer.nextInt();
        salon c = (salon) salonRepositorio.porCodigo(id_salon);
        System.out.println("=======================================");
        System.out.println("Id salon: " + c.getSalon_id());
        System.out.println("Capacidad: " + c.getSalon_capacidad());
        System.out.println("Piso: " + c.getSalon_piso());
        System.out.println("Id edificio: " + c.getEdificio_id());
        System.out.println("=======================================");
        System.out.println("\nDatos mostrados exitosamente");
    }

    @Override
    public void listar(){
        @SuppressWarnings("unchecked")
        List<salon> listasalones = salonRepositorio.listar();
        for(salon c: listasalones){
            System.out.println("=======================================");
            System.out.println("Id salon: " + c.getSalon_id());
            System.out.println("Capacidad: " + c.getSalon_capacidad());
            System.out.println("Piso: " + c.getSalon_piso());
            System.out.println("Id edificio: " + c.getEdificio_id());
            System.out.println("=======================================");
        }
    }

    @Override
    public void menu(){
        byte d = 0;
        do{
            System.out.println("\n=======================================");
            System.out.println("MENU\n");
            System.out.println("1. Guardar datos de salon");
            System.out.println("2. Modificar datos de salon");
            System.out.println("3. Eliminar datos de salon");
            System.out.println("4. Buscar datos de salon");
            System.out.println("5. Listar datos de salon");
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
    public boolean validarId(int id) {
        Object entidad = salonRepositorio.porCodigo(id);
        if (entidad == null) {
            return false;
        }
        return true;
    }
}
