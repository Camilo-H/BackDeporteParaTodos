package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IAlertaServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.AlertaEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IAlertaRepositorio;

@Service
public class AlertaServicio implements IAlertaServicio{

    @Autowired
    private IAlertaRepositorio repoAlerta;

    @Override
    public Iterable<AlertaEntidad> obtenerAlertas() {
        return repoAlerta.findAll();
    }
    
}
