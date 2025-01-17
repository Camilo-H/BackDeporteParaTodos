package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida;

import java.util.List;
import java.util.Optional;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Coordinador;

public interface ICoordinadorGateway {
    public boolean existeCoordinador(String coordId);

    public List<Coordinador> obtenerCoordinadores();

    public Coordinador insertarCoordinador(Coordinador datosCoordinador);

    public Optional<Coordinador> obtenerCoordinador(String coorId);

    public Coordinador actualizarCoordinador(String coordId, Coordinador datosCoordinador);

    public Coordinador eliminarCoordinador(String coordId);
}
