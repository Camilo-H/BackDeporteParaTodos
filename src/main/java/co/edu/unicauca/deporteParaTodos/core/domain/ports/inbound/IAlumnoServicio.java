package co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.AlumnoEntidad;

public interface IAlumnoServicio {
    public Iterable<AlumnoEntidad> obtenerAlumnos();
}
