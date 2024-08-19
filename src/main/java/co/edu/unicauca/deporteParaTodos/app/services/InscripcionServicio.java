package co.edu.unicauca.deporteParaTodos.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.core.domain.models.InscripcionEntidad;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound.IInscripcionServicio;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.outbound.database.IInscripcionRepositorio;

@Service
public class InscripcionServicio implements IInscripcionServicio{

    @Autowired
    IInscripcionRepositorio repoInscripcion;

    @Override
    public Iterable<InscripcionEntidad> obtenerInscripciones() {
        return repoInscripcion.findAll();
    }
    
}
