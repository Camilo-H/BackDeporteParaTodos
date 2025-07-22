package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import java.sql.Date;
import java.util.List;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.GrupoEntidad;
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

    private Date fechaCreacion;

    private Date fechaFinalizacion;

    public static Grupo fabricarDeEntidad(GrupoEntidad entidad){
        try{
            Grupo grupo = new Grupo();
            grupo.setCategoria(entidad.getCategoria());
            grupo.setCurso(entidad.getCurso());
            grupo.setAnio(entidad.getAnio());
            grupo.setCupos(entidad.getCupos());
            grupo.setFechaCreacion(entidad.getFechaCreacion());
            grupo.setFechaFinalizacion(entidad.getFechaFinalizacion());
            grupo.setIdInstructor(entidad.getIdInstructor());
            grupo.setImagenGrupo(entidad.getImagenGrupo());
            grupo.setIterable(entidad.getIterable());
            return grupo;
        }catch(Exception e){
            return null;
        }
    }
}
