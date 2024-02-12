package com.campuslands.services;

@SuppressWarnings("rawtypes")
public class conexion_vacia implements Services{

    @Override
    public Object datos() {
        System.out.println("No hay datos");
        return null;
    }

    @Override
    public void guardar() {
        System.out.println("No se pudo guardar, no hay datos");
    }

    @Override
    public void modificar() {
        System.out.println("No se pudo modificar, no hay datos");
    }

    @Override
    public void buscar() {
        System.out.println("No se pudo buscar, no hay datos");
    }

    @Override
    public void eliminar() {
        System.out.println("No se pudo eliminar, No hay datos");
    }

    @Override
    public void listar() {
        System.out.println("No se pudo listar, no hay datos");
    }

    @Override
    public void menu() {
        System.out.println("No hay menu, no hay datos");
    }
    
}
