package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IHorarioServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.HorarioEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IHorarioRepositorio;

@Service
public class HorarioServicio implements IHorarioServicio{
    
    @Autowired
    private IHorarioRepositorio repoHorario;

    @Override
    public Iterable<HorarioEntidad> obtenerHorarios() {

        return repoHorario.findAll();
    }


}
