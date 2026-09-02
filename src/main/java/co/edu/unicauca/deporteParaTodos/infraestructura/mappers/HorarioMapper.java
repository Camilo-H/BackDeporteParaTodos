package co.edu.unicauca.deporteParaTodos.infraestructura.mappers;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Horario;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.HorarioDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.HorarioEntidad;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoProcesableEntidadException;

/**
 * Las fabricas originales (Horario.fabricarDeEntidad, fabricarDeDto, HorarioDto.fabricarDeModelo,
 * HorarioEntidad.fabricarDeModelo) no tenian try/catch porque todos los campos son String o int
 * primitivo, sin conversiones de tipo riesgosas. Se añade el patron try/catch por consistencia
 * con CursoMapper, CategoriaMapper y EscenarioMapper — facilita la lectura uniforme del codigo
 * y no cambia el comportamiento practico en los casos de uso normales.
 */
public class HorarioMapper {

    public static Horario toDominio(HorarioEntidad entidad) {
        try {
            Horario h = new Horario();
            h.setId(entidad.getId());
            h.setCategoria(entidad.getCategoria());
            h.setCurso(entidad.getCurso());
            h.setAnio(entidad.getAnio());
            h.setIterable(entidad.getIterable());
            h.setDia(entidad.getDia());
            h.setHoraInicio(entidad.getHoraInicio());
            h.setHoraFin(entidad.getHoraFin());
            h.setEscenario(entidad.getEscenario());
            h.setEliminado(entidad.getEliminado());
            return h;
        } catch (Exception e) {
            throw new NoProcesableEntidadException("No fue posible convertir HorarioEntidad a dominio: " + e.getMessage());
        }
    }

    public static HorarioEntidad toEntidad(Horario horario) {
        try {
            HorarioEntidad e = new HorarioEntidad();
            e.setId(horario.getId());
            e.setCategoria(horario.getCategoria());
            e.setCurso(horario.getCurso());
            e.setAnio(horario.getAnio());
            e.setIterable(horario.getIterable());
            e.setDia(horario.getDia());
            e.setHoraInicio(horario.getHoraInicio());
            e.setHoraFin(horario.getHoraFin());
            e.setEscenario(horario.getEscenario());
            e.setEliminado(horario.getEliminado());
            return e;
        } catch (Exception ex) {
            throw new NoProcesableEntidadException("No fue posible convertir Horario a entidad: " + ex.getMessage());
        }
    }

    public static HorarioDto toDto(Horario horario) {
        try {
            HorarioDto dto = new HorarioDto();
            dto.setId(horario.getId());
            dto.setCategoria(horario.getCategoria());
            dto.setCurso(horario.getCurso());
            dto.setAnio(horario.getAnio());
            dto.setIterable(horario.getIterable());
            dto.setDia(horario.getDia());
            dto.setHoraInicio(horario.getHoraInicio());
            dto.setHoraFin(horario.getHoraFin());
            dto.setEscenario(horario.getEscenario());
            // eliminado no se expone en el DTO
            return dto;
        } catch (Exception e) {
            throw new NoProcesableEntidadException("No fue posible convertir Horario a DTO: " + e.getMessage());
        }
    }

    public static Horario fromDto(HorarioDto dto) {
        try {
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
            // eliminado no viene del DTO; el gateway lo fija en 0 al insertar
            return h;
        } catch (Exception e) {
            throw new NoProcesableEntidadException("No fue posible convertir HorarioDto a dominio: " + e.getMessage());
        }
    }
}
