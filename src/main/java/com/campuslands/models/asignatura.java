package com.campuslands.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class asignatura {
    private int asignatura_id;
    private String asignatura_nombre;
    private int asignatura_numero_creditos;
    private int profesor_id;
    private int curso_id;
    private int periodo_id;
}
