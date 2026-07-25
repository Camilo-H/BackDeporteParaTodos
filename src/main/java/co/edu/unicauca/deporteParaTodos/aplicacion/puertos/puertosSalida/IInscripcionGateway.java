package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Inscripcion;

public interface IInscripcionGateway {

    boolean existeInscripcion(String alumnoId, String categoria, String curso, int anio, int iterable);

    boolean existeInscripcionActiva(String alumnoId, String categoria, String curso, int anio, int iterable);

    Inscripcion obtenerInscripcion(String alumnoId, String categoria, String curso, int anio, int iterable);

    Inscripcion guardarInscripcion(Inscripcion inscripcion);

    Inscripcion desvincularInscripcion(String alumnoId, String categoria, String curso, int anio, int iterable);
}
