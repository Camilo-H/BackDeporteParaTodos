package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class HorarioDTO {
    
    private int id;
    @JsonIgnore
    private GrupoDTO grupo;

    private String dia;

    private String horaInicio;

    private String horaFin;

    private String escenario;
}
