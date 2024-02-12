package com.campuslands.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import com.campuslands.models.horario;
import com.campuslands.repositories.horarioImpl;
import com.campuslands.repositories.repository;

public class ServiceHorario implements Services<horario> {
    public static Scanner leer = new Scanner(System.in);
    @SuppressWarnings("rawtypes")
    private static final repository horarioRepositorio = new horarioImpl();

    public void limpiar(){
        leer.nextLine();
    }

    @Override
    public horario datos(){
        horario c = new horario();

        System.out.print("Digite el dia --> ");
        LocalDate dia = LocalDate.parse(leer.next());
        
        System.out.print("Digite la hora de inicio --> ");
        leer.nextLine();
        LocalDateTime h_inicio = LocalDateTime.parse(leer.nextLine());

        System.out.print("Digite la hora de fin --> ");
        LocalDateTime h_fin = LocalDateTime.parse(leer.nextLine());

        System.out.print("Digite el id del salon --> ");
        int id_sal = leer.nextInt();

        System.out.print("Digite el id de la asignatura --> ");
        int id_asi = leer.nextInt();

        c.setHorario_dia(dia);
        c.setHorario_hora_inicio(h_inicio);
        c.setHorario_hora_fin(h_fin);
        c.setSalon_id(id_sal);
        c.setAsignatura_id(id_asi);

        return c;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void guardar(){
        limpiar();
        horarioRepositorio.guardar(datos());
        System.out.println("\nDatos guardados exitosamente");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void modificar(){
        limpiar();
        System.out.print("Digite el id de la horario que desea modificar --> ");
        int id_horario = leer.nextInt();
        leer.nextLine();
        horario c = datos();
        c.setHorario_id(id_horario);
        horarioRepositorio.modificar(c);
        System.out.println("\nDatos modificados exitosamente");
    }

    @Override
    public void eliminar(){
        limpiar();
        System.out.print("Digite el id de la horario que desea eliminar --> ");
        int id_horario = leer.nextInt();
        horarioRepositorio.eliminar(id_horario);
        System.out.println("\nDatos eliminados exitosamente");
    }

    @Override
    public void buscar(){
        limpiar();
        System.out.print("Digite el id de la horario que desea buscar --> ");
        int id_horario = leer.nextInt();
        horario c = (horario) horarioRepositorio.porCodigo(id_horario);
        System.out.println("=======================================");
        System.out.println("Id horario: " + c.getHorario_id());
        System.out.println("Dia: " + c.getHorario_dia());
        System.out.println("Hora inicio: " + c.getHorario_hora_inicio());
        System.out.println("Hora fin: " + c.getHorario_hora_fin());
        System.out.println("Id salon: " + c.getSalon_id());
        System.out.println("Id asignatura: " + c.getAsignatura_id());
        System.out.println("=======================================");
        System.out.println("\nDatos mostrados exitosamente");
    }

    @Override
    public void listar(){
        @SuppressWarnings("unchecked")
        List<horario> listahorarioes = horarioRepositorio.listar();
        for(horario c: listahorarioes){
            System.out.println("=======================================");
            System.out.println("Id horario: " + c.getHorario_id());
            System.out.println("Dia: " + c.getHorario_dia());
            System.out.println("Hora inicio: " + c.getHorario_hora_inicio());
            System.out.println("Hora fin: " + c.getHorario_hora_fin());
            System.out.println("Id salon: " + c.getSalon_id());
            System.out.println("Id asignatura: " + c.getAsignatura_id());
            System.out.println("=======================================");
        }
    }

    @Override
    public void menu(){
        byte d = 0;
        do{
            System.out.println("\n=======================================");
            System.out.println("MENU\n");
            System.out.println("1. Guardar datos de horario");
            System.out.println("2. Modificar datos de horario");
            System.out.println("3. Eliminar datos de horario");
            System.out.println("4. Buscar datos de horario");
            System.out.println("5. Listar datos de horario");
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
