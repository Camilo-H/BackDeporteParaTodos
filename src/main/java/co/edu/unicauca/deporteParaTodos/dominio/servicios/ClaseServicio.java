package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IClaseServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.baseDatos.IClaseRepositorio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.ClaseEntidad;

@Service
public class ClaseServicio implements IClaseServicio{

    @Autowired
    private IClaseRepositorio repoClase;

    @Override
    public Iterable<ClaseEntidad> obtenerClases() {
        return repoClase.findAll();
    }
    
}
