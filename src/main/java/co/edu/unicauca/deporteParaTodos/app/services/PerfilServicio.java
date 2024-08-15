package co.edu.unicauca.deporteParaTodos.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.core.domain.models.PerfilEntidad;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound.IPerfilServicio;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.outbound.database.IPerfilRepositorio;

@Service
public class PerfilServicio implements IPerfilServicio{
    
    @Autowired
    private IPerfilRepositorio repoPerfil;

    @Override
    public Iterable<PerfilEntidad> obtenerPerfiles() {
        Iterable<PerfilEntidad> perfiles = repoPerfil.findAll();
        return perfiles;
    }

}
