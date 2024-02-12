package com.campuslands.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.campuslands.models.ciudad;
import com.campuslands.utils.Conexion;

public class ciudadImpl implements repository<ciudad>{
    private Connection getConnection() throws SQLException {
        return Conexion.getInstance().conectar();
    }   

    private ciudad crearCiudad(ResultSet campo) throws SQLException{
        ciudad c = new ciudad();
        c.setCiudad_id(campo.getInt("id_ciudad"));
        c.setCiudad_nombre(campo.getString("nombre_ciudad"));

        return c;
    }

    @Override
    public List<ciudad> listar() {
        List<ciudad> listarCiudades = new ArrayList<>();
        try (Statement fila = getConnection().createStatement();
            ResultSet resultado = fila.executeQuery("SELECT * FROM ciudad")
        ) {
            while (resultado.next()) {
                listarCiudades.add(crearCiudad(resultado));
            }
        } catch (SQLException e) {
            System.out.println("ERROR fallo al guardar la ciudad, ciudadImpl");
            System.out.println("Verifique el try de la linea 30");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, ciudadImpl");
                System.out.println("Verifique el try de la linea 41");
                System.out.println("Error: " + ex);
            }
        }
        return listarCiudades;
    }

    @Override
    public ciudad porCodigo(int id) {
        ciudad c = null;
        String sql = """
            SELECT * FROM ciudad
            WHERE id_ciudad = ?
                """;
        try (PreparedStatement modificacion = getConnection().prepareStatement(sql)){
            modificacion.setInt(1 , id);// Con esta modificacion ya tenemos la consulta completa
            ResultSet fila = modificacion.executeQuery();

            if(fila.next()){
                c = crearCiudad(fila);
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, ciudadImpl");
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
        return c;
    }

    @Override
    public void guardar(ciudad entidad) {
        String sql = """
                INSERT INTO ciudad(nombre_ciudad) VALUES
                (?);
                """;
            try (PreparedStatement fila = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                fila.setString(1, entidad.getCiudad_nombre());
                int filas_afectadas = fila.executeUpdate();
                if(filas_afectadas == 0){
                    throw new SQLException("Fallo al guardar la ciudad, no se modificaron filas.");
                }
                try (ResultSet generatedKeys = fila.getGeneratedKeys()){
                    if (generatedKeys.next()) {
                        entidad.setCiudad_id(generatedKeys.getInt(1));
                    }else{
                        throw new SQLException("Fallo al programa la ciudad, no se obtuvo el ID generado.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("ERROR fallo al guardar la ciudad, ciudadImpl");
                System.out.println("Verifique el try de la linea 79");
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
    }

    @Override
    public void eliminar(int id) {
        try (PreparedStatement fila = getConnection().prepareStatement("DELETE FROM ciudad WHERE id_ciudad = ?")){
            fila.setInt(1, id);
            fila.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR eliminando datos, ciudadImpl");
            System.out.println("Verifique el try de la linea 118");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, ciudadImpl");
                System.out.println("Verifique el try de la linea 126");
                System.out.println("Error: " + ex);
            }
        }
    }

    @Override
    public void modificar(ciudad entidad) {
        String sql = """
                UPDATE ciudad
                SET nombre_ciudad = ?
                WHERE id_ciudad = ?;
                """;
        try (PreparedStatement fila = getConnection().prepareStatement(sql)){
            fila.setString(1, entidad.getCiudad_nombre());
            fila.setInt(2, entidad.getCiudad_id());
            int filas_afectadas = fila.executeUpdate();

            if(filas_afectadas == 0){
                throw new SQLException("Fallo al modificar la ciudad, no se modificaron filas.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR fallo al modificar la ciudad, ciudadImpl");
            System.out.println("Verifique el try de la linea 136");
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
    }
}
