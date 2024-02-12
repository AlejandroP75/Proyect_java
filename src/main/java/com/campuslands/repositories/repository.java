package com.campuslands.repositories;
import java.util.List;

public interface repository<T> {
    public List<T> listar(); //Retorna una lista de un objeto
    public T porCodigo(int id); //Retorna un objeto por codigo
    public void guardar(T entidad);
    public void eliminar(int id);
    public void modificar(T entidad);
}
