package co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.CoordinadorEntidad;

public interface ICoordinadorServicio{
    public Iterable<CoordinadorEntidad> obtenerCoordinadores();
}
