package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ProgramaEntidad;

public interface IProgramaServicio {
    public Iterable<ProgramaEntidad> obtenerProgramas();
}
