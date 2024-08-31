package co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.DeporteEntidad;

public interface IDeporteServicio {
    public Iterable<DeporteEntidad> obtenerDeportes();
}
