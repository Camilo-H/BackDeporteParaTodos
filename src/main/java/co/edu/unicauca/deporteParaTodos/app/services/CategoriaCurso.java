package co.edu.unicauca.deporteParaTodos.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.core.domain.models.CategoriaCursoEntidad;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound.ICategoriaCursoServicio;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.outbound.database.ICategoriaCursoRepositorio;

@Service
public class CategoriaCurso implements ICategoriaCursoServicio{

    @Autowired
    private ICategoriaCursoRepositorio repoCategoriaCurso;

    @Override
    public Iterable<CategoriaCursoEntidad> obtenerCategoriasCurso() {
        return repoCategoriaCurso.findAll();
    }

    @Override
    public CategoriaCursoEntidad obtenerCategoriaCursoPorId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerCategoriaCursoPorId'");
    }
    
}
