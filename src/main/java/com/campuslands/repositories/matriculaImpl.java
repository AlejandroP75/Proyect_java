package com.campuslands.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.campuslands.models.matricula;
import com.campuslands.utils.Conexion;

public class matriculaImpl implements repository<matricula>{
    private Connection getConnection() throws SQLException {
        return Conexion.getInstance().conectar();
    }   

    private matricula crearMatricula(ResultSet campo) throws SQLException{
        matricula t = new matricula();
        t.setMatricula_id(campo.getInt("id_matricula"));
        t.setAlumno_id(campo.getInt("id_alumno"));
        t.setAsignatura_id(campo.getInt("id_asignatura"));
        t.setPeriodo_id(campo.getInt("id_periodo"));

        return t;
    }

    @Override
    public List<matricula> listar() {
        List<matricula> listarMatriculas = new ArrayList<>();
        try (Statement fila = getConnection().createStatement();
            ResultSet resultado = fila.executeQuery("SELECT * FROM matricula")
        ) {
            while (resultado.next()) {
                listarMatriculas.add(crearMatricula(resultado));
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, matriculaImpl");
            System.out.println("Verifique el try de la linea 32");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, matriculaImpl");
                System.out.println("Verifique el try de la linea 43");
                System.out.println("Error: " + ex);
            }
        }
        return listarMatriculas;
    }

    @Override
    public matricula porCodigo(int id) {
        matricula t = null;
        String sql = """
            SELECT * FROM matricula
            WHERE id_matricula = ?
                """;
        try (PreparedStatement modificacion = getConnection().prepareStatement(sql)){
            modificacion.setInt(1 , id);// Con esta modificacion ya tenemos la consulta completa
            ResultSet fila = modificacion.executeQuery();

            if(fila.next()){
                t = crearMatricula(fila);
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, matriculaImpl");
            System.out.println("Verifique el try de la linea 61");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, matriculaImpl");
                System.out.println("Verifique el try de la linea 73");
                System.out.println("Error: " + ex);
            }
        }
        return t;
    }

    @Override
    public void guardar(matricula entidad) {
        String sql = """
                INSERT INTO matricula(id_alumno, id_asignatura, id_periodo) VALUES
                (?, ?, ?);
                """;
            try (PreparedStatement fila = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                fila.setInt(1, entidad.getAlumno_id());
                fila.setInt(2, entidad.getAsignatura_id());
                fila.setInt(3, entidad.getPeriodo_id());
                int filas_afectadas = fila.executeUpdate();
                if(filas_afectadas == 0){
                    throw new SQLException("Fallo al guardar el matricula, no se modificaron filas.");
                }
                try (ResultSet generatedKeys = fila.getGeneratedKeys()){
                    if (generatedKeys.next()) {
                        entidad.setMatricula_id(generatedKeys.getInt(1));
                    }else{
                        throw new SQLException("Fallo al matricula la matricula, no se obtuvo el ID generado.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("ERROR fallo al guardar la matricula, matriculaImpl");
                System.out.println("Verifique el try de la linea 90");
                System.out.println("Error: " + e);
            } finally{
                try{
                    Conexion.getInstance().cerrarConexion();
                } catch (SQLException ex){
                    System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, matriculaImpl");
                    System.out.println("Verifique el try de la linea 110");
                    System.out.println("Error: " + ex);
                }
            }
    }

    @Override
    public void eliminar(int id) {
        try (PreparedStatement fila = getConnection().prepareStatement("DELETE FROM matricula WHERE id_matricula = ?")){
            fila.setInt(1, id);
            fila.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR eliminando datos, matriculaImpl");
            System.out.println("Verifique el try de la linea 122");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, matriculaImpl");
                System.out.println("Verifique el try de la linea 130");
                System.out.println("Error: " + ex);
            }
        }
    }

    @Override
    public void modificar(matricula entidad) {
        String sql = """
                UPDATE matricula
                SET id_alumno = ?,
                    id_asignatura = ?,
                    id_periodo = ?
                WHERE id_matricula = ?;
                """;
        try (PreparedStatement fila = getConnection().prepareStatement(sql)){
            fila.setInt(1, entidad.getAlumno_id());
            fila.setInt(2, entidad.getAsignatura_id());
            fila.setInt(3, entidad.getPeriodo_id());
            fila.setInt(4, entidad.getMatricula_id());
            int filas_afectadas = fila.executeUpdate();

            if(filas_afectadas == 0){
                throw new SQLException("Fallo al modificar la matricula, no se modificaron filas.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR fallo al modificar la matricula, matriculaImpl");
            System.out.println("Verifique el try de la linea 149");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, matriculaImpl");
                System.out.println("Verifique el try de la linea 164");
                System.out.println("Error: " + ex);
            }
        }
    }
}
