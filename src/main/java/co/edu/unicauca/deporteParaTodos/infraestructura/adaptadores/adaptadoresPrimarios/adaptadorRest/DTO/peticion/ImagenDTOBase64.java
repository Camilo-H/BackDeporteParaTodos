package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.peticion;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ImagenDTOBase64 {
    private Integer id;
    
    @NotNull(message = "{imagen.nombre.null}")
    @Pattern(regexp = "^.*\\.(jpg|jpeg|png|webp)$", message = "{imagen.nombre.tipo}")
    private String nombre;

    @NotNull(message = "{imagen.tipo.null}")
    @Pattern(regexp = "(image/jpeg|image/png|image/webp)", message = "{imagen.tipo.tipo}")
    private String tipoArchivo;
    
    @NotNull(message = "{imagen.longitud.null}")
    private Long longitud;
    
    //para obtener cadena codificada en base 64
    @NotNull(message = "{imagen.datos.null}")
    private String datos;
}
