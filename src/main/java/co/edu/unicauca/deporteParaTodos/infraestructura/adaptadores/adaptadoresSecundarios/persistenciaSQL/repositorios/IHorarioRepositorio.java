package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.HorarioEntidad;

public interface IHorarioRepositorio extends CrudRepository<HorarioEntidad, Integer>{

    /**
     * Obtiene la lista de horarios de un grupo que esta identificado con los parametros
     * @param categoria titulo de la categoria
     * @param curso nombre del curso
     * @param anio anio de grupo
     * @param iterable iterable del grupo
     * @return Lista de tipo HorarioEntidad
     */
    List<HorarioEntidad> findByCategoriaAndCursoAndAnioAndIterableAndEliminado(String categoria, String curso, int anio, int iterable, int eliminado);
}
