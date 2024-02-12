package com.campuslands.services;

import java.util.List;
import java.util.Scanner;
import com.campuslands.models.curso;
import com.campuslands.repositories.cursoImpl;
import com.campuslands.repositories.repository;

public class ServiceCurso implements Services<curso> {
    public static Scanner leer = new Scanner(System.in);
    @SuppressWarnings("rawtypes")
    private static final repository cursoRepositorio = new cursoImpl();

    public void limpiar(){
        leer.nextLine();
    }

    @Override
    public curso datos(){
        curso c = new curso();

        System.out.print("Digite el nombre del curso --> ");
        String nom_cur = leer.nextLine();
        
        System.out.print("Digite la guia catedra --> ");
        String guia = leer.nextLine();

        c.setCurso_nombre(nom_cur);
        c.setCurso_guia_catedra(guia);

        return c;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void guardar(){
        limpiar();
        cursoRepositorio.guardar(datos());
        System.out.println("\nDatos guardados exitosamente");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void modificar(){
        limpiar();
        System.out.print("Digite el id del curso que desea modificar --> ");
        int id_curso = leer.nextInt();
        leer.nextLine();
        curso c = datos();
        c.setCurso_id(id_curso);
        cursoRepositorio.modificar(c);
        System.out.println("\nDatos modificados exitosamente");
    }

    @Override
    public void eliminar(){
        limpiar();
        System.out.print("Digite el id del curso que desea eliminar --> ");
        int id_curso = leer.nextInt();
        cursoRepositorio.eliminar(id_curso);
        System.out.println("\nDatos eliminados exitosamente");
    }

    @Override
    public void buscar(){
        limpiar();
        System.out.print("Digite el id del curso que desea buscar --> ");
        int id_curso = leer.nextInt();
        curso c = (curso) cursoRepositorio.porCodigo(id_curso);
        System.out.println("=======================================");
        System.out.println("Id curso: " + c.getCurso_id());
        System.out.println("Nombre curso: " + c.getCurso_nombre());
        System.out.println("Guia catedra: " + c.getCurso_guia_catedra());
        System.out.println("=======================================");
        System.out.println("\nDatos mostrados exitosamente");
    }

    @Override
    public void listar(){
        @SuppressWarnings("unchecked")
        List<curso> listacursoes = cursoRepositorio.listar();
        for(curso c: listacursoes){
            System.out.println("=======================================");
            System.out.println("Id curso: " + c.getCurso_id());
            System.out.println("Nombre curso: " + c.getCurso_nombre());
            System.out.println("Guia catedra: " + c.getCurso_guia_catedra());
            System.out.println("=======================================");
        }
    }

    @Override
    public void menu(){
        byte d = 0;
        do{
            System.out.println("\n=======================================");
            System.out.println("MENU\n");
            System.out.println("1. Guardar datos de curso");
            System.out.println("2. Modificar datos de curso");
            System.out.println("3. Eliminar datos de curso");
            System.out.println("4. Buscar datos de curso");
            System.out.println("5. Listar datos de curso");
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
