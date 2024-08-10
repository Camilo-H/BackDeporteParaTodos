package co.edu.unicauca.proyecto.estudiantes.aplicación.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.proyecto.estudiantes.dominio.model.Alumno;
import co.edu.unicauca.proyecto.estudiantes.dominio.repository.AlumnoRepository;
import co.edu.unicauca.proyecto.estudiantes.dominio.services.AlumnoService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AlumnoServiceImpl  implements AlumnoService{

    private final AlumnoRepository alumnoRepository;

    @Autowired
    public AlumnoServiceImpl(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    @Override
    public Alumno crearAlumno(Alumno prmAlumno) {
        return alumnoRepository.save(prmAlumno);
    }

    @Override
    public Alumno obtenerAlumnoPorCodigo(String prmCodigo) {
       return alumnoRepository.findById(prmCodigo).orElseThrow(() -> new EntityNotFoundException("Alumno no encontrado"));
    }

    @Override
    public List<Alumno> listarAlumnos() {
       return alumnoRepository.findAll();
    }

    @Override
    public void eliminarAlumno(String prmCodigo) {
       alumnoRepository.deleteById(prmCodigo);
    }

    
}
