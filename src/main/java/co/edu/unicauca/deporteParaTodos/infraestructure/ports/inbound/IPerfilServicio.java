package co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.PerfilEntidad;

public interface IPerfilServicio {
    public Iterable<PerfilEntidad> obtenerPerfiles();
}
