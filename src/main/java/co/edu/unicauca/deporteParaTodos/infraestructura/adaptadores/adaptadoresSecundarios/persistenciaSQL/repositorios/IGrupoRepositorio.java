package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CursoEntidad;
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
    FROM TEST.TBL_GRUPO g
    JOIN TEST.TBL_CLASE c ON 
        c.CAT_TITULO = g.CAT_TITULO AND
        c.CUR_NOMBRE = g.CUR_NOMBRE AND
        c.GRP_ANIO = g.GRP_ANIO AND
        c.GRP_ITERABLE = g.GRP_ITERABLE
    WHERE c.PERF_ID = :instructorId
    """, nativeQuery = true)
    List<GrupoEntidad> obtenerGruposPorInstructor(@Param("instructorId") String instructorId);

    List<GrupoEntidad> findByIdInstructor(String idInstructor);
}
