package com.campuslands.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class profesor {
    private int profesor_id;
    private int departamento_id;
    private int persona_id;

}
