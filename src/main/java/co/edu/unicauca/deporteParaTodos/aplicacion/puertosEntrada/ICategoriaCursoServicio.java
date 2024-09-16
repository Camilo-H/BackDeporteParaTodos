package co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.CategoriaCursoEntidad;

public interface ICategoriaCursoServicio {
    public Iterable<CategoriaCursoEntidad> obtenerCategoriasCurso();
    public CategoriaCursoEntidad obtenerCategoriaCursoPorId();
}