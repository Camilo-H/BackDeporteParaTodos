package co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.CoordinadorEntidad;

public interface ICoordinadorServicio{
    public Iterable<CoordinadorEntidad> obtenerCoordinadores();
}
