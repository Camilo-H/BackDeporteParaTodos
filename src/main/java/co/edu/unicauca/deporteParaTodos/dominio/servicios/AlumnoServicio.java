package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IAlumnoServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.AlumnoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IAlumnoRepositorio;

@Service
public class AlumnoServicio implements IAlumnoServicio{

    @Autowired
    private IAlumnoRepositorio repoAlumno;

    @Override
    public Iterable<AlumnoEntidad> obtenerAlumnos() {
        return repoAlumno.findAll();
    }
    
}
