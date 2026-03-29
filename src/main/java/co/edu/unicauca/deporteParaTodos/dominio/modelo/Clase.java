package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import java.sql.Date;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.ClaseDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ClaseEntidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Clase {

    private Integer codigo;

    private String categoria;

    private String curso;

    private Integer anio;

    private Integer iterable;

    private String idInstructor;

    private Date fecha;

    private Integer horas;

    private Integer minutos;

    private String observacion;

    private Integer eliminado;

    public static Clase fabricarDeEntidad(ClaseEntidad entidad) {
        try {
            Clase modelo = new Clase();
            modelo.setCodigo(entidad.getCodigo());
            modelo.setCategoria(entidad.getIdGrupoCategoria());
            modelo.setCurso(entidad.getIdGrupoCurso());
            modelo.setAnio(entidad.getIdGrupoAnio());
            modelo.setIterable(entidad.getIdGrupoIterable());
            modelo.setIdInstructor(entidad.getIdInstructor());
            modelo.setFecha(entidad.getFecha());
            modelo.setHoras(entidad.getHoras());
            modelo.setMinutos(entidad.getMinutos());
            modelo.setObservacion(entidad.getObservacion());
            modelo.setEliminado(entidad.getEliminado());
            return modelo;
        } catch (Exception e) {
            return null;
        }
    }

    public static Clase fabricarDeDto(ClaseDto dto) {
        try {
            Clase modelo = new Clase();
            modelo.setCodigo(dto.getCodigo());
            modelo.setCategoria(dto.getCategoria());
            modelo.setCurso(dto.getCurso());
            modelo.setAnio(dto.getAnio());
            modelo.setIterable(dto.getIterable());
            modelo.setIdInstructor(dto.getIdInstructor());
            modelo.setFecha(dto.getFecha());
            modelo.setHoras(dto.getHoras());
            modelo.setMinutos(dto.getMinutos());
            modelo.setObservacion(dto.getObservacion());
            modelo.setEliminado(dto.getEliminado());
            return modelo;
        } catch (Exception e) {
            return null;
        }
    }
}
