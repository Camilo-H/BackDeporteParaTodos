package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Horario {
    
    private int id;

    private Grupo grupo;

    private String dia;

    private String horaInicio;

    private String horaFin;

    private String escenario;
}
