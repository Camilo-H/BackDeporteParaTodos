package co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.AlumnoEntidad;

public interface IAlumnoServicio {
    public Iterable<AlumnoEntidad> obtenerAlumnos();
}
