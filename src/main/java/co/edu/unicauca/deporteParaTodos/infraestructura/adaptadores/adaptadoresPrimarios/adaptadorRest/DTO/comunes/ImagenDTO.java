package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ImagenDTO {
    
    private Integer id;
    
    @NotNull(message = "{imagen.nombre.null}")
    @Pattern(regexp = "^.*\\.(jpg|jpeg|png|webp)$", message = "{imagen.nombre.tipo}")
    private String nombre;

    @NotNull(message = "{imagen.tipo.null}")
    @Pattern(regexp = "(image/jpeg|image/png|image/webp)", message = "{imagen.tipo.tipo}")
    private String tipoArchivo;
    
    @NotNull(message = "{imagen.longitud.null}")
    private Long longitud;
    
    @NotNull(message = "{imagen.datos.null}")
    @Size(min = 1, message = "{imagen.datos.size}")
    private byte[] datos;
}
