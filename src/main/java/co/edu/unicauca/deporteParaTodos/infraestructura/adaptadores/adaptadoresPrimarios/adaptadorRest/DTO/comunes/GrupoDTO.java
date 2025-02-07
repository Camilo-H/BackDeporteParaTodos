package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class GrupoDTO {

    private String nombre;

    private int anio;

    private int iterable;

    @JsonIgnore
    private CursoDTO curso;

    private ImagenDTO imagen;

    private int cupos;

    private String estado;

    private Date fechaCreacion;

    private Date fechaFinalizacion;
    //@JsonIgnore
    private List<HorarioDTO> horarios;
    //@JsonIgnore
    private List<InscripcionDTO> inscripciones;
}
