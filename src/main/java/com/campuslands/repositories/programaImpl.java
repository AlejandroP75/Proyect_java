package com.campuslands.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.campuslands.models.programa;
import com.campuslands.utils.Conexion;

public class programaImpl implements repository<programa>{
    private Connection getConnection() throws SQLException {
        return Conexion.getInstance().conectar();
    }   

    private programa crearPrograma(ResultSet campo) throws SQLException{
        programa p = new programa();
        p.setPrograma_id(campo.getInt("id_programa"));
        p.setPrograma_nombre(campo.getString("nombre_programa"));
        p.setPrograma_nivel(campo.getString("nivel_programa"));

        return p;
    }

    @Override
    public List<programa> listar() {
        List<programa> listarProgramas = new ArrayList<>();
        try (Statement fila = getConnection().createStatement();
            ResultSet resultado = fila.executeQuery("SELECT * FROM programa")
        ) {
            while (resultado.next()) {
                listarProgramas.add(crearPrograma(resultado));
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, programaImpl");
            System.out.println("Verifique el try de la linea 31");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, programaImpl");
                System.out.println("Verifique el try de la linea 42");
                System.out.println("Error: " + ex);
            }
        }
        return listarProgramas;
    }

    @Override
    public programa porCodigo(int id) {
        programa p = null;
        String sql = """
            SELECT * FROM programa
            WHERE id_programa = ?
                """;
        try (PreparedStatement modificacion = getConnection().prepareStatement(sql)){
            modificacion.setInt(1 , id);// Con esta modificacion ya tenemos la consulta completa
            ResultSet fila = modificacion.executeQuery();

            if(fila.next()){
                p = crearPrograma(fila);
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, programaImpl");
            System.out.println("Verifique el try de la linea 60");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, programaImpl");
                System.out.println("Verifique el try de la linea 72");
                System.out.println("Error: " + ex);
            }
        }
        return p;
    }

    @Override
    public void guardar(programa entidad) {
        String sql = """
                INSERT INTO programa(nombre_programa, nivel_programa) VALUES
                (?, ?);
                """;
            try (PreparedStatement fila = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                fila.setString(1, entidad.getPrograma_nombre());
                fila.setString(2, entidad.getPrograma_nivel());
                int filas_afectadas = fila.executeUpdate();
                if(filas_afectadas == 0){
                    throw new SQLException("Fallo al guardar el programa, no se modificaron filas.");
                }
                try (ResultSet generatedKeys = fila.getGeneratedKeys()){
                    if (generatedKeys.next()) {
                        entidad.setPrograma_id(generatedKeys.getInt(1));
                    }else{
                        throw new SQLException("Fallo al programa la programa, no se obtuvo el ID generado.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("ERROR fallo al guardar la programa, programaImpl");
                System.out.println("Verifique el try de la linea 89");
                System.out.println("Error: " + e);
            } finally{
                try{
                    Conexion.getInstance().cerrarConexion();
                } catch (SQLException ex){
                    System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, programaImpl");
                    System.out.println("Verifique el try de la linea 108");
                    System.out.println("Error: " + ex);
                }
            }
    }

    @Override
    public void eliminar(int id) {
        try (PreparedStatement fila = getConnection().prepareStatement("DELETE FROM programa WHERE id_programa = ?")){
            fila.setInt(1, id);
            fila.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR eliminando datos, programaImpl");
            System.out.println("Verifique el try de la linea 120");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, programaImpl");
                System.out.println("Verifique el try de la linea 128");
                System.out.println("Error: " + ex);
            }
        }
    }

    @Override
    public void modificar(programa entidad) {
        String sql = """
                UPDATE programa
                SET nombre_programa = ?,
                    nivel_programa = ?
                WHERE id_programa = ?;
                """;
        try (PreparedStatement fila = getConnection().prepareStatement(sql)){
            fila.setString(1, entidad.getPrograma_nombre());
            fila.setString(2, entidad.getPrograma_nivel());
            fila.setInt(3, entidad.getPrograma_id());
            int filas_afectadas = fila.executeUpdate();

            if(filas_afectadas == 0){
                throw new SQLException("Fallo al modificar la programa, no se modificaron filas.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR fallo al modificar la programa, programaImpl");
            System.out.println("Verifique el try de la linea 146");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, programaImpl");
                System.out.println("Verifique el try de la linea 160");
                System.out.println("Error: " + ex);
            }
        }
    }
}
