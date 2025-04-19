--procedimiento almacenado para consultar alumnos por grupo
create or replace procedure pro_obtener_alumnos_grupo(
    categoria IN VARCHAR2,
    curso in varchar2,
    anio in number,
    iterable in number,
    eliminado in number,
    alumnos out SYS_REFCURSOR
)
as 
begin 
    open alumnos for
        select 
            alm.meta_eliminado as eliminadoestado, 
            alm.perf_id as id, 
            alm.alm_codigo as codigo, 
            alm.alm_tipo as tipo, 
            perf.perf_nombre as nombre, 
            perf.perf_correo as correo, 
            perf.perf_sexo as sexo, 
            perf.perf_tipoid as tipoid, 
            perf.perf_imagen as imagen
        from tbl_alumno alm 
            inner join tbl_inscripcion ins on alm.perf_id = ins.perf_id
            inner join tbl_perfil perf on perf.perf_id = alm.perf_id
        where alm.meta_eliminado = eliminado
            and ins.cat_titulo = categoria 
            and ins.cur_nombre = curso
            and ins.grp_anio = anio
            and ins.grp_iterable = iterable;
end pro_obtener_alumnos_grupo;