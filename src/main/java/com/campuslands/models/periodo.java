package com.campuslands.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class periodo {
    private int periodo_id;
    private int periodo_anio;
    private int periodo_semestre;
    private String periodo_nombre;

}
