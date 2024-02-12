package com.campuslands.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import com.campuslands.models.persona;
import com.campuslands.repositories.personaImpl;
import com.campuslands.repositories.repository;

public class ServicePersona implements Services<persona> {
    public static Scanner leer = new Scanner(System.in);
    @SuppressWarnings("rawtypes")
    private static final repository personaRepositorio = new personaImpl();

    public void limpiar(){
        leer.nextLine();
    }

    @Override
    public persona datos(){
        persona c = new persona();

        System.out.print("Digite el tipo de documento --> ");
        String tipo_d = leer.next();

        System.out.print("Digite el numero de documento --> ");
        int num_d = leer.nextInt();

        System.out.print("Digite los nombres de la persona --> ");
        leer.nextLine();
        String nom_p = leer.nextLine();

        System.out.print("Digite los apellidos de la persona --> ");
        String ape_p = leer.nextLine();

        System.out.print("Digite el telefono de la persona --> ");
        int tel_p = leer.nextInt();

        System.out.print("Digite la fecha de nacimiento de la persona (aaaa-mm-dd) --> ");
        LocalDate fecha = LocalDate.parse(leer.next());

        System.out.print("Digite el genero de la persona --> ");
        leer.nextLine();
        String gen = leer.nextLine();

        System.out.print("Digite el id de la ciudad --> ");
        int id_ciu = leer.nextInt();

        System.out.print("Digite el id de la direccion --> ");
        int id_dir = leer.nextInt();

        c.setPersona_tipo_documento(tipo_d);
        c.setPersona_numero_documento(num_d);
        c.setPersona_nombres(nom_p);
        c.setPersona_apellidos(ape_p);
        c.setPersona_telefono(tel_p);
        c.setPersona_fecha_nacimiento(fecha);
        c.setPersona_genero(gen);
        c.setCiudad_id(id_ciu);
        c.setDireccion_id(id_dir);

        return c;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void guardar(){
        limpiar();
        personaRepositorio.guardar(datos());
        System.out.println("\nDatos guardados exitosamente");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void modificar(){
        limpiar();
        System.out.print("Digite el id de la persona que desea modificar --> ");
        int id_persona = leer.nextInt();
        leer.nextLine();
        persona c = datos();
        c.setPersona_id(id_persona);
        personaRepositorio.modificar(c);
        System.out.println("\nDatos modificados exitosamente");
    }

    @Override
    public void eliminar(){
        limpiar();
        System.out.print("Digite el id de la persona que desea eliminar --> ");
        int id_persona = leer.nextInt();
        personaRepositorio.eliminar(id_persona);
        System.out.println("\nDatos eliminados exitosamente");
    }

    @Override
    public void buscar(){
        limpiar();
        System.out.print("Digite el id de la persona que desea buscar --> ");
        int id_persona = leer.nextInt();
        persona c = (persona) personaRepositorio.porCodigo(id_persona);
        System.out.println("=======================================");
        System.out.println("Id persona: " + c.getPersona_id());
        System.out.println("Tipo de documento: " + c.getPersona_tipo_documento());
        System.out.println("Numero de documento: " + c.getPersona_numero_documento());
        System.out.println("Nombres: " + c.getPersona_nombres());
        System.out.println("Apellidos: " + c.getPersona_apellidos());
        System.out.println("Telefono: " + c.getPersona_telefono());
        System.out.println("Fecha de nacimiento: " + c.getPersona_fecha_nacimiento());
        System.out.println("Genero: " + c.getPersona_genero());
        System.out.println("Id ciudad: " + c.getCiudad_id());
        System.out.println("Id direccion: " + c.getDireccion_id());
        System.out.println("=======================================");
        System.out.println("\nDatos mostrados exitosamente");
    }

    @Override
    public void listar(){
        @SuppressWarnings("unchecked")
        List<persona> listapersonaes = personaRepositorio.listar();
        for(persona c: listapersonaes){
            System.out.println("=======================================");
            System.out.println("Id persona: " + c.getPersona_id());
            System.out.println("Tipo de documento: " + c.getPersona_tipo_documento());
            System.out.println("Numero de documento: " + c.getPersona_numero_documento());
            System.out.println("Nombres: " + c.getPersona_nombres());
            System.out.println("Apellidos: " + c.getPersona_apellidos());
            System.out.println("Telefono: " + c.getPersona_telefono());
            System.out.println("Fecha de nacimiento: " + c.getPersona_fecha_nacimiento());
            System.out.println("Genero: " + c.getPersona_genero());
            System.out.println("Id ciudad: " + c.getCiudad_id());
            System.out.println("Id direccion: " + c.getDireccion_id());
            System.out.println("=======================================");
        }
    }

    @Override
    public void menu(){
        byte d = 0;
        do{
            System.out.println("\n=======================================");
            System.out.println("MENU\n");
            System.out.println("1. Guardar datos de persona");
            System.out.println("2. Modificar datos de persona");
            System.out.println("3. Eliminar datos de persona");
            System.out.println("4. Buscar datos de persona");
            System.out.println("5. Listar datos de persona");
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
