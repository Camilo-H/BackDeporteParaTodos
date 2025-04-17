package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CursoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.GrupoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids.GrupoId;

public interface IGrupoRepositorio extends CrudRepository<GrupoEntidad, GrupoId>{
    //List<CursoEntidad> findByCategoriaCursoAndEliminado(String nombreCategoria, Integer eliminado);
    List<GrupoEntidad> findByEliminado(Integer eliminado);
    List<GrupoEntidad> findByCategoriaAndCursoAndEliminado(String categoria, String curso, Integer eliminado);
}
