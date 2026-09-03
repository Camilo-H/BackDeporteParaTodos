package co.edu.unicauca.deporteParaTodos.infraestructura.mappers;

import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoProcesableEntidadException;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Inscripcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.InscripcionDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.InscripcionEntidad;

public class InscripcionMapper {

    public static Inscripcion toDominio(InscripcionEntidad entidad) {
        try {
            Inscripcion modelo = new Inscripcion();
            modelo.setAlumnoId(entidad.getAlumnoId());
            modelo.setCategoria(entidad.getCategoria());
            modelo.setCurso(entidad.getCurso());
            modelo.setAnio(entidad.getAnio());
            modelo.setIterable(entidad.getIterable());
            modelo.setFechaInscripcion(entidad.getFechaInscripcion());
            modelo.setFechaDesvinculacion(entidad.getFechaDesvinculacion());
            return modelo;
        } catch (Exception e) {
            throw new NoProcesableEntidadException("No fue posible convertir InscripcionEntidad a dominio: " + e.getMessage());
        }
    }

    public static InscripcionEntidad toEntidad(Inscripcion inscripcion) {
        try {
            InscripcionEntidad entidad = new InscripcionEntidad();
            entidad.setAlumnoId(inscripcion.getAlumnoId());
            entidad.setCategoria(inscripcion.getCategoria());
            entidad.setCurso(inscripcion.getCurso());
            entidad.setAnio(inscripcion.getAnio());
            entidad.setIterable(inscripcion.getIterable());
            entidad.setFechaInscripcion(inscripcion.getFechaInscripcion());
            entidad.setFechaDesvinculacion(inscripcion.getFechaDesvinculacion());
            entidad.setEliminado(0);
            return entidad;
        } catch (Exception e) {
            throw new NoProcesableEntidadException("No fue posible convertir Inscripcion a entidad: " + e.getMessage());
        }
    }

    public static InscripcionDto toDto(Inscripcion inscripcion) {
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
            throw new NoProcesableEntidadException("No fue posible convertir Inscripcion a DTO: " + e.getMessage());
        }
    }

    public static Inscripcion fromDto(InscripcionDto dto) {
        try {
            Inscripcion modelo = new Inscripcion();
            modelo.setAlumnoId(dto.getAlumnoId());
            modelo.setCategoria(dto.getCategoria());
            modelo.setCurso(dto.getCurso());
            modelo.setAnio(dto.getAnio());
            modelo.setIterable(dto.getIterable());
            modelo.setFechaInscripcion(dto.getFechaInscripcion());
            modelo.setFechaDesvinculacion(dto.getFechaDesvinculacion());
            return modelo;
        } catch (Exception e) {
            throw new NoProcesableEntidadException("No fue posible convertir InscripcionDto a dominio: " + e.getMessage());
        }
    }
}
