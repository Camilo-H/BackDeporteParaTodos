package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CoordinadorEntidad;

public interface ICoordinadorServicio{
    public Iterable<CoordinadorEntidad> obtenerCoordinadores();
}
