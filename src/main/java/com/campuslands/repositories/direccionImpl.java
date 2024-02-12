package com.campuslands.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.campuslands.models.direccion;
import com.campuslands.utils.Conexion;

public class direccionImpl implements repository<direccion>{
    private Connection getConnection() throws SQLException {
        return Conexion.getInstance().conectar();
    }   

    private direccion crearDireccion(ResultSet campo) throws SQLException{
        direccion d = new direccion();
        d.setDireccion_id(campo.getInt("id_direccion"));
        d.setDireccion(campo.getString("direccion"));

        return d;
    }

    @Override
    public List<direccion> listar() {
        List<direccion> listarCiudades = new ArrayList<>();
        try (Statement fila = getConnection().createStatement();
            ResultSet resultado = fila.executeQuery("SELECT * FROM direccion")
        ) {
            while (resultado.next()) {
                listarCiudades.add(crearDireccion(resultado));
            }
        } catch (SQLException e) {
            System.out.println("ERROR en listar, direccionImpl");
            System.out.println("Verifique el try de la linea 30");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, ciudadImpl");
                System.out.println("Verifique el try de la linea 61");
                System.out.println("Error: " + ex);
            }
        }
        return listarCiudades;
    }

    @Override
    public direccion porCodigo(int id) {
        direccion d = null;
        String sql = """
            SELECT * FROM direccion
            WHERE id_direccion = ?
                """;
        try (PreparedStatement modificacion = getConnection().prepareStatement(sql)){
            modificacion.setInt(1 , id);// Con esta modificacion ya tenemos la consulta completa
            ResultSet fila = modificacion.executeQuery();

            if(fila.next()){
                d = crearDireccion(fila);
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, direccionImpl");
            System.out.println("Verifique el try de la linea 50");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, ciudadImpl");
                System.out.println("Verifique el try de la linea 61");
                System.out.println("Error: " + ex);
            }
        }
        return d;
    }

    @Override
    public void guardar(direccion entidad) {
        String sql = """
                INSERT INTO direccion(direccion) VALUES
                (?);
                """;
            try (PreparedStatement fila = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

                fila.setString(1, entidad.getDireccion());
                int filas_afectadas = fila.executeUpdate();

                if(filas_afectadas == 0){
                    throw new SQLException("Fallo al guardar la dirección, no se modificaron filas.");
                }
                try (ResultSet generatedKeys = fila.getGeneratedKeys()){
                    if (generatedKeys.next()) {
                        entidad.setDireccion_id(generatedKeys.getInt(1));
                    }else{
                        throw new SQLException("Fallo al programa la direccion, no se obtuvo el ID generado.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("ERROR fallo al guardar la direccion, direccionImpl");
                System.out.println("Verifique el try de la linea 96");
                System.out.println("Error: " + e);
            } finally{
                try{
                    Conexion.getInstance().cerrarConexion();
                } catch (SQLException ex){
                    System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, direccionImpl");
                    System.out.println("Verifique el try de la linea 88");
                    System.out.println("Error: " + ex);
                }
            }
    }

    @Override
    public void eliminar(int id) {
        try (PreparedStatement fila = getConnection().prepareStatement("DELETE FROM direccion WHERE id_direccion = ?")){
            fila.setInt(1, id);
            fila.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR eliminando datos, direccionImpl");
            System.out.println("Verifique el try de la linea 120");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, direccionImpl");
                System.out.println("Verifique el try de la linea 128");
                System.out.println("Error: " + ex);
            }
        }
    }

    @Override
    public void modificar(direccion entidad) {
        String sql = """
                UPDATE direccion
                SET direccion = ?
                WHERE id_direccion = ?;
                """;
        try (PreparedStatement fila = getConnection().prepareStatement(sql)){
            fila.setString(1, entidad.getDireccion());
            fila.setInt(2, entidad.getDireccion_id());
            int filas_afectadas = fila.executeUpdate();

            if(filas_afectadas == 0){
                throw new SQLException("Fallo al modificar la direccion, no se modificaron filas.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR fallo al modificar la direccion, direccionImpl");
            System.out.println("Verifique el try de la linea 145");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, direccionImpl");
                System.out.println("Verifique el try de la linea 158");
                System.out.println("Error: " + ex);
            }
        }
    }
}
