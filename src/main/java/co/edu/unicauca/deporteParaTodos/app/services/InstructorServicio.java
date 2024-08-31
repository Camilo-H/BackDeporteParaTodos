package co.edu.unicauca.deporteParaTodos.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.core.domain.models.InstructorEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound.IInstructorServicio;
import co.edu.unicauca.deporteParaTodos.infraestructure.ports.outbound.database.IInstructorRepositorio;

@Service
public class InstructorServicio implements IInstructorServicio{


    @Autowired
    IInstructorRepositorio repoInstructor;

    @Override
    public Iterable<InstructorEntidad> obtenerInstructores() {

        return repoInstructor.findAll();
    }
    
}
