package co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.InscripcionEntidad;

public interface IInscripcionServicio {
    public Iterable<InscripcionEntidad> obtenerInscripciones();
}
