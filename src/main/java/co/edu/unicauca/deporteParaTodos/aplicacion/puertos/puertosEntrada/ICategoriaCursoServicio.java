package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CategoriaCursoEntidad;

public interface ICategoriaCursoServicio {
    public Iterable<CategoriaCursoEntidad> obtenerCategoriasCurso();
    public CategoriaCursoEntidad obtenerCategoriaCursoPorId();
}