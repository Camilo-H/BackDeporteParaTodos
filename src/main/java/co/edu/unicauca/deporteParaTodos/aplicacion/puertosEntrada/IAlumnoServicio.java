package co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.AlumnoEntidad;

public interface IAlumnoServicio {
    public Iterable<AlumnoEntidad> obtenerAlumnos();
}
