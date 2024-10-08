package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IInstructorServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.InstructorEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IInstructorRepositorio;

@Service
public class InstructorServicio implements IInstructorServicio{


    @Autowired
    IInstructorRepositorio repoInstructor;

    @Override
    public Iterable<InstructorEntidad> obtenerInstructores() {

        return repoInstructor.findAll();
    }
    
}
