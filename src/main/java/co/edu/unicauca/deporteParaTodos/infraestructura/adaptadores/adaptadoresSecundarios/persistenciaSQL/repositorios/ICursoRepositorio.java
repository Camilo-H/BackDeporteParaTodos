package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CursoEntidad;

public interface ICursoRepositorio extends CrudRepository<CursoEntidad,String>{
    List<CursoEntidad> findByCategoriaCursoAndEliminado(String nombreCategoria, Integer eliminado);
}
