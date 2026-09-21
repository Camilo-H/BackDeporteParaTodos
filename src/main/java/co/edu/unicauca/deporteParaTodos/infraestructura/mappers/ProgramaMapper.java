package co.edu.unicauca.deporteParaTodos.infraestructura.mappers;

import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoProcesableEntidadException;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Facultad;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Programa;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.FacultadEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ProgramaEntidad;

// TODO: agregar toDto(Programa) y fromDto(ProgramaDto) cuando se cree ProgramaDto y ProgramaRest
public final class ProgramaMapper {

    private ProgramaMapper() {}

    public static Programa toDominio(ProgramaEntidad entidad) {
        if (entidad == null)
            throw new NoProcesableEntidadException("ProgramaEntidad no puede ser nula");
        Programa modelo = new Programa();
        modelo.setPrg_nombre(entidad.getPrg_nombre());
        if (entidad.getFacultad() != null) {
            Facultad f = new Facultad();
            f.setNombre(entidad.getFacultad().getNombre());
            modelo.setFacultad(f);
        }
        return modelo;
    }

    public static ProgramaEntidad toEntidad(Programa modelo) {
        if (modelo == null)
            throw new NoProcesableEntidadException("Programa no puede ser nulo");
        ProgramaEntidad entidad = new ProgramaEntidad();
        entidad.setPrg_nombre(modelo.getPrg_nombre());
        if (modelo.getFacultad() != null) {
            FacultadEntidad fe = new FacultadEntidad();
            fe.setNombre(modelo.getFacultad().getNombre());
            entidad.setFacultad(fe);
        }
        return entidad;
    }
}
