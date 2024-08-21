package co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.DeporteEntidad;

public interface IDeporteServicio {
    public Iterable<DeporteEntidad> obtenerDeportes();
}
