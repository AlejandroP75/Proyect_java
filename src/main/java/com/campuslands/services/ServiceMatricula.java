package com.campuslands.services;

import java.util.List;
import java.util.Scanner;

import com.campuslands.models.asignatura;
import com.campuslands.models.horario;
import com.campuslands.models.matricula;
import com.campuslands.models.salon;
import com.campuslands.repositories.asignaturaImpl;
import com.campuslands.repositories.horarioImpl;
import com.campuslands.repositories.matriculaImpl;
import com.campuslands.repositories.repository;
import com.campuslands.repositories.salonImpl;

public class ServiceMatricula implements Services<matricula> {
    public static Scanner leer = new Scanner(System.in);
    @SuppressWarnings("rawtypes")
    private static final repository matriculaRepositorio = new matriculaImpl();
    @SuppressWarnings("rawtypes")
    private static final repository salonRepositorio = new salonImpl();
    @SuppressWarnings("unchecked")
    List<salon> listarSalones = salonRepositorio.listar();
    @SuppressWarnings("rawtypes")
    private static final repository asignaturaRepositorio = new asignaturaImpl();
    @SuppressWarnings("unchecked")
    List<asignatura> listarAsignaturas = asignaturaRepositorio.listar();
    @SuppressWarnings("unchecked")
    List<matricula> listarMatriculas = matriculaRepositorio.listar();
    @SuppressWarnings("rawtypes")
    private static final repository horarioRepositorio = new horarioImpl();
    @SuppressWarnings("unchecked")
    List<horario> listarHorarios = horarioRepositorio.listar();

    public void limpiar(){
        leer.nextLine();
    }

    @Override
    public matricula datos(){
        matricula c = new matricula();
        int id_alu, id_asi, id_per;

        while(true){
            System.out.print("Digite el id del alumno --> ");
            id_alu = leer.nextInt();
            if (!validarId(id_alu)) {
                System.out.println("\nEl id no existe, vuelva a intentarlo");
                continue;
            }
            break;
        }
        while(true){
            System.out.print("Digite el id de la asignatura --> ");
            id_asi = leer.nextInt();
            if (!validarId(id_asi)) {
                System.out.println("\nEl id no existe, vuelva a intentarlo");
                continue;
            }
            break;
        }
        while(true){
            System.out.print("Digite el id del periodo --> ");
            id_per = leer.nextInt();
            if (!validarId(id_per)) {
                System.out.println("\nEl id no existe, vuelva a intentarlo");
                continue;
            }
            break;
        }

        c.setAlumno_id(id_alu);
        c.setAsignatura_id(id_asi);
        c.setPeriodo_id(id_per);

        return c;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void guardar(){
        limpiar();
        matriculaRepositorio.guardar(datos());
        System.out.println("\nDatos guardados exitosamente");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void modificar(){
        limpiar();
        System.out.print("Digite el id de la matricula que desea modificar --> ");
        int id_matricula = leer.nextInt();
        leer.nextLine();
        matricula c = datos();
        c.setMatricula_id(id_matricula);
        matriculaRepositorio.modificar(c);
        System.out.println("\nDatos modificados exitosamente");
    }

    @Override
    public void eliminar(){
        limpiar();
        System.out.print("Digite el id de la matricula que desea eliminar --> ");
        int id_matricula = leer.nextInt();
        matriculaRepositorio.eliminar(id_matricula);
        System.out.println("\nDatos eliminados exitosamente");
    }

    @Override
    public void buscar(){
        limpiar();
        System.out.print("Digite el id de la matricula que desea buscar --> ");
        int id_matricula = leer.nextInt();
        matricula c = (matricula) matriculaRepositorio.porCodigo(id_matricula);
        System.out.println("=======================================");
        System.out.println("Id matricula: " + c.getMatricula_id());
        System.out.println("Id alumno: " + c.getAlumno_id());
        System.out.println("Id asignatura: " + c.getAsignatura_id());
        System.out.println("Id periodo: " + c.getPeriodo_id());
        System.out.println("=======================================");
        System.out.println("\nDatos mostrados exitosamente");
    }

    @Override
    public void listar(){
        @SuppressWarnings("unchecked")
        List<matricula> listamatriculaes = matriculaRepositorio.listar();
        for(matricula c: listamatriculaes){
            System.out.println("=======================================");
            System.out.println("Id matricula: " + c.getMatricula_id());
            System.out.println("Id alumno: " + c.getAlumno_id());
            System.out.println("Id asignatura: " + c.getAsignatura_id());
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
            System.out.println("1. Guardar datos de matricula");
            System.out.println("2. Modificar datos de matricula");
            System.out.println("3. Eliminar datos de matricula");
            System.out.println("4. Buscar datos de matricula");
            System.out.println("5. Listar datos de matricula");
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
        Object entidad = matriculaRepositorio.porCodigo(id);
        if (entidad == null) {
            return false;
        }
        return true;
    }
}
