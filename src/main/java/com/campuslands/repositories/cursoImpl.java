package com.campuslands.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.campuslands.models.curso;
import com.campuslands.utils.Conexion;

public class cursoImpl implements repository<curso>{
    private Connection getConnection() throws SQLException {
        return Conexion.getInstance().conectar();
    }   

    private curso crearCurso(ResultSet campo) throws SQLException{
        curso t = new curso();
        t.setCurso_id(campo.getInt("id_curso"));
        t.setCurso_nombre(campo.getString("nombre_curso"));
        t.setCurso_guia_catedra(campo.getString("guia_catedra"));

        return t;
    }

    @Override
    public List<curso> listar() {
        List<curso> listarCurso = new ArrayList<>();
        try (Statement fila = getConnection().createStatement();
            ResultSet resultado = fila.executeQuery("SELECT * FROM curso")
        ) {
            while (resultado.next()) {
                listarCurso.add(crearCurso(resultado));
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, cursoImpl");
            System.out.println("Verifique el try de la linea 31");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, cursoImpl");
                System.out.println("Verifique el try de la linea 42");
                System.out.println("Error: " + ex);
            }
        }
        return listarCurso;
    }

    @Override
    public curso porCodigo(int id) {
        curso c = null;
        String sql = """
            SELECT * FROM curso
            WHERE id_curso = ?
                """;
        try (PreparedStatement modificacion = getConnection().prepareStatement(sql)){
            modificacion.setInt(1 , id);// Con esta modificacion ya tenemos la consulta completa
            ResultSet fila = modificacion.executeQuery();

            if(fila.next()){
                c = crearCurso(fila);
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, cursoImpl");
            System.out.println("Verifique el try de la linea 61");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, cursoImpl");
                System.out.println("Verifique el try de la linea 73");
                System.out.println("Error: " + ex);
            }
        }
        return c;
    }

    @Override
    public void guardar(curso entidad) {
        String sql = """
                INSERT INTO curso(nombre_curso, guia_catedra) VALUES
                (?, ?);
                """;
            try (PreparedStatement fila = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                fila.setString(1, entidad.getCurso_nombre());
                fila.setString(2, entidad.getCurso_guia_catedra());
                int filas_afectadas = fila.executeUpdate();
                if(filas_afectadas == 0){
                    throw new SQLException("Fallo al guardar el curso, no se modificaron filas.");
                }
                try (ResultSet generatedKeys = fila.getGeneratedKeys()){
                    if (generatedKeys.next()) {
                        entidad.setCurso_id(generatedKeys.getInt(1));
                    }else{
                        throw new SQLException("Fallo al curso la curso, no se obtuvo el ID generado.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("ERROR fallo al guardar la curso, cursoImpl");
                System.out.println("Verifique el try de la linea 90");
                System.out.println("Error: " + e);
            } finally{
                try{
                    Conexion.getInstance().cerrarConexion();
                } catch (SQLException ex){
                    System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, cursoImpl");
                    System.out.println("Verifique el try de la linea 110");
                    System.out.println("Error: " + ex);
                }
            }
    }

    @Override
    public void eliminar(int id) {
        try (PreparedStatement fila = getConnection().prepareStatement("DELETE FROM curso WHERE id_curso = ?")){
            fila.setInt(1, id);
            fila.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR eliminando datos, cursoImpl");
            System.out.println("Verifique el try de la linea 122");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, cursoImpl");
                System.out.println("Verifique el try de la linea 130");
                System.out.println("Error: " + ex);
            }
        }
    }

    @Override
    public void modificar(curso entidad) {
        String sql = """
                UPDATE curso
                SET nombre_curso = ?,
                    guia_catedra = ?
                WHERE id_curso = ?;
                """;
        try (PreparedStatement fila = getConnection().prepareStatement(sql)){
            fila.setString(1, entidad.getCurso_nombre());
            fila.setString(2, entidad.getCurso_guia_catedra());
            fila.setInt(3, entidad.getCurso_id());
            int filas_afectadas = fila.executeUpdate();

            if(filas_afectadas == 0){
                throw new SQLException("Fallo al modificar la curso, no se modificaron filas.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR fallo al modificar la curso, cursoImpl");
            System.out.println("Verifique el try de la linea 149");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, cursoImpl");
                System.out.println("Verifique el try de la linea 164");
                System.out.println("Error: " + ex);
            }
        }
    }
}
