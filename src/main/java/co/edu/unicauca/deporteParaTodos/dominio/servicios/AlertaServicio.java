package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada.IAlertaServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertosSalida.baseDatos.IAlertaRepositorio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.AlertaEntidad;

@Service
public class AlertaServicio implements IAlertaServicio{

    @Autowired
    private IAlertaRepositorio repoAlerta;

    @Override
    public Iterable<AlertaEntidad> obtenerAlertas() {
        return repoAlerta.findAll();
    }
    
}
