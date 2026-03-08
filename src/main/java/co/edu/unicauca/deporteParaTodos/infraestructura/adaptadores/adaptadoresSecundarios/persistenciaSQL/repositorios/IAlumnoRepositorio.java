package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.AlumnoDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.AlumnoEntidad;

public interface IAlumnoRepositorio extends CrudRepository<AlumnoEntidad,String>{
    @Procedure(procedureName = "pro_obtener_alumnos_grupo")
    List<AlumnoDto> obtenerAlumnosGrupo(
        @Param("categoria") String categoria, 
        @Param("curso") String curso, 
        @Param("anio") double anio, 
        @Param("iterable") double iterable, 
        @Param("eliminado") double eliminado);

     @Query(value = """
        SELECT 
            alm.meta_eliminado AS eliminadoestado,
            alm.perf_id AS id,
            alm.alm_codigo AS codigo,
            alm.alm_tipo AS tipo,
            perf.perf_nombre AS nombre,
            perf.perf_correo AS correo,
            perf.perf_sexo AS sexo,
            perf.perf_tipoid AS tipoid,
            perf.perf_imagen AS imagen
        FROM tbl_alumno alm
        INNER JOIN tbl_inscripcion ins ON alm.perf_id = ins.perf_id
        INNER JOIN tbl_perfil perf ON perf.perf_id = alm.perf_id
        WHERE alm.meta_eliminado = :eliminado
          AND ins.cat_titulo = :categoria
          AND ins.cur_nombre = :curso
          AND ins.grp_anio = :anio
          AND ins.grp_iterable = :iterable
        """, nativeQuery = true)
    List<Object[]> buscarAlumnosGrupoRaw(
        @Param("categoria") String categoria,
        @Param("curso") String curso,
        @Param("anio") Integer anio,
        @Param("iterable") Integer iterable,
        @Param("eliminado") Integer eliminado
    );
}
