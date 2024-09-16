package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.InstructorEntidad;

public interface IInstructorServicio {
    public Iterable<InstructorEntidad> obtenerInstructores();
}
