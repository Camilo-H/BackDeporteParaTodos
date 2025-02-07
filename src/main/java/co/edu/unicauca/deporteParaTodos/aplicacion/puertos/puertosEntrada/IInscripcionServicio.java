package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.sql.Timestamp;
import java.util.List;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Inscripcion;

public interface IInscripcionServicio {
    public List<Inscripcion> obtenerInscripciones();

    public Inscripcion obteneInscripcion(Timestamp fecha);

    public Inscripcion insertarInscripcion(Inscripcion datosInscripcion);

    public Inscripcion eliminarInscripcion(Timestamp fecha);
}
