package co.edu.unicauca.deporteParaTodos.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.core.domain.models.AlumnoEntidad;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound.IAlumnoServicio;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.outbound.database.IAlumnoRepositorio;

@Service
public class AlumnoServicio implements IAlumnoServicio{

    @Autowired
    private IAlumnoRepositorio repoAlumno;

    @Override
    public Iterable<AlumnoEntidad> obtenerAlumnos() {
        return repoAlumno.findAll();
    }
    
}
