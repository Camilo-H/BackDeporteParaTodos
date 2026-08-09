package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.HorarioDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.HorarioEntidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Horario {

    private Integer id;
    private String categoria;
    private String curso;
    private int anio;
    private int iterable;
    private String dia;
    private String horaInicio;
    private String horaFin;
    private String escenario;
    private Integer eliminado;

    public static Horario fabricarDeEntidad(HorarioEntidad e) {
        Horario h = new Horario();
        h.setId(e.getId());
        h.setCategoria(e.getCategoria());
        h.setCurso(e.getCurso());
        h.setAnio(e.getAnio());
        h.setIterable(e.getIterable());
        h.setDia(e.getDia());
        h.setHoraInicio(e.getHoraInicio());
        h.setHoraFin(e.getHoraFin());
        h.setEscenario(e.getEscenario());
        h.setEliminado(e.getEliminado());
        return h;
    }

    public static Horario fabricarDeDto(HorarioDto dto) {
        Horario h = new Horario();
        h.setId(dto.getId());
        h.setCategoria(dto.getCategoria());
        h.setCurso(dto.getCurso());
        h.setAnio(dto.getAnio() != null ? dto.getAnio() : 0);
        h.setIterable(dto.getIterable() != null ? dto.getIterable() : 0);
        h.setDia(dto.getDia());
        h.setHoraInicio(dto.getHoraInicio());
        h.setHoraFin(dto.getHoraFin());
        h.setEscenario(dto.getEscenario());
        return h;
    }
}
