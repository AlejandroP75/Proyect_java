package com.campuslands.views;

import com.campuslands.services.*;

public class conexion_fabrica {
    @SuppressWarnings("rawtypes")
    public Services getConexion(String opciones){
        return switch (opciones.toLowerCase()) {
            case "ciudades" -> new ServiceCiudad();
            case "alumnos" -> new ServiceAlumno();
            case "asignaturas" -> new ServiceAsignatura();
            case "cursos" -> new ServiceCurso();
            case "departamentos" -> new ServiceDepartamento();
            case "direcciones" -> new ServiceDireccion();
            case "edificios" -> new ServiceEdificio();
            case "horarios" -> new ServiceHorario();
            case "matriculas" -> new ServiceMatricula();
            case "periodos" -> new ServicePeriodo();
            case "personas" -> new ServicePersona();
            case "profesores" -> new ServiceProfesor();
            case "programas" -> new ServicePrograma();
            case "salones" -> new ServiceSalon();
            case "tarifas" -> new ServiceTarifa();
            default -> new conexion_vacia();
        };        
    }
}
