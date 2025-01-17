package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CategoriaDTO {
    
    @NotNull(message = "{categoria.titulo.null}") //no existe el campo, la informacion
    @NotEmpty(message = "{categoria.titulo.empty}") //no se ha digitado nada
    @NotBlank(message = "{categoria.titulo.blank}") //no se ha digitado nada o solo son espacios en blanco
    private String titulo;

    @NotNull(message = "{categoria.descripcion.null}")
    @NotEmpty(message = "{categoria.descripcion.empty}") 
    @NotBlank(message = "{categoria.descripcion.blank}") 
    private String descripcion;

    private String rutaImagen;

    //private MultipartFile imagen;
    private ImagenDTO imagen;

    //@JsonIgnore
    private List<CursoDTO> cursos;
}
