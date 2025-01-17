package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida;

import java.util.List;
import java.util.Optional;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Alumno;

public interface IAlumnoGateway {
    public boolean existeAlumno(String alumnoId);

    public List<Alumno> obtenerAlumnos();

    public Alumno insertAlumno(Alumno datosAlumno);

    public Optional<Alumno> obtenerAlumno(String alumnoId);

    public Alumno actualizarAlumno(String alumnoId, Alumno datosAlumno);

    public Alumno eliminarAlumno(String alumnoId);
}
