package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CursoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids.CursoId;

public interface ICursoRepositorio extends CrudRepository<CursoEntidad,CursoId>{
    /**
     * Obtiene una lista de cursos a partir de una categoria
     * @param nombreCategoria categoria a la que pertenecen los cursos a buscar
     * @param eliminado bandera para la busqueda, 1 indica cursos en estado eliminado, 0 cursos no eliminados
     * @return lista de tipo CursoEntidad
     */
    List<CursoEntidad> findByCategoriaCursoAndEliminado(
            @Param("nombreCategoria") String nombreCategoria,
            @Param("eliminado") Integer eliminado);

    /**
     * Obtiene un curso
     * @param categoriaCurso categoria a la que pertenece, clave primaria
     * @param nombre nombre del curso, clave primaria
     * @return objeto de tipo CursoEntidad
     */
    CursoEntidad findByCategoriaCursoAndNombre(String categoriaCurso, String nombre);

    /***
     * A partir de un titulo de categoria retorna la cantidad de cursos asociados
     * @param CategoriaCurso titulo de la categoria
     * @return cantidad de categorias
     */
    long countByCategoriaCurso(String CategoriaCurso);
}
