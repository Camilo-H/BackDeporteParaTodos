package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.peticion;

import java.sql.Date;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GrupoInDTO {
    @NotNull(message = "{grupo.nombre.null}") // no existe el campo, la informacion
    @NotEmpty(message = "{grupo.nombre.empty}") // no se ha digitado nada
    @NotBlank(message = "{grupo.nombre.blank}") // no se ha digitado nada o solo son espacios en blanco
    private String nombre;

    @NotNull(message = "{grupo.anio.null}")
    @NotEmpty(message = "{grupo.anio.empty}")
    @NotBlank(message = "{grupo.anio.blank}")
    private int anio;

    @NotNull(message = "{grupo.iterable.null}")
    @NotEmpty(message = "{grupo.iterable.empty}")
    @NotBlank(message = "{grupo.iterable.blank}")
    private int iterable;

    @NotNull(message = "{grupo.curso.null}")
    @NotEmpty(message = "{grupo.curso.empty}")
    @NotBlank(message = "{grupo.curso.blank}")
    private String curso;

    private MultipartFile imagen;

    @NotNull(message = "{grupo.cupos.null}")
    @NotEmpty(message = "{grupo.cupos.empty}")
    @NotBlank(message = "{grupo.cupos.blank}")
    private int cupos;

    @NotNull(message = "{grupo.estado.null}")
    @NotEmpty(message = "{grupo.estado.empty}")
    @NotBlank(message = "{grupo.estado.blank}")
    private String estado;

    @NotNull(message = "{grupo.fechaCreacion.null}")
    @NotEmpty(message = "{grupo.fechaCreacion.empty}")
    @NotBlank(message = "{grupo.fechaCreacion.blank}")
    private Date fechaCreacion;

    private Date fechaFinalizacion;
}
