package co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.ProgramaEntidad;

public interface IProgramaServicio {
    public Iterable<ProgramaEntidad> obtenerProgramas();
}
