package co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.FacultadEntidad;

public interface IFacultadServicio {
    public Iterable<FacultadEntidad> obtenerFacultades();
}
