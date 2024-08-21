package co.edu.unicauca.deporteParaTodos.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.core.domain.models.CursoEntidad;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound.ICursoServicio;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.outbound.database.ICursoRepositorio;

@Service
public class CursoServicio implements ICursoServicio{

    @Autowired
    private ICursoRepositorio repoCurso;

    @Override
    public Iterable<CursoEntidad> obtenerCursos() {
        return repoCurso.findAll();
    }
    
}
