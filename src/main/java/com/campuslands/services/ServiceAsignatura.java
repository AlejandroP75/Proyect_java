package com.campuslands.services;

import java.util.List;
import java.util.Scanner;
import com.campuslands.models.asignatura;
import com.campuslands.repositories.asignaturaImpl;
import com.campuslands.repositories.repository;

public class ServiceAsignatura implements Services<asignatura> {
    public static Scanner leer = new Scanner(System.in);
    @SuppressWarnings("rawtypes")
    private static final repository asignaturaRepositorio = new asignaturaImpl();

    public void limpiar(){
        leer.nextLine();
    }

    @Override
    public asignatura datos(){
        asignatura c = new asignatura();
        int num_cre, id_pro, id_cur, id_per;

        System.out.print("Digite el nombre de la asignatura --> ");
        String nom_asi = leer.nextLine();
        
        while (true) {
            System.out.print("Digite el numero de creditos --> ");
            num_cre = leer.nextInt();
            if (num_cre < 1) {
                System.out.println("\nEl numero de creditos tiene que ser mayor que 0");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("Digite el id del profesor --> ");
            id_pro = leer.nextInt();
            if (!validarId(id_pro)) {
                System.out.println("\nEl id no existe, vuelva a intentarlo");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("Digite el id del curso --> ");
            id_cur = leer.nextInt();
            if (!validarId(id_cur)) {
                System.out.println("\nEl id no existe, vuelva a intentarlo");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("Digite el id del periodo --> ");
            id_per = leer.nextInt();
            if (!validarId(id_per)) {
                System.out.println("\nEl id no existe, vuelva a intentarlo");
                continue;
            }
            break;
        }
        
        c.setAsignatura_nombre(nom_asi);
        c.setAsignatura_numero_creditos(num_cre);
        c.setProfesor_id(id_pro);
        c.setCurso_id(id_cur);
        c.setPeriodo_id(id_per);

        return c;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void guardar(){
        limpiar();
        asignaturaRepositorio.guardar(datos());
        System.out.println("\nDatos guardados exitosamente");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void modificar(){
        limpiar();
        System.out.print("Digite el id de la asignatura que desea modificar --> ");
        int id_asignatura = leer.nextInt();
        leer.nextLine();
        asignatura c = datos();
        c.setAsignatura_id(id_asignatura);
        asignaturaRepositorio.modificar(c);
        System.out.println("\nDatos modificados exitosamente");
    }

    @Override
    public void eliminar(){
        limpiar();
        System.out.print("Digite el id de la asignatura que desea eliminar --> ");
        int id_asignatura = leer.nextInt();
        asignaturaRepositorio.eliminar(id_asignatura);
        System.out.println("\nDatos eliminados exitosamente");
    }

    @Override
    public void buscar(){
        limpiar();
        System.out.print("Digite el id de la asignatura que desea buscar --> ");
        int id_asignatura = leer.nextInt();
        asignatura c = (asignatura) asignaturaRepositorio.porCodigo(id_asignatura);
        System.out.println("=======================================");
        System.out.println("Id asignatura: " + c.getAsignatura_id());
        System.out.println("Nombre asignatura: " + c.getAsignatura_nombre());
        System.out.println("Numero de creditos: " + c.getAsignatura_numero_creditos());
        System.out.println("Id profesor: " + c.getProfesor_id());
        System.out.println("Id curso: " + c.getCurso_id());
        System.out.println("Id periodo: " + c.getPeriodo_id());
        System.out.println("=======================================");
        System.out.println("\nDatos mostrados exitosamente");
    }

    @Override
    public void listar(){
        @SuppressWarnings("unchecked")
        List<asignatura> listaasignaturaes = asignaturaRepositorio.listar();
        for(asignatura c: listaasignaturaes){
            System.out.println("=======================================");
            System.out.println("Id asignatura: " + c.getAsignatura_id());
            System.out.println("Nombre asignatura: " + c.getAsignatura_nombre());
            System.out.println("Numero de creditos: " + c.getAsignatura_numero_creditos());
            System.out.println("Id profesor: " + c.getProfesor_id());
            System.out.println("Id curso: " + c.getCurso_id());
            System.out.println("Id periodo: " + c.getPeriodo_id());
            System.out.println("=======================================");
        }
    }

    @Override
    public void menu(){
        byte d = 0;
        do{
            System.out.println("\n=======================================");
            System.out.println("MENU\n");
            System.out.println("1. Guardar datos de asignatura");
            System.out.println("2. Modificar datos de asignatura");
            System.out.println("3. Eliminar datos de asignatura");
            System.out.println("4. Buscar datos de asignatura");
            System.out.println("5. Listar datos de asignatura");
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
        Object entidad = asignaturaRepositorio.porCodigo(id);
        if (entidad == null) {
            return false;
        }
        return true;
    }
}
