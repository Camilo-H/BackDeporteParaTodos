package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Horario;
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

    public static HorarioDto fabricarDeModelo(Horario h) {
        HorarioDto dto = new HorarioDto();
        dto.setId(h.getId());
        dto.setCategoria(h.getCategoria());
        dto.setCurso(h.getCurso());
        dto.setAnio(h.getAnio());
        dto.setIterable(h.getIterable());
        dto.setDia(h.getDia());
        dto.setHoraInicio(h.getHoraInicio());
        dto.setHoraFin(h.getHoraFin());
        dto.setEscenario(h.getEscenario());
        return dto;
    }
}
