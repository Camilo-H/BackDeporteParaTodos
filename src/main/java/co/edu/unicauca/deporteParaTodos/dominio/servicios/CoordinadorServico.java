package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICoordinadorServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.baseDatos.ICoordinadorRepositorio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.CoordinadorEntidad;

@Service
public class CoordinadorServico implements ICoordinadorServicio{

    @Autowired
    private ICoordinadorRepositorio repoCoordinador;


    @Override
    public Iterable<CoordinadorEntidad> obtenerCoordinadores() {
        return repoCoordinador.findAll();
    }
    
}
