package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada.IProgramaServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertosSalida.baseDatos.IProgramaRepositorio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.ProgramaEntidad;

@Service
public class ProgramaServicio implements IProgramaServicio{

    @Autowired
    private IProgramaRepositorio repoPrograma;

    @Override
    public Iterable<ProgramaEntidad> obtenerProgramas() {
        return repoPrograma.findAll();
    }
    
}
