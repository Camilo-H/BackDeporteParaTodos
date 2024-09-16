package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada.ICategoriaCursoServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertosSalida.baseDatos.ICategoriaCursoRepositorio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.CategoriaCursoEntidad;

@Service
public class CategoriaCursoServicio implements ICategoriaCursoServicio{

    @Autowired
    private ICategoriaCursoRepositorio repoCategoriaCurso;

    @Override
    public Iterable<CategoriaCursoEntidad> obtenerCategoriasCurso() {
        return repoCategoriaCurso.findAll();
    }

    @Override
    public CategoriaCursoEntidad obtenerCategoriaCursoPorId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerCategoriaCursoPorId'");
    }
    
}
