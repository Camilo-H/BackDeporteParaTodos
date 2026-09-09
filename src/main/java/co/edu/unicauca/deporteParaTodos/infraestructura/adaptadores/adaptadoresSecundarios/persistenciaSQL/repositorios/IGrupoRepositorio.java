package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.GrupoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids.GrupoId;

public interface IGrupoRepositorio extends CrudRepository<GrupoEntidad, GrupoId>{
    List<GrupoEntidad> findByEliminado(Integer eliminado);
    List<GrupoEntidad> findByCategoriaAndCursoAndEliminado(String categoria, String curso, Integer eliminado);

    /**
     * Obtiene los grupos disponibles para la inscripcion
     * @return
     */
    @Query(value = "SELECT * FROM tbl_grupo " +
               "WHERE meta_eliminado = 0 AND " +
               "grp_fecha_inscrip_apertura <= CURRENT_DATE AND " +
               "(grp_fecha_inscrip_cierre IS NULL OR grp_fecha_inscrip_cierre >= CURRENT_DATE)", 
       nativeQuery = true)
    List<GrupoEntidad> obtenerGruposConInscripcionDisponibleNativo();

    @Query(value = """
    SELECT DISTINCT g.*
    FROM TBL_GRUPO g
    WHERE g.PERF_ID = :instructorId and g.META_ELIMINADO= 0
    """, nativeQuery = true)
    List<GrupoEntidad> obtenerGruposPorInstructor(@Param("instructorId") String instructorId);

    List<GrupoEntidad> findByIdInstructor(String idInstructor);

    /**
     * util para obtener el iterable identificador,
     * @param Categoria
     * @param Curso
     * @param anio
     * @return cantidad de coincidencias
     */
    int countByCategoriaAndCursoAndAnio(String Categoria, String Curso, Integer anio);
}
