package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada.IAlumnoServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertosSalida.baseDatos.IAlumnoRepositorio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.AlumnoEntidad;

@Service
public class AlumnoServicio implements IAlumnoServicio{

    @Autowired
    private IAlumnoRepositorio repoAlumno;

    @Override
    public Iterable<AlumnoEntidad> obtenerAlumnos() {
        return repoAlumno.findAll();
    }
    
}
