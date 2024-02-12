package com.campuslands.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.campuslands.models.persona;
import com.campuslands.utils.Conexion;

public class personaImpl implements repository<persona>{
    private Connection getConnection() throws SQLException {
        return Conexion.getInstance().conectar();
    }   

    private persona crearPersona(ResultSet campo) throws SQLException{
        persona p = new persona();
        p.setPersona_id(campo.getInt("id_persona"));
        p.setPersona_tipo_documento(campo.getString("tipo_documento"));
        p.setPersona_numero_documento(campo.getInt("numero_documento"));
        p.setPersona_nombres(campo.getString("nombres"));
        p.setPersona_apellidos(campo.getString("apellidos"));
        p.setPersona_telefono(campo.getInt("telefono"));
        p.setPersona_fecha_nacimiento(campo.getDate("fecha_nacimiento").toLocalDate());
        p.setPersona_genero(campo.getString("sexo"));
        p.setCiudad_id(campo.getInt("id_ciudad"));
        p.setDireccion_id(campo.getInt("id_direccion"));

        return p;
    }

    @Override
    public List<persona> listar() {
        List<persona> listarPersonas = new ArrayList<>();
        try (Statement fila = getConnection().createStatement();
            ResultSet resultado = fila.executeQuery("SELECT * FROM persona")
        ) {
            while (resultado.next()) {
                listarPersonas.add(crearPersona(resultado));
            }
        } catch (SQLException e) {
            System.out.println("ERROR en listar, personaImpl");
            System.out.println("Verifique el try de la linea 38");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, personaImpl");
                System.out.println("Verifique el try de la linea 49");
                System.out.println("Error: " + ex);
            }
        }
        return listarPersonas;
    }

    @Override
    public persona porCodigo(int id) {
        persona p = null;
        String sql = """
            SELECT * FROM persona
            WHERE id_persona = ?
                """;
        try (PreparedStatement modificacion = getConnection().prepareStatement(sql)){
            modificacion.setInt(1 , id);// Con esta modificacion ya tenemos la consulta completa
            ResultSet fila = modificacion.executeQuery();

            if(fila.next()){
                p = crearPersona(fila);
            }
        } catch (SQLException e) {
            System.out.println("ERROR en la busqueda del porCodigo, personaImpl");
            System.out.println("Verifique el try de la linea 67");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, personaImpl");
                System.out.println("Verifique el try de la linea 79");
                System.out.println("Error: " + ex);
            }
        }
        return p;
    }

    @Override
    public void guardar(persona entidad) {
        String sql = """
                INSERT INTO persona(tipo_documento, numero_documento, nombres, apellidos, telefono, fecha_nacimiento, sexo, id_ciudad, id_direccion) VALUES
                (?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;
            try (PreparedStatement fila = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

                fila.setString(1, entidad.getPersona_tipo_documento());
                fila.setInt(2, entidad.getPersona_numero_documento());
                fila.setString(3, entidad.getPersona_nombres());
                fila.setString(4, entidad.getPersona_apellidos());
                fila.setInt(5, entidad.getPersona_telefono());
                fila.setDate(6, java.sql.Date.valueOf(entidad.getPersona_fecha_nacimiento()));
                fila.setString(7, entidad.getPersona_genero());
                fila.setInt(8, entidad.getCiudad_id());
                fila.setInt(9, entidad.getDireccion_id());

                int filas_afectadas = fila.executeUpdate();

                if(filas_afectadas == 0){
                    throw new SQLException("Fallo al guardar la persona, no se modificaron filas.");
                }
                try (ResultSet generatedKeys = fila.getGeneratedKeys()){
                    if (generatedKeys.next()) {
                        entidad.setPersona_id(generatedKeys.getInt(1));
                    }else{
                        throw new SQLException("Fallo al programa la persona, no se obtuvo el ID generado.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("ERROR fallo al guardar la persona, personaImpl");
                System.out.println("Verifique el try de la linea 96");
                System.out.println("Error: " + e);
            } finally{
                try{
                    Conexion.getInstance().cerrarConexion();
                } catch (SQLException ex){
                    System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, personaImpl");
                    System.out.println("Verifique el try de la linea 125");
                    System.out.println("Error: " + ex);
                }
            }
    }

    @Override
    public void eliminar(int id) {
        try (PreparedStatement fila = getConnection().prepareStatement("DELETE FROM persona WHERE id_persona = ?")){
            fila.setInt(1, id);
            fila.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR eliminando datos, personaImpl");
            System.out.println("Verifique el try de la linea 137");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, personaImpl");
                System.out.println("Verifique el try de la linea 145");
                System.out.println("Error: " + ex);
            }
        }
    }

    @Override
    public void modificar(persona entidad) {
        String sql = """
                UPDATE persona
                SET tipo_documento = ?, 
                    numero_documento = ?, 
                    nombres = ?, 
                    apellidos = ?, 
                    telefono = ?, 
                    fecha_nacimiento = ?, 
                    sexo = ?, 
                    id_ciudad = ?, 
                    id_direccion = ?
                WHERE id_persona = ?;
                """;
        try (PreparedStatement fila = getConnection().prepareStatement(sql)){
            fila.setString(1, entidad.getPersona_tipo_documento());
            fila.setInt(2, entidad.getPersona_numero_documento());
            fila.setString(3, entidad.getPersona_nombres());
            fila.setString(4, entidad.getPersona_apellidos());
            fila.setInt(5, entidad.getPersona_telefono());
            fila.setDate(6, java.sql.Date.valueOf(entidad.getPersona_fecha_nacimiento()));
            fila.setString(7, entidad.getPersona_genero());
            fila.setInt(8, entidad.getCiudad_id());
            fila.setInt(9, entidad.getDireccion_id());
            fila.setInt(10, entidad.getPersona_id());
            int filas_afectadas = fila.executeUpdate();

            if(filas_afectadas == 0){
                throw new SQLException("Fallo al modificar la persona, no se modificaron filas.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR fallo al modificar la persona, personaImpl");
            System.out.println("Verifique el try de la linea 170");
            System.out.println("Error: " + e);
        } finally{
            try{
                Conexion.getInstance().cerrarConexion();
            } catch (SQLException ex){
                System.out.println("ERROR cerrando la conexion de la busqueda de porCodigo, personaImpl");
                System.out.println("Verifique el try de la linea 191");
                System.out.println("Error: " + ex);
            }
        }
    }
}
