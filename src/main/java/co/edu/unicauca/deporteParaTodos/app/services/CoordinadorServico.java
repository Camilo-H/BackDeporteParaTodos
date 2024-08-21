package co.edu.unicauca.deporteParaTodos.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.core.domain.models.CoordinadorEntidad;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound.ICoordinadorServicio;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.outbound.database.ICoordinadorRepositorio;

@Service
public class CoordinadorServico implements ICoordinadorServicio{

    @Autowired
    private ICoordinadorRepositorio repoCoordinador;


    @Override
    public Iterable<CoordinadorEntidad> obtenerCoordinadores() {
        return repoCoordinador.findAll();
    }
    
}
