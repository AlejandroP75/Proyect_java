package com.campuslands.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.campuslands.models.periodo;
import com.campuslands.utils.Conexion;

public class periodoImpl implements repository<periodo>{
    private Connection getConnection() throws SQLException {
        return Conexion.getInstance().conectar();
    }   

    private periodo crearPeriodo(ResultSet campo) throws SQLException{
        periodo t = new periodo();
        t.setPeriodo_id(campo.getInt("id_periodo"));
        t.setPeriodo_anio(campo.getInt("año"));
        t.setPeriodo_semestre(campo.getInt("semestre"));
        t.setPeriodo_nombre(campo.getString("nombre_periodo"));

        return t;
    }

    @Override
    public List<periodo> listar() {
        List<periodo> listarPeriodos = new ArrayList<>();
        try (Statement fila = getConnection().createStatement();
            ResultSet resultado = fila.executeQuery("SELECT * FROM periodo")
        ) {
            while (resultado.next()) {
                listarPeriodos.add(crearPeriodo(resultado));
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, periodoImpl");
            System.out.println("Verifique el try de la linea 32");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, periodoImpl");
                System.out.println("Verifique el try de la linea 43");
                System.out.println("Error: " + ex);
            }
        }
        return listarPeriodos;
    }

    @Override
    public periodo porCodigo(int id) {
        periodo t = null;
        String sql = """
            SELECT * FROM periodo
            WHERE id_periodo = ?
                """;
        try (PreparedStatement modificacion = getConnection().prepareStatement(sql)){
            modificacion.setInt(1 , id);// Con esta modificacion ya tenemos la consulta completa
            ResultSet fila = modificacion.executeQuery();

            if(fila.next()){
                t = crearPeriodo(fila);
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, periodoImpl");
            System.out.println("Verifique el try de la linea 61");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, periodoImpl");
                System.out.println("Verifique el try de la linea 73");
                System.out.println("Error: " + ex);
            }
        }
        return t;
    }

    @Override
    public void guardar(periodo entidad) {
        String sql = """
                INSERT INTO periodo(año, semestre, nombre_periodo) VALUES
                (?, ?, ?);
                """;
            try (PreparedStatement fila = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                fila.setInt(1, entidad.getPeriodo_anio());
                fila.setInt(2, entidad.getPeriodo_semestre());
                fila.setString(3, entidad.getPeriodo_nombre());
                int filas_afectadas = fila.executeUpdate();
                if(filas_afectadas == 0){
                    throw new SQLException("Fallo al guardar el periodo, no se modificaron filas.");
                }
                try (ResultSet generatedKeys = fila.getGeneratedKeys()){
                    if (generatedKeys.next()) {
                        entidad.setPeriodo_id(generatedKeys.getInt(1));
                    }else{
                        throw new SQLException("Fallo al periodo la periodo, no se obtuvo el ID generado.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("ERROR fallo al guardar la periodo, periodoImpl");
                System.out.println("Verifique el try de la linea 90");
                System.out.println("Error: " + e);
            } finally{
                try{
                    Conexion.getInstance().cerrarConexion();
                } catch (SQLException ex){
                    System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, periodoImpl");
                    System.out.println("Verifique el try de la linea 110");
                    System.out.println("Error: " + ex);
                }
            }
    }

    @Override
    public void eliminar(int id) {
        try (PreparedStatement fila = getConnection().prepareStatement("DELETE FROM periodo WHERE id_periodo = ?")){
            fila.setInt(1, id);
            fila.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR eliminando datos, periodoImpl");
            System.out.println("Verifique el try de la linea 122");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, periodoImpl");
                System.out.println("Verifique el try de la linea 130");
                System.out.println("Error: " + ex);
            }
        }
    }

    @Override
    public void modificar(periodo entidad) {
        String sql = """
                UPDATE periodo
                SET año = ?,
                    semestre = ?,
                    nombre_periodo = ?
                WHERE id_periodo = ?;
                """;
        try (PreparedStatement fila = getConnection().prepareStatement(sql)){
            fila.setInt(1, entidad.getPeriodo_anio());
            fila.setInt(2, entidad.getPeriodo_semestre());
            fila.setString(3, entidad.getPeriodo_nombre());
            fila.setInt(4, entidad.getPeriodo_id());
            int filas_afectadas = fila.executeUpdate();

            if(filas_afectadas == 0){
                throw new SQLException("Fallo al modificar la periodo, no se modificaron filas.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR fallo al modificar la periodo, periodoImpl");
            System.out.println("Verifique el try de la linea 149");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, periodoImpl");
                System.out.println("Verifique el try de la linea 164");
                System.out.println("Error: " + ex);
            }
        }
    }
}
