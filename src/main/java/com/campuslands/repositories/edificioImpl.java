package com.campuslands.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.campuslands.models.edificio;
import com.campuslands.utils.Conexion;

public class edificioImpl implements repository<edificio>{
    private Connection getConnection() throws SQLException {
        return Conexion.getInstance().conectar();
    }   

    private edificio crearEdificio(ResultSet campo) throws SQLException{
        edificio t = new edificio();
        t.setEdificio_id(campo.getInt("id_edificio"));
        t.setEdificio_nombre(campo.getString("nombre"));
        t.setEdificio_pisos(campo.getInt("numero_pisos"));

        return t;
    }

    @Override
    public List<edificio> listar() {
        List<edificio> listarEdificios = new ArrayList<>();
        try (Statement fila = getConnection().createStatement();
            ResultSet resultado = fila.executeQuery("SELECT * FROM edificio")
        ) {
            while (resultado.next()) {
                listarEdificios.add(crearEdificio(resultado));
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, edificioImpl");
            System.out.println("Verifique el try de la linea 31");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, edificioImpl");
                System.out.println("Verifique el try de la linea 42");
                System.out.println("Error: " + ex);
            }
        }
        return listarEdificios;
    }

    @Override
    public edificio porCodigo(int id) {
        edificio t = null;
        String sql = """
            SELECT * FROM edificio
            WHERE id_edificio = ?
                """;
        try (PreparedStatement modificacion = getConnection().prepareStatement(sql)){
            modificacion.setInt(1 , id);// Con esta modificacion ya tenemos la consulta completa
            ResultSet fila = modificacion.executeQuery();

            if(fila.next()){
                t = crearEdificio(fila);
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, edificioImpl");
            System.out.println("Verifique el try de la linea 60");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, edificioImpl");
                System.out.println("Verifique el try de la linea 72");
                System.out.println("Error: " + ex);
            }
        }
        return t;
    }

    @Override
    public void guardar(edificio entidad) {
        String sql = """
                INSERT INTO edificio(nombre, numero_pisos) VALUES
                (?, ?);
                """;
            try (PreparedStatement fila = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                fila.setString(1, entidad.getEdificio_nombre());
                fila.setInt(2, entidad.getEdificio_pisos());

                int filas_afectadas = fila.executeUpdate();
                if(filas_afectadas == 0){
                    throw new SQLException("Fallo al guardar el edificio, no se modificaron filas.");
                }
                try (ResultSet generatedKeys = fila.getGeneratedKeys()){
                    if (generatedKeys.next()) {
                        entidad.setEdificio_id(generatedKeys.getInt(1));
                    }else{
                        throw new SQLException("Fallo al edificio la edificio, no se obtuvo el ID generado.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("ERROR fallo al guardar la edificio, edificioImpl");
                System.out.println("Verifique el try de la linea 89");
                System.out.println("Error: " + e);
            } finally{
                try{
                    Conexion.getInstance().cerrarConexion();
                } catch (SQLException ex){
                    System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, edificioImpl");
                    System.out.println("Verifique el try de la linea 109");
                    System.out.println("Error: " + ex);
                }
            }
    }

    @Override
    public void eliminar(int id) {
        try (PreparedStatement fila = getConnection().prepareStatement("DELETE FROM edificio WHERE id_edificio = ?")){
            fila.setInt(1, id);
            fila.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR eliminando datos, edificioImpl");
            System.out.println("Verifique el try de la linea 121");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, edificioImpl");
                System.out.println("Verifique el try de la linea 129");
                System.out.println("Error: " + ex);
            }
        }
    }

    @Override
    public void modificar(edificio entidad) {
        String sql = """
                UPDATE edificio
                SET nombre = ?,
                    numero_pisos = ?
                WHERE id_edificio = ?;
                """;
        try (PreparedStatement fila = getConnection().prepareStatement(sql)){
            fila.setString(1, entidad.getEdificio_nombre());
            fila.setInt(2, entidad.getEdificio_pisos());
            fila.setInt(3, entidad.getEdificio_id());
            int filas_afectadas = fila.executeUpdate();

            if(filas_afectadas == 0){
                throw new SQLException("Fallo al modificar la edificio, no se modificaron filas.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR fallo al modificar la edificio, edificioImpl");
            System.out.println("Verifique el try de la linea 147");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, edificioImpl");
                System.out.println("Verifique el try de la linea 161");
                System.out.println("Error: " + ex);
            }
        }
    }
}
