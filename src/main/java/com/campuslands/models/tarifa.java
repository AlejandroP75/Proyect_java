package com.campuslands.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class tarifa {
    private int tarifa_id;
    private double tarifa_valor_credito;
    private int programa_id;
    private int periodo_id;
}
