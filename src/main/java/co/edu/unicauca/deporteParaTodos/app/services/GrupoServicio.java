package co.edu.unicauca.deporteParaTodos.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.core.domain.models.GrupoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound.IGrupoServicio;
import co.edu.unicauca.deporteParaTodos.infraestructure.ports.outbound.database.IGrupoRepositorio;

@Service
public class GrupoServicio implements IGrupoServicio{
    
    @Autowired
    private IGrupoRepositorio repoGrupo;

    @Override
    public Iterable<GrupoEntidad> obtenerGrupos() {
        return repoGrupo.findAll();
    }


}
