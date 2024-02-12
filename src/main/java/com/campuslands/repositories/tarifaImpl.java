package com.campuslands.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.campuslands.models.tarifa;
import com.campuslands.utils.Conexion;

public class tarifaImpl implements repository<tarifa>{
    private Connection getConnection() throws SQLException {
        return Conexion.getInstance().conectar();
    }   

    private tarifa crearTarifa(ResultSet campo) throws SQLException{
        tarifa t = new tarifa();
        t.setTarifa_id(campo.getInt("id_tarifa"));
        t.setTarifa_valor_credito(campo.getDouble("valor_credito"));
        t.setPrograma_id(campo.getInt("id_programa"));
        t.setPeriodo_id(campo.getInt("id_periodo"));

        return t;
    }

    @Override
    public List<tarifa> listar() {
        List<tarifa> listarTarifas = new ArrayList<>();
        try (Statement fila = getConnection().createStatement();
            ResultSet resultado = fila.executeQuery("SELECT * FROM tarifa")
        ) {
            while (resultado.next()) {
                listarTarifas.add(crearTarifa(resultado));
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, tarifaImpl");
            System.out.println("Verifique el try de la linea 32");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, tarifaImpl");
                System.out.println("Verifique el try de la linea 43");
                System.out.println("Error: " + ex);
            }
        }
        return listarTarifas;
    }

    @Override
    public tarifa porCodigo(int id) {
        tarifa t = null;
        String sql = """
            SELECT * FROM tarifa
            WHERE id_tarifa = ?
                """;
        try (PreparedStatement modificacion = getConnection().prepareStatement(sql)){
            modificacion.setInt(1 , id);// Con esta modificacion ya tenemos la consulta completa
            ResultSet fila = modificacion.executeQuery();

            if(fila.next()){
                t = crearTarifa(fila);
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, tarifaImpl");
            System.out.println("Verifique el try de la linea 61");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, tarifaImpl");
                System.out.println("Verifique el try de la linea 73");
                System.out.println("Error: " + ex);
            }
        }
        return t;
    }

    @Override
    public void guardar(tarifa entidad) {
        String sql = """
                INSERT INTO tarifa(valor_credito, id_programa, id_periodo) VALUES
                (?, ?, ?);
                """;
            try (PreparedStatement fila = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                fila.setDouble(1, entidad.getTarifa_valor_credito());
                fila.setInt(2, entidad.getPrograma_id());
                fila.setInt(3, entidad.getPeriodo_id());
                int filas_afectadas = fila.executeUpdate();
                if(filas_afectadas == 0){
                    throw new SQLException("Fallo al guardar el tarifa, no se modificaron filas.");
                }
                try (ResultSet generatedKeys = fila.getGeneratedKeys()){
                    if (generatedKeys.next()) {
                        entidad.setTarifa_id(generatedKeys.getInt(1));
                    }else{
                        throw new SQLException("Fallo al tarifa la tarifa, no se obtuvo el ID generado.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("ERROR fallo al guardar la tarifa, tarifaImpl");
                System.out.println("Verifique el try de la linea 90");
                System.out.println("Error: " + e);
            } finally{
                try{
                    Conexion.getInstance().cerrarConexion();
                } catch (SQLException ex){
                    System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, tarifaImpl");
                    System.out.println("Verifique el try de la linea 110");
                    System.out.println("Error: " + ex);
                }
            }
    }

    @Override
    public void eliminar(int id) {
        try (PreparedStatement fila = getConnection().prepareStatement("DELETE FROM tarifa WHERE id_tarifa = ?")){
            fila.setInt(1, id);
            fila.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR eliminando datos, tarifaImpl");
            System.out.println("Verifique el try de la linea 122");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, tarifaImpl");
                System.out.println("Verifique el try de la linea 130");
                System.out.println("Error: " + ex);
            }
        }
    }

    @Override
    public void modificar(tarifa entidad) {
        String sql = """
                UPDATE tarifa
                SET id_programa = ?,
                    id_periodo = ?,
                    valor_credito = ?
                WHERE id_tarifa = ?;
                """;
        try (PreparedStatement fila = getConnection().prepareStatement(sql)){
            fila.setInt(1, entidad.getPrograma_id());
            fila.setInt(2, entidad.getPeriodo_id());
            fila.setDouble(3, entidad.getTarifa_valor_credito());
            fila.setInt(4, entidad.getTarifa_id());
            int filas_afectadas = fila.executeUpdate();

            if(filas_afectadas == 0){
                throw new SQLException("Fallo al modificar la tarifa, no se modificaron filas.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR fallo al modificar la tarifa, tarifaImpl");
            System.out.println("Verifique el try de la linea 149");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, tarifaImpl");
                System.out.println("Verifique el try de la linea 164");
                System.out.println("Error: " + ex);
            }
        }
    }
}
