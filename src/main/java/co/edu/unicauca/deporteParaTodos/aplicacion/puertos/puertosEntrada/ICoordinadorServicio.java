package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.CoordinadorEntidad;

public interface ICoordinadorServicio{
    public Iterable<CoordinadorEntidad> obtenerCoordinadores();
}
