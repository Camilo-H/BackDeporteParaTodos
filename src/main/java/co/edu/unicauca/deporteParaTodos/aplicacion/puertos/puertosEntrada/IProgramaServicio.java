package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.ProgramaEntidad;

public interface IProgramaServicio {
    public Iterable<ProgramaEntidad> obtenerProgramas();
}
