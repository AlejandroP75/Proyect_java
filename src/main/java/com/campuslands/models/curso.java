package com.campuslands.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class curso {
    private int curso_id;
    private String curso_nombre;
    private String curso_guia_catedra;
        
}
