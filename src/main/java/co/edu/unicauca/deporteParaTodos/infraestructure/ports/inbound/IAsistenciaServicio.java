package co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.AsistenciaEntidad;

public interface IAsistenciaServicio {
    public Iterable<AsistenciaEntidad> obtenerAsistencias();
}
