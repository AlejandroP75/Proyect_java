package com.campuslands.services;

import java.util.List;
import java.util.Scanner;
import com.campuslands.models.profesor;
import com.campuslands.repositories.profesorImpl;
import com.campuslands.repositories.repository;

public class ServiceProfesor implements Services<profesor> {
    public static Scanner leer = new Scanner(System.in);
    @SuppressWarnings("rawtypes")
    private static final repository profesorRepositorio = new profesorImpl();

    public void limpiar(){
        leer.nextLine();
    }

    @Override
    public profesor datos(){
        profesor c = new profesor();
        int id_dep, id_per;

        while (true) {
            System.out.print("Digite el id del departamento --> ");
            id_dep = leer.nextInt();
            if (!validarId(id_dep)) {
                System.out.println("\nEl id no existe, vuelva a intentarlo");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("Digite el id de la persona --> ");
            id_per = leer.nextInt();

            boolean exist = validarId(id_per);
            if (!exist) {
                System.out.println("El id no existe, vuelva a intentarlo");
                continue;
            }
            break;
        }

        c.setDepartamento_id(id_dep);
        c.setPersona_id(id_per);

        return c;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void guardar(){
        limpiar();
        profesorRepositorio.guardar(datos());
        System.out.println("\nDatos guardados exitosamente");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void modificar(){
        limpiar();
        System.out.print("Digite el id del profesor que desea modificar --> ");
        int id_profesor = leer.nextInt();
        leer.nextLine();
        profesor c = datos();
        c.setProfesor_id(id_profesor);
        profesorRepositorio.modificar(c);
        System.out.println("\nDatos modificados exitosamente");
    }

    @Override
    public void eliminar(){
        limpiar();
        System.out.print("Digite el id del profesor que desea eliminar --> ");
        int id_profesor = leer.nextInt();
        profesorRepositorio.eliminar(id_profesor);
        System.out.println("\nDatos eliminados exitosamente");
    }

    @Override
    public void buscar(){
        limpiar();
        System.out.print("Digite el id del profesor que desea buscar --> ");
        int id_profesor = leer.nextInt();
        profesor c = (profesor) profesorRepositorio.porCodigo(id_profesor);
        System.out.println("=======================================");
        System.out.println("Id profesor: " + c.getProfesor_id());
        System.out.println("Id departamento: " + c.getDepartamento_id());
        System.out.println("Id persona: " + c.getPersona_id());
        System.out.println("=======================================");
        System.out.println("\nDatos mostrados exitosamente");
    }

    @Override
    public void listar(){
        @SuppressWarnings("unchecked")
        List<profesor> listaprofesores = profesorRepositorio.listar();
        for(profesor c: listaprofesores){
            System.out.println("=======================================");
            System.out.println("Id profesor: " + c.getProfesor_id());
            System.out.println("Id departamento: " + c.getDepartamento_id());
            System.out.println("Id persona: " + c.getPersona_id());
            System.out.println("=======================================");
        }
    }

    @Override
    public void menu(){
        byte d = 0;
        do{
            System.out.println("\n=======================================");
            System.out.println("MENU\n");
            System.out.println("1. Guardar datos de profesor");
            System.out.println("2. Modificar datos de profesor");
            System.out.println("3. Eliminar datos de profesor");
            System.out.println("4. Buscar datos de profesor");
            System.out.println("5. Listar datos de profesor");
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
    public boolean validarId(int id) {
        Object entidad = profesorRepositorio.porCodigo(id);
        if (entidad == null) {
            return false;
        }
        return true;
    }
}
