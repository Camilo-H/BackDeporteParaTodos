package co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.InstructorEntidad;

public interface IInstructorServicio {
    public Iterable<InstructorEntidad> obtenerInstructores();
}
