package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs;

import java.sql.Date;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Clase;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClaseDto {

    private Integer codigo;

    @NotBlank(message = "{clase.categoria.blank}")
    @JsonAlias("idGrupoCategoria")
    @JsonProperty("idGrupoCategoria")
    private String categoria;

    @NotBlank(message = "{clase.curso.blank}")
    @JsonAlias("idGrupoCurso")
    @JsonProperty("idGrupoCurso")
    private String curso;

    @NotNull(message = "{clase.anio.null}")
    @JsonAlias("idGrupoAnio")
    @JsonProperty("idGrupoAnio")
    private Integer anio;

    @NotNull(message = "{clase.iterable.null}")
    @JsonAlias("idGrupoIterable")
    @JsonProperty("idGrupoIterable")
    private Integer iterable;

    @NotBlank(message = "{clase.instructor.blank}")
    private String idInstructor;

    @NotNull(message = "{clase.fecha.null}")
    private Date fecha;

    @NotNull(message = "{clase.horas.null}")
    private Integer horas;

    @NotNull(message = "{clase.minutos.null}")
    private Integer minutos;

    private String observacion;

    private Integer eliminado;

    public static ClaseDto fabricarDeModelo(Clase modelo) {
        try {
            ClaseDto dto = new ClaseDto();
            dto.setCodigo(modelo.getCodigo());
            dto.setCategoria(modelo.getCategoria());
            dto.setCurso(modelo.getCurso());
            dto.setAnio(modelo.getAnio());
            dto.setIterable(modelo.getIterable());
            dto.setIdInstructor(modelo.getIdInstructor());
            dto.setFecha(modelo.getFecha());
            dto.setHoras(modelo.getHoras());
            dto.setMinutos(modelo.getMinutos());
            dto.setObservacion(modelo.getObservacion());
            dto.setEliminado(modelo.getEliminado());
            return dto;
        } catch (Exception e) {
            return null;
        }
    }
}
