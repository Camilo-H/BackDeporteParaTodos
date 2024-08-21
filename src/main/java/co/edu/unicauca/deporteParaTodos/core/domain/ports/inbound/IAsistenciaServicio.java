package co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.AsistenciaEntidad;

public interface IAsistenciaServicio {
    public Iterable<AsistenciaEntidad> obtenerAsistencias();
}
