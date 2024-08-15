package co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.ProgramaEntidad;

public interface IProgramaServicio {
    public Iterable<ProgramaEntidad> obtenerProgramas();
}
