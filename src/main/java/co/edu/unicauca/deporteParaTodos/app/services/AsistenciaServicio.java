package co.edu.unicauca.deporteParaTodos.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.core.domain.models.AsistenciaEntidad;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound.IAsistenciaServicio;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.outbound.database.IAsistenciaRepositorio;

@Service
public class AsistenciaServicio implements IAsistenciaServicio{

    @Autowired
    private IAsistenciaRepositorio repoAsistencia;

    @Override
    public Iterable<AsistenciaEntidad> obtenerAsistencias() {
        return repoAsistencia.findAll();
    }
    
}
