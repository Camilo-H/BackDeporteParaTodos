package co.edu.unicauca.deporteParaTodos.infraestructura.mappers;

import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoProcesableEntidadException;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Deporte;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.DeporteDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.DeporteEntidad;

public final class DeporteMapper {

    private DeporteMapper() {}

    public static Deporte toDominio(DeporteEntidad entidad) {
        if (entidad == null)
            throw new NoProcesableEntidadException("DeporteEntidad no puede ser nula");
        Deporte modelo = new Deporte();
        modelo.setNombre(entidad.getNombre());
        return modelo;
    }

    public static DeporteEntidad toEntidad(Deporte modelo) {
        if (modelo == null)
            throw new NoProcesableEntidadException("Deporte modelo no puede ser nulo");
        DeporteEntidad entidad = new DeporteEntidad();
        entidad.setNombre(modelo.getNombre());
        return entidad;
    }

    public static DeporteDto toDto(Deporte modelo) {
        if (modelo == null)
            throw new NoProcesableEntidadException("Deporte modelo no puede ser nulo");
        DeporteDto dto = new DeporteDto();
        dto.setNombre(modelo.getNombre());
        return dto;
    }

    public static Deporte fromDto(DeporteDto dto) {
        if (dto == null)
            throw new NoProcesableEntidadException("DeporteDto no puede ser nulo");
        Deporte modelo = new Deporte();
        modelo.setNombre(dto.getNombre());
        return modelo;
    }
}
