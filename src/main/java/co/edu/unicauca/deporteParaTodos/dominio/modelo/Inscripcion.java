package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import java.sql.Timestamp;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.InscripcionDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.InscripcionEntidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Inscripcion {

    private String alumnoId;
    private String categoria;
    private String curso;
    private int anio;
    private int iterable;
    private Timestamp fechaInscripcion;
    private Timestamp fechaDesvinculacion;

    public static Inscripcion fabricarDeEntidad(InscripcionEntidad entidad) {
        try {
            Inscripcion inscripcion = new Inscripcion();
            inscripcion.setAlumnoId(entidad.getAlumnoId());
            inscripcion.setCategoria(entidad.getCategoria());
            inscripcion.setCurso(entidad.getCurso());
            inscripcion.setAnio(entidad.getAnio());
            inscripcion.setIterable(entidad.getIterable());
            inscripcion.setFechaInscripcion(entidad.getFechaInscripcion());
            inscripcion.setFechaDesvinculacion(entidad.getFechaDesvinculacion());
            return inscripcion;
        } catch (Exception e) {
            return null;
        }
    }

    public static Inscripcion fabricarDeDto(InscripcionDto dto) {
        try {
            Inscripcion inscripcion = new Inscripcion();
            inscripcion.setAlumnoId(dto.getAlumnoId());
            inscripcion.setCategoria(dto.getCategoria());
            inscripcion.setCurso(dto.getCurso());
            inscripcion.setAnio(dto.getAnio());
            inscripcion.setIterable(dto.getIterable());
            inscripcion.setFechaInscripcion(dto.getFechaInscripcion());
            inscripcion.setFechaDesvinculacion(dto.getFechaDesvinculacion());
            return inscripcion;
        } catch (Exception e) {
            return null;
        }
    }
}
