package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IProgramaServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ProgramaEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IProgramaRepositorio;

@Service
public class ProgramaServicio implements IProgramaServicio{

    @Autowired
    private IProgramaRepositorio repoPrograma;

    @Override
    public Iterable<ProgramaEntidad> obtenerProgramas() {
        return repoPrograma.findAll();
    }
    
}
