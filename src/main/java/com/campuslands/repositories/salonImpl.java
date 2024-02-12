package com.campuslands.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.campuslands.models.salon;
import com.campuslands.utils.Conexion;

public class salonImpl implements repository<salon>{
    private Connection getConnection() throws SQLException {
        return Conexion.getInstance().conectar();
    }   

    private salon crearSalon(ResultSet campo) throws SQLException{
        salon t = new salon();
        t.setSalon_id(campo.getInt("id_salon"));
        t.setSalon_capacidad(campo.getInt("capacidad"));
        t.setSalon_piso(campo.getInt("piso"));
        t.setEdificio_id(campo.getInt("id_edificio"));

        return t;
    }

    @Override
    public List<salon> listar() {
        List<salon> listarSalones = new ArrayList<>();
        try (Statement fila = getConnection().createStatement();
            ResultSet resultado = fila.executeQuery("SELECT * FROM salon")
        ) {
            while (resultado.next()) {
                listarSalones.add(crearSalon(resultado));
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, salonImpl");
            System.out.println("Verifique el try de la linea 32");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, salonImpl");
                System.out.println("Verifique el try de la linea 43");
                System.out.println("Error: " + ex);
            }
        }
        return listarSalones;
    }

    @Override
    public salon porCodigo(int id) {
        salon t = null;
        String sql = """
            SELECT * FROM salon
            WHERE id_salon = ?
                """;
        try (PreparedStatement modificacion = getConnection().prepareStatement(sql)){
            modificacion.setInt(1 , id);// Con esta modificacion ya tenemos la consulta completa
            ResultSet fila = modificacion.executeQuery();

            if(fila.next()){
                t = crearSalon(fila);
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, salonImpl");
            System.out.println("Verifique el try de la linea 61");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, salonImpl");
                System.out.println("Verifique el try de la linea 73");
                System.out.println("Error: " + ex);
            }
        }
        return t;
    }

    @Override
    public void guardar(salon entidad) {
        String sql = """
                INSERT INTO salon(capacidad, piso, id_edificio) VALUES
                (?, ?, ?);
                """;
            try (PreparedStatement fila = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                fila.setInt(1, entidad.getSalon_capacidad());
                fila.setInt(2, entidad.getSalon_piso());
                fila.setInt(3, entidad.getEdificio_id());

                int filas_afectadas = fila.executeUpdate();
                if(filas_afectadas == 0){
                    throw new SQLException("Fallo al guardar el salon, no se modificaron filas.");
                }
                try (ResultSet generatedKeys = fila.getGeneratedKeys()){
                    if (generatedKeys.next()) {
                        entidad.setSalon_id(generatedKeys.getInt(1));
                    }else{
                        throw new SQLException("Fallo al salon la salon, no se obtuvo el ID generado.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("ERROR fallo al guardar la salon, salonImpl");
                System.out.println("Verifique el try de la linea 90");
                System.out.println("Error: " + e);
            } finally{
                try{
                    Conexion.getInstance().cerrarConexion();
                } catch (SQLException ex){
                    System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, salonImpl");
                    System.out.println("Verifique el try de la linea 111");
                    System.out.println("Error: " + ex);
                }
            }
    }

    @Override
    public void eliminar(int id) {
        try (PreparedStatement fila = getConnection().prepareStatement("DELETE FROM salon WHERE id_salon = ?")){
            fila.setInt(1, id);
            fila.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR eliminando datos, salonImpl");
            System.out.println("Verifique el try de la linea 123");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, salonImpl");
                System.out.println("Verifique el try de la linea 131");
                System.out.println("Error: " + ex);
            }
        }
    }

    @Override
    public void modificar(salon entidad) {
        String sql = """
                UPDATE salon
                SET capacidad = ?,
                    piso = ?,
                    id_edificio = ?
                WHERE id_salon = ?;
                """;
        try (PreparedStatement fila = getConnection().prepareStatement(sql)){
            fila.setInt(1, entidad.getSalon_capacidad());
            fila.setInt(2, entidad.getSalon_piso());
            fila.setInt(3, entidad.getEdificio_id());
            fila.setInt(4, entidad.getSalon_id());
            int filas_afectadas = fila.executeUpdate();

            if(filas_afectadas == 0){
                throw new SQLException("Fallo al modificar la salon, no se modificaron filas.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR fallo al modificar la salon, salonImpl");
            System.out.println("Verifique el try de la linea 150");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, salonImpl");
                System.out.println("Verifique el try de la linea 165");
                System.out.println("Error: " + ex);
            }
        }
    }
}
