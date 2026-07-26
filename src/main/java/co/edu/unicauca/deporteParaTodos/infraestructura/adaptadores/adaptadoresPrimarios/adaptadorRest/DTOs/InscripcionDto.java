package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs;

import java.sql.Timestamp;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Inscripcion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InscripcionDto {

    private String alumnoId;
    private String categoria;
    private String curso;
    private int anio;
    private int iterable;
    private Timestamp fechaInscripcion;
    private Timestamp fechaDesvinculacion;

    public static InscripcionDto fabricarDeModelo(Inscripcion inscripcion) {
        try {
            InscripcionDto dto = new InscripcionDto();
            dto.setAlumnoId(inscripcion.getAlumnoId());
            dto.setCategoria(inscripcion.getCategoria());
            dto.setCurso(inscripcion.getCurso());
            dto.setAnio(inscripcion.getAnio());
            dto.setIterable(inscripcion.getIterable());
            dto.setFechaInscripcion(inscripcion.getFechaInscripcion());
            dto.setFechaDesvinculacion(inscripcion.getFechaDesvinculacion());
            return dto;
        } catch (Exception e) {
            return null;
        }
    }
}
