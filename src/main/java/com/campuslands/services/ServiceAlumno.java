package com.campuslands.services;

import java.util.List;
import java.util.Scanner;
import com.campuslands.models.alumno;
import com.campuslands.repositories.alumnoImpl;
import com.campuslands.repositories.repository;

public class ServiceAlumno implements Services<alumno> {
    public static Scanner leer = new Scanner(System.in);
    @SuppressWarnings("rawtypes")
    private static final repository alumnoRepositorio = new alumnoImpl();

    public void limpiar(){
        leer.nextLine();
    }

    @Override
    public alumno datos(){
        alumno c = new alumno();
        int id_pro, id_per;

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
            System.out.print("Digite el id de la persona --> ");
            id_per = leer.nextInt();

            boolean exist = validarId(id_per);
            if (!exist) {
                System.out.println("El id no existe, vuelva a intentarlo");
                continue;
            }
            break;
        }

        c.setPrograma_id(id_pro);
        c.setPersona_id(id_per);

        return c;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void guardar(){
        limpiar();
        alumnoRepositorio.guardar(datos());
        System.out.println("\nDatos guardados exitosamente");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void modificar(){
        limpiar();
        System.out.print("Digite el id del alumno que desea modificar --> ");
        int id_alumno = leer.nextInt();
        leer.nextLine();
        alumno c = datos();
        c.setAlumno_id(id_alumno);
        alumnoRepositorio.modificar(c);
        System.out.println("\nDatos modificados exitosamente");
    }

    @Override
    public void eliminar(){
        limpiar();
        System.out.print("Digite el id del alumno que desea eliminar --> ");
        int id_alumno = leer.nextInt();
        alumnoRepositorio.eliminar(id_alumno);
        System.out.println("\nDatos eliminados exitosamente");
    }

    @Override
    public void buscar(){
        limpiar();
        System.out.print("Digite el id del alumno que desea buscar --> ");
        int id_alumno = leer.nextInt();
        alumno c = (alumno) alumnoRepositorio.porCodigo(id_alumno);
        System.out.println("=======================================");
        System.out.println("Id alumno: " + c.getAlumno_id());
        System.out.println("Id departamento: " + c.getPrograma_id());
        System.out.println("Id persona: " + c.getPersona_id());
        System.out.println("=======================================");
        System.out.println("\nDatos mostrados exitosamente");
    }

    @Override
    public void listar(){
        @SuppressWarnings("unchecked")
        List<alumno> listaalumnoes = alumnoRepositorio.listar();
        for(alumno c: listaalumnoes){
            System.out.println("=======================================");
            System.out.println("Id alumno: " + c.getAlumno_id());
            System.out.println("Id departamento: " + c.getPrograma_id());
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
            System.out.println("1. Guardar datos de alumno");
            System.out.println("2. Modificar datos de alumno");
            System.out.println("3. Eliminar datos de alumno");
            System.out.println("4. Buscar datos de alumno");
            System.out.println("5. Listar datos de alumno");
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
        Object entidad = alumnoRepositorio.porCodigo(id);
        if (entidad == null) {
            return false;
        }
        return true;
    }
    
    
}
