package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.InscripcionEntidad;

public interface IInscripcionServicio {
    public Iterable<InscripcionEntidad> obtenerInscripciones();
}
