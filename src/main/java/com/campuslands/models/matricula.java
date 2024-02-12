package com.campuslands.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class matricula {
    private int matricula_id;
    private int alumno_id;
    private int asignatura_id;
    private int periodo_id;
}
