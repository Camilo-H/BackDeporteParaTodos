package co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.ProgramaEntidad;

public interface IProgramaServicio {
    public Iterable<ProgramaEntidad> obtenerProgramas();
}
