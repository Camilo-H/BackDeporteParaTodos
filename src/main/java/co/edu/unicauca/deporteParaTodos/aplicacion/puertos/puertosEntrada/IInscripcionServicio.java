package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Disponibilidad;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Inscripcion;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.InscripcionEnEspera;

import java.util.List;

public interface IInscripcionServicio {

    Inscripcion inscribir(Inscripcion datos);

    boolean validarInscripcion(String alumnoId, String categoria, String curso, int anio, int iterable);

    Inscripcion desvincularInscripcion(String alumnoId, String categoria, String curso, int anio, int iterable);

    Inscripcion promoverManualmente(String alumnoId, String categoria, String curso, int anio, int iterable);

    Disponibilidad obtenerDisponibilidad(String categoria, String curso, int anio, int iterable);

    List<InscripcionEnEspera> listarEnEspera(String categoria, String curso, int anio, int iterable);
}
