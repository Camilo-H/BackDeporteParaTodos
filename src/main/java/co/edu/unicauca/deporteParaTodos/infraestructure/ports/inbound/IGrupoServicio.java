package co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.GrupoEntidad;

public interface IGrupoServicio {
    public Iterable<GrupoEntidad> obtenerGrupos();
}
