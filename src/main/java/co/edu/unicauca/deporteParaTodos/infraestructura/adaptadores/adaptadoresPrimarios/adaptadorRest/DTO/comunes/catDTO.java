package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class catDTO {
    @NotNull(message = "{categoria.titulo.null}") //no existe el campo, la informacion
    @NotEmpty(message = "{categoria.titulo.empty}") //no se ha digitado nada
    @NotBlank(message = "{categoria.titulo.blank}") //no se ha digitado nada o solo son espacios en blanco
    private String titulo;

    @NotNull(message = "{categoria.descripcion.null}")
    @NotEmpty(message = "{categoria.descripcion.empty}") 
    @NotBlank(message = "{categoria.descripcion.blank}") 
    private String descripcion;

    private MultipartFile imagen;

    private List<CursoDTO> cursos;
}
