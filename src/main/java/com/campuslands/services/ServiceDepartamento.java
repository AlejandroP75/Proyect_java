package com.campuslands.services;

import java.util.List;
import java.util.Scanner;
import com.campuslands.models.departamento;
import com.campuslands.repositories.departamentoImpl;
import com.campuslands.repositories.repository;

public class ServiceDepartamento implements Services<departamento> {
    public static Scanner leer = new Scanner(System.in);
    @SuppressWarnings("rawtypes")
    private static final repository departamentoRepositorio = new departamentoImpl();

    public void limpiar(){
        leer.nextLine();
    }

    @Override
    public departamento datos(){
        departamento c = new departamento();

        System.out.print("Digite el nombre del departamento--> ");
        String nombre_dep = leer.next();

        c.setDepartamento_nombre(nombre_dep);

        return c;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void guardar(){
        limpiar();
        departamentoRepositorio.guardar(datos());
        System.out.println("\nDatos guardados exitosamente");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void modificar(){
        limpiar();
        System.out.print("Digite el id del departamento que desea modificar --> ");
        int id_departamento = leer.nextInt();
        leer.nextLine();
        departamento c = datos();
        c.setDepartamento_id(id_departamento);
        departamentoRepositorio.modificar(c);
        System.out.println("\nDatos modificados exitosamente");
    }

    @Override
    public void eliminar(){
        limpiar();
        System.out.print("Digite el id del departamento que desea eliminar --> ");
        int id_departamento = leer.nextInt();
        departamentoRepositorio.eliminar(id_departamento);
        System.out.println("\nDatos eliminados exitosamente");
    }

    @Override
    public void buscar(){
        limpiar();
        System.out.print("Digite el id del departamento que desea buscar --> ");
        int id_departamento = leer.nextInt();
        departamento c = (departamento) departamentoRepositorio.porCodigo(id_departamento);
        System.out.println("=======================================");
        System.out.println("Id departamento: " + c.getDepartamento_id());
        System.out.println("Nombre: " + c.getDepartamento_nombre());
        System.out.println("=======================================");
        System.out.println("\nDatos mostrados exitosamente");
    }

    @Override
    public void listar(){
        @SuppressWarnings("unchecked")
        List<departamento> listadepartamentoes = departamentoRepositorio.listar();
        for(departamento c: listadepartamentoes){
            System.out.println("=======================================");
            System.out.println("Id departamento: " + c.getDepartamento_id());
            System.out.println("Nombre: " + c.getDepartamento_nombre());
            System.out.println("=======================================");
        }
    }

    @Override
    public void menu(){
        byte d = 0;
        do{
            System.out.println("\n=======================================");
            System.out.println("MENU\n");
            System.out.println("1. Guardar datos de departamento");
            System.out.println("2. Modificar datos de departamento");
            System.out.println("3. Eliminar datos de departamento");
            System.out.println("4. Buscar datos de departamento");
            System.out.println("5. Listar datos de departamento");
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
}
