package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes;


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

public class CursoDTO {
    
    @NotNull(message = "{curso.nombre.null}") //no existe el campo, la informacion
    @NotEmpty(message = "{curso.nombre.empty}") //no se ha digitado nada
    @NotBlank(message = "{curso.nombre.blank}") //no se ha digitado nada o solo son espacios en blanco
    private String nombre;

    @NotNull(message = "{curso.nombreDeporte.null}") 
    @NotEmpty(message = "{curso.nombreDeporte.empty}")
    @NotBlank(message = "{curso.nombreDeporte.blank}")
    private String nombreDeporte;

    @NotNull(message = "{curso.tituloCategoria.null}")
    @NotEmpty(message = "{curso.tituloCategoria.empty}")
    @NotBlank(message = "{curso.tituloCategoria.blank}")
    private String tituloCategoria;

    private MultipartFile imagen;

    @NotNull(message = "{curso.descripcion.null}")
    @NotEmpty(message = "{curso.descripcion.empty}")
    @NotBlank(message = "{curso.descripcion.blank}")
    private String descripcion;
}
