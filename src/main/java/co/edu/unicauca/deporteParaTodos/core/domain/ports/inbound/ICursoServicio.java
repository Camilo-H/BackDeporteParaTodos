package co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.CursoEntidad;

public interface ICursoServicio {
    public Iterable<CursoEntidad> obtenerCursos();
}
