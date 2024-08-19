package co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.InstructorEntidad;

public interface IInstructorServicio {
    public Iterable<InstructorEntidad> obtenerInstructores();
}
