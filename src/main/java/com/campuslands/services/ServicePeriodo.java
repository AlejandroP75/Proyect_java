package com.campuslands.services;

import java.util.List;
import java.util.Scanner;
import com.campuslands.models.periodo;
import com.campuslands.repositories.periodoImpl;
import com.campuslands.repositories.repository;

public class ServicePeriodo implements Services<periodo> {
    public static Scanner leer = new Scanner(System.in);
    @SuppressWarnings("rawtypes")
    private static final repository periodoRepositorio = new periodoImpl();

    public void limpiar(){
        leer.nextLine();
    }

    @Override
    public periodo datos(){
        periodo c = new periodo();
        int sem_per;

        System.out.print("Digite el año del periodo --> ");
        int ani_per = leer.nextInt();
        while (true) {
            System.out.print("Digite el semestre del periodo --> ");
            sem_per = leer.nextInt();
            if (sem_per != 1 && sem_per != 2) {
                System.out.println("\nEl semestre solo puede ser 1 o 2, vuelva a intentarlo");
                continue;
            }
            break;
        }
        System.out.print("Digite el nombre del periodo --> ");
        leer.nextLine();
        String nom_per = leer.nextLine();

        c.setPeriodo_anio(ani_per);
        c.setPeriodo_semestre(sem_per);
        c.setPeriodo_nombre(nom_per);

        return c;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void guardar(){
        limpiar();
        periodoRepositorio.guardar(datos());
        System.out.println("\nDatos guardados exitosamente");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void modificar(){
        limpiar();
        System.out.print("Digite el id del periodo que desea modificar --> ");
        int id_periodo = leer.nextInt();
        leer.nextLine();
        periodo c = datos();
        c.setPeriodo_id(id_periodo);
        periodoRepositorio.modificar(c);
        System.out.println("\nDatos modificados exitosamente");
    }

    @Override
    public void eliminar(){
        limpiar();
        System.out.print("Digite el id del periodo que desea eliminar --> ");
        int id_periodo = leer.nextInt();
        periodoRepositorio.eliminar(id_periodo);
        System.out.println("\nDatos eliminados exitosamente");
    }

    @Override
    public void buscar(){
        limpiar();
        System.out.print("Digite el id del periodo que desea buscar --> ");
        int id_periodo = leer.nextInt();
        periodo c = (periodo) periodoRepositorio.porCodigo(id_periodo);
        System.out.println("=======================================");
        System.out.println("Id periodo: " + c.getPeriodo_id());
        System.out.println("Año: " + c.getPeriodo_anio());
        System.out.println("Semestre: " + c.getPeriodo_semestre());
        System.out.println("Nombre: " + c.getPeriodo_nombre());
        System.out.println("=======================================");
        System.out.println("\nDatos mostrados exitosamente");
    }

    @Override
    public void listar(){
        @SuppressWarnings("unchecked")
        List<periodo> listaperiodoes = periodoRepositorio.listar();
        for(periodo c: listaperiodoes){
            System.out.println("=======================================");
            System.out.println("Id periodo: " + c.getPeriodo_id());
            System.out.println("Año: " + c.getPeriodo_anio());
            System.out.println("Semestre: " + c.getPeriodo_semestre());
            System.out.println("Nombre: " + c.getPeriodo_nombre());
            System.out.println("=======================================");
        }
    }

    @Override
    public void menu(){
        byte d = 0;
        do{
            System.out.println("\n=======================================");
            System.out.println("MENU\n");
            System.out.println("1. Guardar datos de periodo");
            System.out.println("2. Modificar datos de periodo");
            System.out.println("3. Eliminar datos de periodo");
            System.out.println("4. Buscar datos de periodo");
            System.out.println("5. Listar datos de periodo");
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
