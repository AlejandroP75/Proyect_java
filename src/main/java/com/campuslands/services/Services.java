package com.campuslands.services;

public interface Services<T> {
    
    public T datos();
    public void guardar();
    public void modificar();
    public void buscar();
    public void eliminar();
    public void listar();
    public void menu();
} 