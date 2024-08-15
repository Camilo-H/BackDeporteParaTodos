package co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.FacultadEntidad;

public interface IFacultadServicio {
    public Iterable<FacultadEntidad> obtenerFacultades();
}
