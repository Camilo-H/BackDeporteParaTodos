package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada.IAsistenciaServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertosSalida.baseDatos.IAsistenciaRepositorio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.AsistenciaEntidad;

@Service
public class AsistenciaServicio implements IAsistenciaServicio{

    @Autowired
    private IAsistenciaRepositorio repoAsistencia;

    @Override
    public Iterable<AsistenciaEntidad> obtenerAsistencias() {
        return repoAsistencia.findAll();
    }
    
}
