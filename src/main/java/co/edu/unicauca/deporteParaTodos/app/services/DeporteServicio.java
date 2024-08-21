package co.edu.unicauca.deporteParaTodos.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.core.domain.models.DeporteEntidad;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound.IDeporteServicio;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.outbound.database.IDeporteRepositorio;

@Service
public class DeporteServicio implements IDeporteServicio{
    
    @Autowired
    private IDeporteRepositorio repoDeporte;

    @Override
    public Iterable<DeporteEntidad> obtenerDeportes() {
        return repoDeporte.findAll();
    }
}
