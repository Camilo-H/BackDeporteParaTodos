package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Inscripcion;
import jakarta.servlet.UnavailableException;

public interface IInscripcionGateway {

    public boolean existeInscripcion(Timestamp fecha) throws UnavailableException, Exception;

    public List<Inscripcion> obtenerInscripciones();

    public Optional<Inscripcion> obteneInscripcion(Timestamp fecha);

    public Inscripcion insertarInscripcion(Inscripcion datosInscripcion);

    public Inscripcion eliminarInscripcion(Timestamp fecha);
}
