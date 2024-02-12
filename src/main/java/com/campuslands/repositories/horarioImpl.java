package com.campuslands.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.campuslands.models.horario;
import com.campuslands.utils.Conexion;

public class horarioImpl implements repository<horario>{
    private Connection getConnection() throws SQLException {
        return Conexion.getInstance().conectar();
    }   

    private horario crearHorario(ResultSet campo) throws SQLException{
        horario t = new horario();
        t.setHorario_id(campo.getInt("id_horario"));
        t.setHorario_dia(campo.getDate("dia").toLocalDate());
        t.setHorario_hora_inicio(campo.getTimestamp("hora_inicio").toLocalDateTime());
        t.setHorario_hora_fin(campo.getTimestamp("hora_fin").toLocalDateTime());
        t.setAsignatura_id(campo.getInt("id_salon"));
        t.setSalon_id(campo.getInt("id_asignatura"));

        return t;
    }

    @Override
    public List<horario> listar() {
        List<horario> listarHorarios = new ArrayList<>();
        try (Statement fila = getConnection().createStatement();
            ResultSet resultado = fila.executeQuery("SELECT * FROM horario")
        ) {
            while (resultado.next()) {
                listarHorarios.add(crearHorario(resultado));
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, horarioImpl");
            System.out.println("Verifique el try de la linea 34");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, horarioImpl");
                System.out.println("Verifique el try de la linea 45");
                System.out.println("Error: " + ex);
            }
        }
        return listarHorarios;
    }

    @Override
    public horario porCodigo(int id) {
        horario t = null;
        String sql = """
            SELECT * FROM horario
            WHERE id_horario = ?
                """;
        try (PreparedStatement modificacion = getConnection().prepareStatement(sql)){
            modificacion.setInt(1 , id);// Con esta modificacion ya tenemos la consulta completa
            ResultSet fila = modificacion.executeQuery();

            if(fila.next()){
                t = crearHorario(fila);
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, horarioImpl");
            System.out.println("Verifique el try de la linea 63");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, horarioImpl");
                System.out.println("Verifique el try de la linea 75");
                System.out.println("Error: " + ex);
            }
        }
        return t;
    }

    @Override
    public void guardar(horario entidad) {
        String sql = """
                INSERT INTO horario(dia, hora_inicio, hora_fin, id_salon, id_asignatura) VALUES
                (?, ?, ?, ?, ?);
                """;
            try (PreparedStatement fila = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                fila.setDate(1, java.sql.Date.valueOf(entidad.getHorario_dia()));
                fila.setTimestamp(2, java.sql.Timestamp.valueOf(entidad.getHorario_hora_inicio()));
                fila.setTimestamp(3, java.sql.Timestamp.valueOf(entidad.getHorario_hora_fin()));
                fila.setInt(4, entidad.getSalon_id());
                fila.setInt(5, entidad.getAsignatura_id());

                int filas_afectadas = fila.executeUpdate();
                if(filas_afectadas == 0){
                    throw new SQLException("Fallo al guardar el horario, no se modificaron filas.");
                }
                try (ResultSet generatedKeys = fila.getGeneratedKeys()){
                    if (generatedKeys.next()) {
                        entidad.setHorario_id(generatedKeys.getInt(1));
                    }else{
                        throw new SQLException("Fallo al horario la horario, no se obtuvo el ID generado.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("ERROR fallo al guardar la horario, horarioImpl");
                System.out.println("Verifique el try de la linea 92");
                System.out.println("Error: " + e);
            } finally{
                try{
                    Conexion.getInstance().cerrarConexion();
                } catch (SQLException ex){
                    System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, horarioImpl");
                    System.out.println("Verifique el try de la linea 115");
                    System.out.println("Error: " + ex);
                }
            }
    }

    @Override
    public void eliminar(int id) {
        try (PreparedStatement fila = getConnection().prepareStatement("DELETE FROM horario WHERE id_horario = ?")){
            fila.setInt(1, id);
            fila.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR eliminando datos, horarioImpl");
            System.out.println("Verifique el try de la linea 127");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, horarioImpl");
                System.out.println("Verifique el try de la linea 135");
                System.out.println("Error: " + ex);
            }
        }
    }

    @Override
    public void modificar(horario entidad) {
        String sql = """
                UPDATE horario
                SET dia = ?,
                    hora_inicio = ?,
                    hora_fin = ?,
                    id_salon = ?,
                    id_asignatura = ?
                WHERE id_horario = ?;
                """;
        try (PreparedStatement fila = getConnection().prepareStatement(sql)){
            fila.setDate(1, java.sql.Date.valueOf(entidad.getHorario_dia()));
            fila.setTimestamp(2, java.sql.Timestamp.valueOf(entidad.getHorario_hora_inicio()));
            fila.setTimestamp(3, java.sql.Timestamp.valueOf(entidad.getHorario_hora_fin()));
            fila.setInt(4, entidad.getSalon_id());
            fila.setInt(5, entidad.getAsignatura_id());
            fila.setInt(6, entidad.getHorario_id());
            int filas_afectadas = fila.executeUpdate();

            if(filas_afectadas == 0){
                throw new SQLException("Fallo al modificar la horario, no se modificaron filas.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR fallo al modificar la horario, horarioImpl");
            System.out.println("Verifique el try de la linea 156");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, horarioImpl");
                System.out.println("Verifique el try de la linea 173");
                System.out.println("Error: " + ex);
            }
        }
    }
}
