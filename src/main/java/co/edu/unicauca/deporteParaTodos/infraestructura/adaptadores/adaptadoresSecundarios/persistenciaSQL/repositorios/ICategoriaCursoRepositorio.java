package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CategoriaCursoEntidad;
import jakarta.transaction.Transactional;

import java.util.List;


public interface ICategoriaCursoRepositorio extends CrudRepository<CategoriaCursoEntidad, String>{
    /**
     * Obtiene las categorias que se encuentren marcadas segun la bandera eliminado, a nivel de base de datos el campo META_ELIMINADO
     * @param eliminado 1 representa registro eliminado, 0 registro disponible
     * @return lista de cartegorias encontradas
     */
    List<CategoriaCursoEntidad> findByEliminado(int eliminado);

    /**
     * Marca como eliminada una categoria, eliminar no representa quitar el registro de la base de datos
     * @param titulo Identificador de la categoria
     * @return Numero de filas afectadas, el exito de la operacion debe ser con retorno 1
     */
    @Modifying
    @Transactional
    @Query("UPDATE CategoriaCursoEntidad c SET c.eliminado = 1 WHERE c.titulo = :titulo")
    int marcarComoEliminado(
        @Param("titulo") String titulo
        );
}
