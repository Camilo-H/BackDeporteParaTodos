package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICoordinadorServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CoordinadorEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICoordinadorRepositorio;

@Service
public class CoordinadorServico implements ICoordinadorServicio{

    @Autowired
    private ICoordinadorRepositorio repoCoordinador;


    @Override
    public Iterable<CoordinadorEntidad> obtenerCoordinadores() {
        return repoCoordinador.findAll();
    }
    
}
