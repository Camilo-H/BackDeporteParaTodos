package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Inscripcion;

public interface IInscripcionServicio {

    Inscripcion inscribir(Inscripcion datos);

    boolean validarInscripcion(String alumnoId, String categoria, String curso, int anio, int iterable);

    Inscripcion desvincularInscripcion(String alumnoId, String categoria, String curso, int anio, int iterable);
}
