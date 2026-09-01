package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDto {

    @NotBlank(message = "{categoria.titulo.blank}")
    private String titulo;

    @NotBlank(message = "{categoria.descripcion.blank}")
    private String descripcion;
    
    @NotNull(message = "{categoria.imagenid.null}")
    private Integer imagenId;

}
