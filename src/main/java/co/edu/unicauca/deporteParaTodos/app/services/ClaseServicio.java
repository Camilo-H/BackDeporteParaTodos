package co.edu.unicauca.deporteParaTodos.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.core.domain.models.ClaseEntidad;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound.IClaseServicio;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.outbound.database.IClaseRepositorio;

@Service
public class ClaseServicio implements IClaseServicio{

    @Autowired
    private IClaseRepositorio repoClase;

    @Override
    public Iterable<ClaseEntidad> obtenerClases() {
        return repoClase.findAll();
    }
    
}
