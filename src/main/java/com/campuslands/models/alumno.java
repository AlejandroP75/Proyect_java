package com.campuslands.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class alumno {
    private int alumno_id;
    private int programa_id;
    private int persona_id;
}
