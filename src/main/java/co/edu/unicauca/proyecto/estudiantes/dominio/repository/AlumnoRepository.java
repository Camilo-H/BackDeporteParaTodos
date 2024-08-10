package co.edu.unicauca.proyecto.estudiantes.dominio.repository;

import java.util.Optional;
import co.edu.unicauca.proyecto.estudiantes.dominio.model.Alumno;
import java.util.List;


public interface AlumnoRepository {
    Alumno save (Alumno prmAlumno);
    Optional <Alumno> findById(String codigo);
    List<Alumno> findAll();
    void deleteById(String codigo);
} 
