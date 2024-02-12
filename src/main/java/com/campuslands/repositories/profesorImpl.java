package com.campuslands.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.campuslands.models.profesor;
import com.campuslands.utils.Conexion;

public class profesorImpl implements repository<profesor>{
    private Connection getConnection() throws SQLException {
        return Conexion.getInstance().conectar();
    }   

    private profesor crearProfesor(ResultSet campo) throws SQLException{
        profesor p = new profesor();
        p.setProfesor_id(campo.getInt("id_profesor"));
        p.setDepartamento_id(campo.getInt("id_departamento"));
        p.setPersona_id(campo.getInt("id_persona"));

        return p;
    }

    @Override
    public List<profesor> listar() {
        List<profesor> listarProfesores = new ArrayList<>();
        try (Statement fila = getConnection().createStatement();
            ResultSet resultado = fila.executeQuery("SELECT * FROM profesor")
        ) {
            while (resultado.next()) {
                listarProfesores.add(crearProfesor(resultado));
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, profesorImpl");
            System.out.println("Verifique el try de la linea 31");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, profesorImpl");
                System.out.println("Verifique el try de la linea 42");
                System.out.println("Error: " + ex);
            }
        }
        return listarProfesores;
    }

    @Override
    public profesor porCodigo(int id) {
        profesor p = null;
        String sql = """
            SELECT * FROM profesor
            WHERE id_profesor = ?
                """;
        try (PreparedStatement modificacion = getConnection().prepareStatement(sql)){
            modificacion.setInt(1 , id);// Con esta modificacion ya tenemos la consulta completa
            ResultSet fila = modificacion.executeQuery();

            if(fila.next()){
                p = crearProfesor(fila);
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, profesorImpl");
            System.out.println("Verifique el try de la linea 60");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, profesorImpl");
                System.out.println("Verifique el try de la linea 72");
                System.out.println("Error: " + ex);
            }
        }
        return p;
    }

    @Override
    public void guardar(profesor entidad) {
        String sql = """
                INSERT INTO profesor(id_departamento, id_persona) VALUES
                (?, ?);
                """;
            try (PreparedStatement fila = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                fila.setInt(1, entidad.getDepartamento_id());
                fila.setInt(2, entidad.getPersona_id());
                int filas_afectadas = fila.executeUpdate();
                if(filas_afectadas == 0){
                    throw new SQLException("Fallo al guardar la profesor, no se modificaron filas.");
                }
                try (ResultSet generatedKeys = fila.getGeneratedKeys()){
                    if (generatedKeys.next()) {
                        entidad.setProfesor_id(generatedKeys.getInt(1));
                    }else{
                        throw new SQLException("Fallo al programa la profesor, no se obtuvo el ID generado.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("ERROR fallo al guardar la profesor, profesorImpl");
                System.out.println("Verifique el try de la linea 89");
                System.out.println("Error: " + e);
            } finally{
                try{
                    Conexion.getInstance().cerrarConexion();
                } catch (SQLException ex){
                    System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, profesorImpl");
                    System.out.println("Verifique el try de la linea 108");
                    System.out.println("Error: " + ex);
                }
            }
    }

    @Override
    public void eliminar(int id) {
        try (PreparedStatement fila = getConnection().prepareStatement("DELETE FROM profesor WHERE id_profesor = ?")){
            fila.setInt(1, id);
            fila.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR eliminando datos, profesorImpl");
            System.out.println("Verifique el try de la linea 120");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, profesorImpl");
                System.out.println("Verifique el try de la linea 128");
                System.out.println("Error: " + ex);
            }
        }
    }

    @Override
    public void modificar(profesor entidad) {
        String sql = """
                UPDATE profesor
                SET id_departamento = ?,
                    id_persona = ?
                WHERE id_profesor = ?;
                """;
        try (PreparedStatement fila = getConnection().prepareStatement(sql)){
            fila.setInt(1, entidad.getDepartamento_id());
            fila.setInt(2, entidad.getPersona_id());
            fila.setInt(3, entidad.getProfesor_id());
            int filas_afectadas = fila.executeUpdate();

            if(filas_afectadas == 0){
                throw new SQLException("Fallo al modificar la profesor, no se modificaron filas.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR fallo al modificar la profesor, profesorImpl");
            System.out.println("Verifique el try de la linea 146");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, profesorImpl");
                System.out.println("Verifique el try de la linea 160");
                System.out.println("Error: " + ex);
            }
        }
    }
}
