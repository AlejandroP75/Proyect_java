package com.campuslands.models;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class persona {
    private int persona_id;
    private String persona_tipo_documento;
    private int persona_numero_documento;
    private String persona_nombres;
    private String persona_apellidos;
    private int persona_telefono;
    private LocalDate persona_fecha_nacimiento;
    private String persona_genero;
    private int ciudad_id;
    private int direccion_id;
}
