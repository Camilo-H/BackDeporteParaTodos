package co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.HorarioEntidad;

public interface IHorarioServicio {
    public Iterable<HorarioEntidad> obtenerHorarios();
}
