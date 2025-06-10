package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.AsistenciaEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids.AsistenciaId;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


public interface IAsistenciaRepositorio extends CrudRepository<AsistenciaEntidad,AsistenciaId>{
    List<AsistenciaEntidad> findByClaseCodigo(Integer claseCodigo);
    Boolean existsByClaseCodigoAndPerfilId(Integer claseCodigo, String perfilId);

    /**
     * Retorna las estadisticas entre dos fechas para las categorias existentes que posean cursos
     * @param fechaInicio
     * @param fechaFin
     * @return retorna un objeto que posee CAT_TITULO, CLASES, HORAS, MINUTOS, DURACION_TOTAL_MINUTOS
     */
    @Query(value = """
        select cc.cat_titulo, 
            coalesce( count(cl.cat_titulo), 0) as clases,
            coalesce (sum(cl.cls_duracion_horas),0) as horas,
            coalesce (sum(cl.cls_duracion_minutos), 0) as minutos,
            (coalesce( sum(cl.cls_duracion_horas),0)*60)
                +coalesce(sum(cl.cls_duracion_minutos),0) as duracion_total_minutos
            from tbl_categoria_curso cc
            right join tbl_curso c on c.cat_titulo=cc.cat_titulo
            right join tbl_grupo g on c.cat_titulo = g.cat_titulo and c.cur_nombre = g.cur_nombre
            left join tbl_clase cl on g.cat_titulo = cl.cat_titulo
                                        and g.cur_nombre = cl.cur_nombre
                                        and g.grp_anio = cl.grp_anio
                                        and g.grp_iterable = cl.grp_iterable
                                        and cl.cls_fecha between :fechaInicio and :fechaFin
            where (:categoria is null or c.cat_titulo = :categoria)
            group by cc.cat_titulo
            """,nativeQuery = true)
    List<Object[]>  estadisticasCategorias(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin, String categoria);

    /***
     * Obtiene las estadisticas de todos los cursos entre 2 fechas
     * @param fechaInicio
     * @param fechaFin
     * @return lista de objetos con los campos: CAT_TITULO, CUR_NOMBRE, CLASES, HORAS, MINUTOS, DURACION_TOTAL_MINUTOS
     */
    @Query(value = """
        select 
            c.cat_titulo,
            c.cur_nombre,
            coalesce( count(cl.cat_titulo), 0) as clases,
            coalesce (sum(cl.cls_duracion_horas),0) as horas,
            coalesce (sum(cl.cls_duracion_minutos), 0) as minutos,
            (coalesce( sum(cl.cls_duracion_horas),0)*60)
                +coalesce(sum(cl.cls_duracion_minutos),0) as duracion_total_minutos
            from tbl_curso c
            right join tbl_grupo g on c.cat_titulo = g.cat_titulo and c.cur_nombre = g.cur_nombre
            left join tbl_clase cl on g.cat_titulo = cl.cat_titulo
                                        and g.cur_nombre = cl.cur_nombre
                                        and g.grp_anio = cl.grp_anio
                                        and g.grp_iterable = cl.grp_iterable
                                        and cl.cls_fecha between :fechaInicio and :fechaFin
            where
                (:categoria is null or c.cat_titulo = :categoria) and
                (:curso is null or c.cur_nombre = :curso)
            group by c.cat_titulo, c.cur_nombre
            """,nativeQuery = true)
    List<Object[]>  estadisticasCursos(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin, String categoria, String curso);


    /**
     * Retorna las estadisticas de los grupos entre 2 fechas
     * @param fechaInicio requerido
     * @param fechaFin requerido
     * @param categoria nullable
     * @param curso nullable
     * @param anio nullable
     * @param iterable nullable
     * @return retorna un objeto que posee los campos CAT_TITULO, CUR_NOMBRE, GRP_ANIO, GRP_ITERABLE, CLASES, HORAS, MINUTOS, DURACION_TOTAL_MINUTOS
     */
    @Query(value = """
        select 
            g.cat_titulo,
            g.cur_nombre,
            g.grp_anio,
            g.grp_iterable,
            coalesce( count(cl.cat_titulo), 0) as clases,
            coalesce (sum(cl.cls_duracion_horas),0) as horas,
            coalesce (sum(cl.cls_duracion_minutos), 0) as minutos,
            (coalesce( sum(cl.cls_duracion_horas),0)*60)
                +coalesce(sum(cl.cls_duracion_minutos),0) as duracion_total_minutos
            from tbl_grupo g
            left join tbl_clase cl on g.cat_titulo = cl.cat_titulo
                                        and g.cur_nombre = cl.cur_nombre
                                        and g.grp_anio = cl.grp_anio
                                        and g.grp_iterable = cl.grp_iterable
                                        and cl.cls_fecha between :fechaInicio and :fechaFin
            where 
                (:categoria is null or g.cat_titulo = :categoria) and 
                (:curso is null or g.cur_nombre = :curso) and 
                (:anio is null or g.grp_anio = :anio) and 
                (:iterable is null or g.grp_iterable = :iterable)
            group by g.cat_titulo,
            g.cur_nombre,
            g.grp_anio,
            g.grp_iterable
            """,nativeQuery = true)
    List<Object[]>  estadisticasGrupos(
        @Param("fechaInicio") LocalDate fechaInicio, 
        @Param("fechaFin") LocalDate fechaFin, 
        @Param("categoria") String categoria, 
        @Param("curso") String curso, 
        @Param("anio") Integer anio, 
        @Param("iterable") Integer iterable);

    /**
     * Retorna el numero de asistencias y sumatoria de horas de un alumno entre 2 fechas
     * @param fechaInicio
     * @param fechaFin
     * @param alumno identificador del alumno
     * @return
     */
    @Query(value = """
        select 
            asis.perf_id,
            coalesce( count(*), 0) as clases,
            coalesce (sum(cl.cls_duracion_horas),0) as horas,
            coalesce (sum(cl.cls_duracion_minutos), 0) as minutos,
            (coalesce( sum(cl.cls_duracion_horas),0)*60)
                +coalesce(sum(cl.cls_duracion_minutos),0) as duracion_total_minutos
        from 
            tbl_asistencia asis
            inner join tbl_clase cl on asis.cls_codigo=cl.cls_codigo and
            cl.cls_fecha BETWEEN :fechaInicio
                            AND :fechaFin
        where 
            (asis.perf_id = :alumno or :alumno is null)
        group by asis.perf_id""", 
        nativeQuery = true)
    List<Object[]> estadisticasAlumno(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin, @Param("alumno") String alumno);
}
