package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs;

import java.sql.Date;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Grupo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GrupoDto {
    private String categoria;

    private String curso;

    private int anio;

    private int iterable;

    private Integer imagenGrupo;

    private Integer cupos;

    private String idInstructor;

    private Date fechaCreacion;

    private Date fechaFinalizacion;

    public static GrupoDto fabricarDeModelo(Grupo grupo){
        try{
            GrupoDto dto = new GrupoDto();
            dto.setCategoria(grupo.getCategoria());
            dto.setCurso(grupo.getCurso());
            dto.setAnio(grupo.getAnio());
            dto.setIterable(grupo.getIterable());
            dto.setCupos(grupo.getCupos());
            dto.setFechaCreacion(grupo.getFechaCreacion());
            dto.setFechaFinalizacion(grupo.getFechaFinalizacion());
            dto.setIdInstructor(grupo.getIdInstructor());
            dto.setImagenGrupo(grupo.getImagenGrupo());
            return dto;
        }catch(Exception e){
            return null;
        }
    }
}
