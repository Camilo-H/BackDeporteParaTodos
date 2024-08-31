package co.edu.unicauca.deporteParaTodos.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.core.domain.models.HorarioEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound.IHorarioServicio;
import co.edu.unicauca.deporteParaTodos.infraestructure.ports.outbound.database.IHorarioRepositorio;

@Service
public class HorarioServicio implements IHorarioServicio{
    
    @Autowired
    private IHorarioRepositorio repoHorario;

    @Override
    public Iterable<HorarioEntidad> obtenerHorarios() {

        return repoHorario.findAll();
    }


}
