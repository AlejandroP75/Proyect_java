package com.campuslands.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class salon {
    private int salon_id;    
    private int salon_capacidad;
    private int salon_piso;
    private int edificio_id;
}
