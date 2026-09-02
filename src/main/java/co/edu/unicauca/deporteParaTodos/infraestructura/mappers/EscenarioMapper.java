package co.edu.unicauca.deporteParaTodos.infraestructura.mappers;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Escenario;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.EscenarioDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.EscenarioEntidad;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoProcesableEntidadException;

public class EscenarioMapper {

    public static Escenario toDominio(EscenarioEntidad entidad) {
        try {
            Escenario escenario = new Escenario();
            escenario.setId(entidad.getId());
            escenario.setNombre(entidad.getNombre());
            escenario.setDescripcion(entidad.getDescripcion());
            escenario.setNumTribunas(entidad.getNumTribunas());
            escenario.setDisponible(entidad.getDisponible() == 1);
            escenario.setEliminado(entidad.getEliminado());
            return escenario;
        } catch (Exception e) {
            throw new NoProcesableEntidadException("No fue posible convertir EscenarioEntidad a dominio: " + e.getMessage());
        }
    }

    public static EscenarioEntidad toEntidad(Escenario escenario) {
        try {
            EscenarioEntidad entidad = new EscenarioEntidad();
            entidad.setId(escenario.getId());
            entidad.setNombre(escenario.getNombre());
            entidad.setDescripcion(escenario.getDescripcion());
            entidad.setNumTribunas(escenario.getNumTribunas());
            entidad.setDisponible(escenario.isDisponible() ? 1 : 0);
            entidad.setEliminado(escenario.getEliminado() != null ? escenario.getEliminado() : 0);
            return entidad;
        } catch (Exception e) {
            throw new NoProcesableEntidadException("No fue posible convertir Escenario a entidad: " + e.getMessage());
        }
    }

    public static EscenarioDto toDto(Escenario escenario) {
        try {
            EscenarioDto dto = new EscenarioDto();
            dto.setId(escenario.getId());
            dto.setNombre(escenario.getNombre());
            dto.setDescripcion(escenario.getDescripcion());
            dto.setNumTribunas(escenario.getNumTribunas());
            dto.setDisponible(escenario.isDisponible());
            return dto;
        } catch (Exception e) {
            throw new NoProcesableEntidadException("No fue posible convertir Escenario a DTO: " + e.getMessage());
        }
    }

    public static Escenario fromDto(EscenarioDto dto) {
        try {
            Escenario escenario = new Escenario();
            escenario.setId(dto.getId());
            escenario.setNombre(dto.getNombre());
            escenario.setDescripcion(dto.getDescripcion());
            escenario.setNumTribunas(dto.getNumTribunas() != null ? dto.getNumTribunas() : 0);
            escenario.setDisponible(dto.getDisponible() != null ? dto.getDisponible() : true);
            escenario.setEliminado(0);
            return escenario;
        } catch (Exception e) {
            throw new NoProcesableEntidadException("No fue posible convertir EscenarioDto a dominio: " + e.getMessage());
        }
    }
}
