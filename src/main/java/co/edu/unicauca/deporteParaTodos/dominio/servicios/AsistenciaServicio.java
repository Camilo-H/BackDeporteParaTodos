package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IAsistenciaServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.AsistenciaEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IAsistenciaRepositorio;

@Service
public class AsistenciaServicio implements IAsistenciaServicio{

    @Autowired
    private IAsistenciaRepositorio repoAsistencia;

    @Override
    public Iterable<AsistenciaEntidad> obtenerAsistencias() {
        return repoAsistencia.findAll();
    }
    
}
