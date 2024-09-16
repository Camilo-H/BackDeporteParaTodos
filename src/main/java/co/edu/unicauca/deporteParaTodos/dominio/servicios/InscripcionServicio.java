package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IInscripcionServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.baseDatos.IInscripcionRepositorio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.InscripcionEntidad;

@Service
public class InscripcionServicio implements IInscripcionServicio{

    @Autowired
    IInscripcionRepositorio repoInscripcion;

    @Override
    public Iterable<InscripcionEntidad> obtenerInscripciones() {
        return repoInscripcion.findAll();
    }
    
}
