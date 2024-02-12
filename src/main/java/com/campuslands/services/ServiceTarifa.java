package com.campuslands.services;

import java.util.List;
import java.util.Scanner;
import com.campuslands.models.tarifa;
import com.campuslands.repositories.tarifaImpl;
import com.campuslands.repositories.repository;

public class ServiceTarifa implements Services<tarifa> {
    public static Scanner leer = new Scanner(System.in);
    @SuppressWarnings("rawtypes")
    private static final repository tarifaRepositorio = new tarifaImpl();

    public void limpiar(){
        leer.nextLine();
    }

    @Override
    public tarifa datos(){
        tarifa c = new tarifa();
        int id_pro, id_per;
        Double valor_credito;

        while (true) {
            System.out.print("Digite el id del programa --> ");
            id_pro = leer.nextInt();
            if (!validarId(id_pro)) {
                System.out.println("\nEl id no existe, vuelva a intentarlo");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("Digite el id del periodo--> ");
            id_per = leer.nextInt();
            if (!validarId(id_per)) {
                System.out.println("\nEl id no existe, vuelva a intentarlo");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("Digite el valor del credito --> ");
            valor_credito = leer.nextDouble();
            if (valor_credito < 1) {
                System.out.println("\nEl valor del credito no puede ser negativo o 0");
                continue;
            }
            break;
        }

        c.setPrograma_id(id_pro);
        c.setPeriodo_id(id_per);
        c.setTarifa_valor_credito(valor_credito);

        return c;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void guardar(){
        limpiar();
        tarifaRepositorio.guardar(datos());
        System.out.println("\nDatos guardados exitosamente");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void modificar(){
        limpiar();
        System.out.print("Digite el id de la tarifa que desea modificar --> ");
        int id_tarifa = leer.nextInt();
        leer.nextLine();
        tarifa c = datos();
        c.setTarifa_id(id_tarifa);
        tarifaRepositorio.modificar(c);
        System.out.println("\nDatos modificados exitosamente");
    }

    @Override
    public void eliminar(){
        limpiar();
        System.out.print("Digite el id de la tarifa que desea eliminar --> ");
        int id_tarifa = leer.nextInt();
        tarifaRepositorio.eliminar(id_tarifa);
        System.out.println("\nDatos eliminados exitosamente");
    }

    @Override
    public void buscar(){
        limpiar();
        System.out.print("Digite el id de la tarifa que desea buscar --> ");
        int id_tarifa = leer.nextInt();
        tarifa c = (tarifa) tarifaRepositorio.porCodigo(id_tarifa);
        System.out.println("=======================================");
        System.out.println("Id tarifa: " + c.getTarifa_id());
        System.out.println("Id programa: " + c.getPrograma_id());
        System.out.println("Id periodo: " + c.getPeriodo_id());
        System.out.println("Valor credito: " + c.getTarifa_valor_credito());
        System.out.println("=======================================");
        System.out.println("\nDatos mostrados exitosamente");
    }

    @Override
    public void listar(){
        @SuppressWarnings("unchecked")
        List<tarifa> listatarifaes = tarifaRepositorio.listar();
        for(tarifa c: listatarifaes){
            System.out.println("=======================================");
            System.out.println("Id tarifa: " + c.getTarifa_id());
            System.out.println("Id programa: " + c.getPrograma_id());
            System.out.println("Id periodo: " + c.getPeriodo_id());
            System.out.println("Valor credito: " + c.getTarifa_valor_credito());
            System.out.println("=======================================");
        }
    }

    @Override
    public void menu(){
        byte d = 0;
        do{
            System.out.println("\n=======================================");
            System.out.println("MENU\n");
            System.out.println("1. Guardar datos de tarifa");
            System.out.println("2. Modificar datos de tarifa");
            System.out.println("3. Eliminar datos de tarifa");
            System.out.println("4. Buscar datos de tarifa");
            System.out.println("5. Listar datos de tarifa");
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
                case 6:
                    return;
                default:
                    System.out.println("ERROR, opción no valida");
                    break;
            }
        }while(d != 0);
        
    }

    public boolean validarId(int id) {
        Object entidad = tarifaRepositorio.porCodigo(id);
        if (entidad == null) {
            return false;
        }
        return true;
    }
}
