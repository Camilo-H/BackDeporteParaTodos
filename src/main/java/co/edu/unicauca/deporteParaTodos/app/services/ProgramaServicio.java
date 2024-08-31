package co.edu.unicauca.deporteParaTodos.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.core.domain.models.ProgramaEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound.IProgramaServicio;
import co.edu.unicauca.deporteParaTodos.infraestructure.ports.outbound.database.IProgramaRepositorio;

@Service
public class ProgramaServicio implements IProgramaServicio{

    @Autowired
    private IProgramaRepositorio repoPrograma;

    @Override
    public Iterable<ProgramaEntidad> obtenerProgramas() {
        return repoPrograma.findAll();
    }
    
}
