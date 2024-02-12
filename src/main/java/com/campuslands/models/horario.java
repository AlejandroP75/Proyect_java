package com.campuslands.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class horario {
    private int horario_id;
    private String horario_dia;
    private String horario_hora_inicio;
    private String horario_hora_fin;
    private int salon_id;
    private int asignatura_id;
}
