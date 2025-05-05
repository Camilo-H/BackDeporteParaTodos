package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

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

}
