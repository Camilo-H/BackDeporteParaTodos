--obtiene el total de clases y tiempo consolidado en duracion total minutos
--entre 2 fechas definidas
--horas y minutos solo son informativos para verificar que la operacion es correcta
select 
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
                            and cl.cls_fecha between to_date('1-1-2025','dd-mm-yyyy') and to_date('1-1-2026','dd-mm-yyyy')
;


--para cada categoria
--obtiene el total de clases y tiempo consolidado en duracion total minutos
--entre 2 fechas definidas
--horas y minutos solo son informativos para verificar que la operacion es correcta
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
                            and cl.cls_fecha between to_date('1-1-2025','dd-mm-yyyy') and to_date('1-1-2026','dd-mm-yyyy')
group by cc.cat_titulo;

--para cada curso
--obtiene para las categorias las clases dictadas y la sumatoria de tiempo, 
--entre 2 fechas definidas
--el valor mas relevante es total minutos, pues convierte las horas en minutos y consolida un valor que tiene en cuenta ambos valores
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
                            and cl.cls_fecha between to_date('1-1-2025','dd-mm-yyyy') and to_date('1-1-2026','dd-mm-yyyy')
group by c.cat_titulo, c.cur_nombre;

--para cada grupo
--obtiene para las categorias las clases dictadas y la sumatoria de tiempo, 
--entre 2 fechas definidas
--el valor mas relevante es total minutos, pues convierte las horas en minutos y consolida un valor que tiene en cuenta ambos valores
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
                            and cl.cls_fecha between to_date('1-1-2025','dd-mm-yyyy') and to_date('1-1-2026','dd-mm-yyyy')
group by g.cat_titulo,
g.cur_nombre,
g.grp_anio,
g.grp_iterable;

--obtner alumnos de un grupo

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
            alm.meta_eliminado, 
            alm.perf_id, 
            alm.alm_codigo, 
            alm.alm_tipo, 
            perf.perf_nombre, 
            perf.perf_correo, 
            perf.perf_sexo, 
            perf.perf_tipoid, 
            perf.perf_imagen
        from tbl_alumno alm 
            inner join tbl_inscripcion ins on alm.perf_id = ins.perf_id
            inner join tbl_perfil perf on perf.perf_id = alm.perf_id
        where alm.meta_eliminado = eliminado
            and ins.cat_titulo = categoria 
            and ins.cur_nombre = curso
            and ins.grp_anio = anio
            and ins.grp_iterable = iterable;
end pro_obtener_alumnos_grupo;