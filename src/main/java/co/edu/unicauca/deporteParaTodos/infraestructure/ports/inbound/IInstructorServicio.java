package co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.InstructorEntidad;

public interface IInstructorServicio {
    public Iterable<InstructorEntidad> obtenerInstructores();
}
