package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IClaseServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ClaseEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IClaseRepositorio;

@Service
public class ClaseServicio implements IClaseServicio{

    @Autowired
    private IClaseRepositorio repoClase;

    @Override
    public Iterable<ClaseEntidad> obtenerClases() {
        return repoClase.findAll();
    }
    
}
