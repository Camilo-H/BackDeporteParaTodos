package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import java.time.LocalDate;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.GrupoDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.GrupoEntidad;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Grupo {

    private String categoria;

    private String curso;

    private int anio;

    private int iterable;

    private Integer imagenGrupo;

    private Integer cupos;

    private String idInstructor;

    private LocalDate fechaCreacion;

    private LocalDate fechaFinalizacion;

    private LocalDate fechaInscripcionApertura;

    private LocalDate fechaIncripcionCierre;

    private int periodo;

    public static Grupo fabricarDeEntidad(GrupoEntidad entidad){
        try{
            Grupo grupo = new Grupo();
            grupo.setCategoria(entidad.getCategoria());
            grupo.setCurso(entidad.getCurso());
            grupo.setAnio(entidad.getAnio());
            grupo.setCupos(entidad.getCupos());
            grupo.setFechaCreacion(entidad.getFechaCreacion());
            grupo.setFechaFinalizacion(entidad.getFechaFinalizacion());
            grupo.setFechaInscripcionApertura(entidad.getFechaInscripcionApertura());
            grupo.setFechaIncripcionCierre(entidad.getFechaIncripcionCierre());
            grupo.setIdInstructor(entidad.getIdInstructor());
            grupo.setImagenGrupo(entidad.getImagenGrupo());
            grupo.setIterable(entidad.getIterable());
            grupo.setPeriodo(entidad.getPeriodo());
            return grupo;
        }catch(Exception e){
            return null;
        }
    }
    public static Grupo fabricarDeDto(GrupoDto dto){
        try{
            Grupo grupo = new Grupo(
                dto.getCategoria(),
                dto.getCurso(),
                dto.getAnio(),
                dto.getIterable(),
                dto.getImagenGrupo(),
                dto.getCupos(),
                dto.getIdInstructor(),
                dto.getFechaCreacion(),
                dto.getFechaFinalizacion(),
                dto.getFechaInscripcionApertura(),
                dto.getFechaIncripcionCierre(),
                dto.getPeriodo() != null ? dto.getPeriodo() : 0);
            return grupo;
        }catch(Exception e){
            return null;
        }
    }
}
