package com.campuslands.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.campuslands.models.asignatura;
import com.campuslands.utils.Conexion;

public class asignaturaImpl implements repository<asignatura>{
    private Connection getConnection() throws SQLException {
        return Conexion.getInstance().conectar();
    }   

    private asignatura crearAsignatura(ResultSet campo) throws SQLException{
        asignatura t = new asignatura();
        t.setAsignatura_id(campo.getInt("id_asignatura"));
        t.setAsignatura_nombre(campo.getString("nombre_asignatura"));
        t.setAsignatura_numero_creditos(campo.getInt("numero_creditos"));
        t.setProfesor_id(campo.getInt("id_profesor"));
        t.setCurso_id(campo.getInt("id_curso"));
        t.setPeriodo_id(campo.getInt("id_periodo"));

        return t;
    }

    @Override
    public List<asignatura> listar() {
        List<asignatura> listarAsignaturas = new ArrayList<>();
        try (Statement fila = getConnection().createStatement();
            ResultSet resultado = fila.executeQuery("SELECT * FROM asignatura")
        ) {
            while (resultado.next()) {
                listarAsignaturas.add(crearAsignatura(resultado));
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, asignaturaImpl");
            System.out.println("Verifique el try de la linea 34");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, asignaturaImpl");
                System.out.println("Verifique el try de la linea 45");
                System.out.println("Error: " + ex);
            }
        }
        return listarAsignaturas;
    }

    @Override
    public asignatura porCodigo(int id) {
        asignatura t = null;
        String sql = """
            SELECT * FROM asignatura
            WHERE id_asignatura = ?
                """;
        try (PreparedStatement modificacion = getConnection().prepareStatement(sql)){
            modificacion.setInt(1 , id);// Con esta modificacion ya tenemos la consulta completa
            ResultSet fila = modificacion.executeQuery();

            if(fila.next()){
                t = crearAsignatura(fila);
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, asignaturaImpl");
            System.out.println("Verifique el try de la linea 63");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, asignaturaImpl");
                System.out.println("Verifique el try de la linea 75");
                System.out.println("Error: " + ex);
            }
        }
        return t;
    }

    @Override
    public void guardar(asignatura entidad) {
        String sql = """
                INSERT INTO asignatura(nombre_asignatura, numero_creditos, id_profesor, id_curso, id_periodo) VALUES
                (?, ?, ?, ?, ?);
                """;
            try (PreparedStatement fila = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                fila.setString(1, entidad.getAsignatura_nombre());
                fila.setInt(2, entidad.getAsignatura_numero_creditos());
                fila.setInt(3, entidad.getProfesor_id());
                fila.setInt(4, entidad.getCurso_id());
                fila.setInt(5, entidad.getPeriodo_id());
                int filas_afectadas = fila.executeUpdate();
                if(filas_afectadas == 0){
                    throw new SQLException("Fallo al guardar el asignatura, no se modificaron filas.");
                }
                try (ResultSet generatedKeys = fila.getGeneratedKeys()){
                    if (generatedKeys.next()) {
                        entidad.setAsignatura_id(generatedKeys.getInt(1));
                    }else{
                        throw new SQLException("Fallo al asignatura la asignatura, no se obtuvo el ID generado.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("ERROR fallo al guardar la asignatura, asignaturaImpl");
                System.out.println("Verifique el try de la linea 92");
                System.out.println("Error: " + e);
            } finally{
                try{
                    Conexion.getInstance().cerrarConexion();
                } catch (SQLException ex){
                    System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, asignaturaImpl");
                    System.out.println("Verifique el try de la linea 114");
                    System.out.println("Error: " + ex);
                }
            }
    }

    @Override
    public void eliminar(int id) {
        try (PreparedStatement fila = getConnection().prepareStatement("DELETE FROM asignatura WHERE id_asignatura = ?")){
            fila.setInt(1, id);
            fila.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR eliminando datos, asignaturaImpl");
            System.out.println("Verifique el try de la linea 126");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, asignaturaImpl");
                System.out.println("Verifique el try de la linea 134");
                System.out.println("Error: " + ex);
            }
        }
    }

    @Override
    public void modificar(asignatura entidad) {
        String sql = """
                UPDATE asignatura
                SET nombre_asignatura = ?,
                    numero_creditos = ?,
                    id_profesor = ?,
                    id_curso = ?,
                    id_periodo = ?
                WHERE id_asignatura = ?;
                """;
        try (PreparedStatement fila = getConnection().prepareStatement(sql)){
            fila.setString(1, entidad.getAsignatura_nombre());
            fila.setInt(2, entidad.getAsignatura_numero_creditos());
            fila.setInt(3, entidad.getProfesor_id());
            fila.setInt(4, entidad.getCurso_id());
            fila.setInt(5, entidad.getPeriodo_id());
            fila.setInt(6, entidad.getAsignatura_id());
            int filas_afectadas = fila.executeUpdate();

            if(filas_afectadas == 0){
                throw new SQLException("Fallo al modificar la asignatura, no se modificaron filas.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR fallo al modificar la asignatura, asignaturaImpl");
            System.out.println("Verifique el try de la linea 155");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, asignaturaImpl");
                System.out.println("Verifique el try de la linea 172");
                System.out.println("Error: " + ex);
            }
        }
    }
}
