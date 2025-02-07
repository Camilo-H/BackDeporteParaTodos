package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.peticion;

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
public class CursoInDTO {
    @NotNull(message = "{curso.nombre.null}") // no existe el campo, la informacion
    @NotEmpty(message = "{curso.nombre.empty}") // no se ha digitado nada
    @NotBlank(message = "{curso.nombre.blank}") // no se ha digitado nada o solo son espacios en blanco
    private String nombre;

    @NotNull(message = "{curso.deporte.null}")
    @NotEmpty(message = "{curso.deporte.empty}")
    @NotBlank(message = "{curso.deporte.blank}")
    private String deporte;

    @NotNull(message = "{curso.categoriaCurso.null}")
    @NotEmpty(message = "{curso.categoriaCurso.empty}")
    @NotBlank(message = "{curso.categoriaCurso.blank}")
    private String categoriaCurso;

    @NotNull(message = "{curso.descripcion.null}")
    @NotEmpty(message = "{curso.descripcion.empty}")
    @NotBlank(message = "{curso.descripcion.blank}")
    private String descripcion;

    private MultipartFile imagen;
}
