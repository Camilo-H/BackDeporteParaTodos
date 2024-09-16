package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICursoServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.baseDatos.ICursoRepositorio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.CursoEntidad;

@Service
public class CursoServicio implements ICursoServicio{

    @Autowired
    private ICursoRepositorio repoCurso;

    @Override
    public Iterable<CursoEntidad> obtenerCursos() {
        return repoCurso.findAll();
    }
    
}
