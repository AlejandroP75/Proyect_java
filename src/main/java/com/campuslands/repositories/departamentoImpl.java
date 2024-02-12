package com.campuslands.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.campuslands.models.departamento;
import com.campuslands.utils.Conexion;

public class departamentoImpl implements repository<departamento>{
    private Connection getConnection() throws SQLException {
        return Conexion.getInstance().conectar();
    }   

    private departamento crearDepartamento(ResultSet campo) throws SQLException{
        departamento d = new departamento();
        d.setDepartamento_id(campo.getInt("id_departamento"));
        d.setDepartamento_nombre(campo.getString("nombre_departamento"));

        return d;
    }

    @Override
    public List<departamento> listar() {
        List<departamento> listarDepartamentos = new ArrayList<>();
        try (Statement fila = getConnection().createStatement();
            ResultSet resultado = fila.executeQuery("SELECT * FROM departamento")
        ) {
            while (resultado.next()) {
                listarDepartamentos.add(crearDepartamento(resultado));
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, departamentoImpl");
            System.out.println("Verifique el try de la linea 30");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, departamentoImpl");
                System.out.println("Verifique el try de la linea 41");
                System.out.println("Error: " + ex);
            }
        }
        return listarDepartamentos;
    }

    @Override
    public departamento porCodigo(int id) {
        departamento d = null;
        String sql = """
            SELECT * FROM departamento
            WHERE id_departamento = ?
                """;
        try (PreparedStatement modificacion = getConnection().prepareStatement(sql)){
            modificacion.setInt(1 , id);// Con esta modificacion ya tenemos la consulta completa
            ResultSet fila = modificacion.executeQuery();

            if(fila.next()){
                d = crearDepartamento(fila);
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, departamentoImpl");
            System.out.println("Verifique el try de la linea 59");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, departamentoImpl");
                System.out.println("Verifique el try de la linea 71");
                System.out.println("Error: " + ex);
            }
        }
        return d;
    }

    @Override
    public void guardar(departamento entidad) {
        String sql = """
                INSERT INTO departamento(nombre_departamento) VALUES
                (?);
                """;
            try (PreparedStatement fila = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                fila.setString(1, entidad.getDepartamento_nombre());
                int filas_afectadas = fila.executeUpdate();
                if(filas_afectadas == 0){
                    throw new SQLException("Fallo al guardar la departamento, no se modificaron filas.");
                }
                try (ResultSet generatedKeys = fila.getGeneratedKeys()){
                    if (generatedKeys.next()) {
                        entidad.setDepartamento_id(generatedKeys.getInt(1));
                    }else{
                        throw new SQLException("Fallo al programa la departamento, no se obtuvo el ID generado.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("ERROR fallo al guardar la departamento, departamentoImpl");
                System.out.println("Verifique el try de la linea 88");
                System.out.println("Error: " + e);
            } finally{
                try{
                    Conexion.getInstance().cerrarConexion();
                } catch (SQLException ex){
                    System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, departamentoImpl");
                    System.out.println("Verifique el try de la linea 106");
                    System.out.println("Error: " + ex);
                }
            }
    }

    @Override
    public void eliminar(int id) {
        try (PreparedStatement fila = getConnection().prepareStatement("DELETE FROM departamento WHERE id_departamento=?")){
            fila.setInt(1, id);
            fila.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR eliminando datos, departamentoImpl");
            System.out.println("Verifique el try de la linea 118");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, departamentoImpl");
                System.out.println("Verifique el try de la linea 126");
                System.out.println("Error: " + ex);
            }
        }
    }

    @Override
    public void modificar(departamento entidad) {
        String sql = """
                UPDATE departamento
                SET nombre_departamento = ?
                WHERE id_departamento = ?;
                """;
        try (PreparedStatement fila = getConnection().prepareStatement(sql)){
            fila.setString(1, entidad.getDepartamento_nombre());
            fila.setInt(2, entidad.getDepartamento_id());
            int filas_afectadas = fila.executeUpdate();

            if(filas_afectadas == 0){
                throw new SQLException("Fallo al modificar la departamento, no se modificaron filas.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR fallo al modificar la departamento, departamentoImpl");
            System.out.println("Verifique el try de la linea 146");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, departamentoImpl");
                System.out.println("Verifique el try de la linea 156");
                System.out.println("Error: " + ex);
            }
        }
    }
}
