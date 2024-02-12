package com.campuslands.models;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class horario {
    private int horario_id;
    private LocalDate horario_dia;
    private LocalDateTime horario_hora_inicio;
    private LocalDateTime horario_hora_fin;
    private int salon_id;
    private int asignatura_id;
}
