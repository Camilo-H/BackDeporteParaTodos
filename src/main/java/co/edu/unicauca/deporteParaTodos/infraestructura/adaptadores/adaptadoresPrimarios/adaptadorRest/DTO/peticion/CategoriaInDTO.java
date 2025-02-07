package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.peticion;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaInDTO {

    @NotNull(message = "{categoria.titulo.null}") //no existe el campo, la informacion
    @NotEmpty(message = "{categoria.titulo.empty}") //no se ha digitado nada
    @NotBlank(message = "{categoria.titulo.blank}")
    private String titulo;

    @NotNull(message = "{categoria.descripcion.null}")
    @NotEmpty(message = "{categoria.descripcion.empty}") 
    @NotBlank(message = "{categoria.descripcion.blank}") 
    private String descripcion;

    private MultipartFile imagen;
}
