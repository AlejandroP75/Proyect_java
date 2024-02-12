package com.campuslands.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.campuslands.models.alumno;
import com.campuslands.utils.Conexion;

public class alumnoImpl implements repository<alumno>{
    private Connection getConnection() throws SQLException {
        return Conexion.getInstance().conectar();
    }   

    private alumno crearAlumno(ResultSet campo) throws SQLException{
        alumno a = new alumno();
        a.setAlumno_id(campo.getInt("id_alumno"));
        a.setPrograma_id(campo.getInt("id_programa"));
        a.setPersona_id(campo.getInt("id_persona"));

        return a;
    }

    @Override
    public List<alumno> listar() {
        List<alumno> listarAlumnos = new ArrayList<>();
        try (Statement fila = getConnection().createStatement();
            ResultSet resultado = fila.executeQuery("SELECT * FROM alumno")
        ) {
            while (resultado.next()) {
                listarAlumnos.add(crearAlumno(resultado));
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, alumnoImpl");
            System.out.println("Verifique el try de la linea 31");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, alumnoImpl");
                System.out.println("Verifique el try de la linea 42");
                System.out.println("Error: " + ex);
            }
        }
        return listarAlumnos;
    }

    @Override
    public alumno porCodigo(int id) {
        alumno a = null;
        String sql = """
            SELECT * FROM alumno
            WHERE id_alumno = ?
                """;
        try (PreparedStatement modificacion = getConnection().prepareStatement(sql)){
            modificacion.setInt(1 , id);// Con esta modificacion ya tenemos la consulta completa
            ResultSet fila = modificacion.executeQuery();

            if(fila.next()){
                a = crearAlumno(fila);
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, alumnoImpl");
            System.out.println("Verifique el try de la linea 60");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, alumnoImpl");
                System.out.println("Verifique el try de la linea 72");
                System.out.println("Error: " + ex);
            }
        }
        return a;
    }

    @Override
    public void guardar(alumno entidad) {
        String sql = """
                INSERT INTO alumno(id_programa, id_persona) VALUES
                (?, ?);
                """;
            try (PreparedStatement fila = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                fila.setInt(1, entidad.getPrograma_id());
                fila.setInt(2, entidad.getPersona_id());
                int filas_afectadas = fila.executeUpdate();
                if(filas_afectadas == 0){
                    throw new SQLException("Fallo al guardar el alumno, no se modificaron filas.");
                }
                try (ResultSet generatedKeys = fila.getGeneratedKeys()){
                    if (generatedKeys.next()) {
                        entidad.setAlumno_id(generatedKeys.getInt(1));
                    }else{
                        throw new SQLException("Fallo al programa la alumno, no se obtuvo el ID generado.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("ERROR fallo al guardar la alumno, alumnoImpl");
                System.out.println("Verifique el try de la linea 89");
                System.out.println("Error: " + e);
            } finally{
                try{
                    Conexion.getInstance().cerrarConexion();
                } catch (SQLException ex){
                    System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, alumnoImpl");
                    System.out.println("Verifique el try de la linea 108");
                    System.out.println("Error: " + ex);
                }
            }
    }

    @Override
    public void eliminar(int id) {
        try (PreparedStatement fila = getConnection().prepareStatement("DELETE FROM alumno WHERE id_alumno = ?")){
            fila.setInt(1, id);
            fila.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR eliminando datos, alumnoImpl");
            System.out.println("Verifique el try de la linea 120");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, alumnoImpl");
                System.out.println("Verifique el try de la linea 128");
                System.out.println("Error: " + ex);
            }
        }
    }

    @Override
    public void modificar(alumno entidad) {
        String sql = """
            UPDATE alumno
            SET id_programa = ?,
                id_persona = ?
            WHERE id_alumno = ?;
            """;
        try (PreparedStatement fila = getConnection().prepareStatement(sql)){
            fila.setInt(1, entidad.getPrograma_id());
            fila.setInt(2, entidad.getPersona_id());
            fila.setInt(3, entidad.getAlumno_id());
            int filas_afectadas = fila.executeUpdate();

            if(filas_afectadas == 0){
                throw new SQLException("Fallo al modificar la alumno, no se modificaron filas.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR fallo al modificar la alumno, alumnoImpl");
            System.out.println("Verifique el try de la linea 146");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, alumnoImpl");
                System.out.println("Verifique el try de la linea 160");
                System.out.println("Error: " + ex);
            }
        }
    }
}
