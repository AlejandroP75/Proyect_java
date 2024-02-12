package com.campuslands.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class edificio {
    private int edificio_id;
    private String edificio_nombre;
    private int edificio_pisos;

}
