package co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.HorarioEntidad;

public interface IHorarioServicio {
    public Iterable<HorarioEntidad> obtenerHorarios();
}
