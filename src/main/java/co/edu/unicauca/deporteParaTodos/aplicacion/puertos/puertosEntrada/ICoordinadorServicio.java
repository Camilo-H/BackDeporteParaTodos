package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Coordinador;

public interface ICoordinadorServicio {

    public List<Coordinador> obtenerCoordinadores();

    public Coordinador insertarCoordinador(Coordinador datosCoordinador);

    public Coordinador obtenerCoordinador(String coorId);

    public Coordinador actualizarCoordinador(String coordId, Coordinador datosCoordinador);

    public Coordinador eliminarCoordinador(String coordId);
}
