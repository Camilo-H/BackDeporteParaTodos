package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ClaseEntidad;
import java.util.List;


public interface IClaseRepositorio extends CrudRepository<ClaseEntidad,Integer>{
    
    List<ClaseEntidad> findByIdGrupoCategoriaAndIdGrupoCursoAndIdGrupoAnioAndIdGrupoIterableAndEliminado(
        String idGrupoCategoria,
        String idGrupoCurso,
        Integer idGrupoAnio,
        Integer idGrupoIterable,
        Integer eliminado
        );
}
