package co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.InscripcionEntidad;

public interface IInscripcionServicio {
    public Iterable<InscripcionEntidad> obtenerInscripciones();
}
