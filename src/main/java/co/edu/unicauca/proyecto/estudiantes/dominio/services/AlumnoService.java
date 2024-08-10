package co.edu.unicauca.proyecto.estudiantes.dominio.services;


import java.util.List;

import co.edu.unicauca.proyecto.estudiantes.dominio.model.Alumno;

public interface AlumnoService {
    Alumno crearAlumno( Alumno prmAlumno);
    Alumno obtenerAlumnoPorCodigo(String prmCodigo);
    List<Alumno> listarAlumnos();
    void eliminarAlumno(String prmCodigo);
    
}
