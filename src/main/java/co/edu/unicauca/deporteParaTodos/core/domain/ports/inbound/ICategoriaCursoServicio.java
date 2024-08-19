package co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.CategoriaCursoEntidad;

public interface ICategoriaCursoServicio {
    public Iterable<CategoriaCursoEntidad> obtenerCategoriasCurso();
    public CategoriaCursoEntidad obtenerCategoriaCursoPorId();
}