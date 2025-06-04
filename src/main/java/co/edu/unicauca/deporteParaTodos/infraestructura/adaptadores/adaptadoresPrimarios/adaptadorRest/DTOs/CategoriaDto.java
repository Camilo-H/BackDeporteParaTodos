package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs;


import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;
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
public class CategoriaDto {

    @NotBlank(message = "{categoria.titulo.blank}")
    private String titulo;

    @NotBlank(message = "{categoria.descripcion.blank}")
    private String descripcion;
    
    @NotNull(message = "{categoria.imagenid.null}")
    private Integer imagenId;

    public static CategoriaDto fabricarDeModelo(Categoria modelo){
        try{
            CategoriaDto dto = new CategoriaDto();
            dto.setTitulo(modelo.getTitulo());
            dto.setDescripcion(modelo.getDescripcion());
            dto.setImagenId(modelo.getImagen());
            return dto;
        }catch(Exception e){
            return null;
        }
    }
}
