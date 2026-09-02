package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class HorarioDto {

    private Integer id;

    @NotBlank
    private String categoria;

    @NotBlank
    private String curso;

    @PositiveOrZero
    private Integer anio;

    @PositiveOrZero
    private Integer iterable;

    @NotBlank
    private String dia;

    @NotBlank
    private String horaInicio;

    @NotBlank
    private String horaFin;

    private String escenario;

}
