package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.PerfilEntidad;
import java.util.Optional;


public interface IPerfilRepositorio extends CrudRepository<PerfilEntidad,String>{
    //List<GrupoEntidad> findByCategoriaAndCursoAndEliminado(String categoria, String curso, Integer eliminado);
    Optional<PerfilEntidad> findByPerfcorreo(String perfcorreo);
}
