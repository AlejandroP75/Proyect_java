package com.campuslands.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class programa {
    private int programa_id;
    private String programa_nombre;
    private String programa_nivel;
}
