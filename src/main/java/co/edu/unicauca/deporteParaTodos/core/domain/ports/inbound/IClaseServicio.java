package co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.ClaseEntidad;

public interface IClaseServicio {
    public Iterable<ClaseEntidad> obtenerClases();
}
