package co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.ClaseEntidad;

public interface IClaseServicio {
    public Iterable<ClaseEntidad> obtenerClases();
}
