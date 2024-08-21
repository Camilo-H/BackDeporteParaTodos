package co.edu.unicauca.deporteParaTodos.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.core.domain.models.AlertaEntidad;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound.IAlertaServicio;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.outbound.database.IAlertaRepositorio;

@Service
public class AlertaServicio implements IAlertaServicio{

    @Autowired
    private IAlertaRepositorio repoAlerta;

    @Override
    public Iterable<AlertaEntidad> obtenerAlertas() {
        return repoAlerta.findAll();
    }
    
}
