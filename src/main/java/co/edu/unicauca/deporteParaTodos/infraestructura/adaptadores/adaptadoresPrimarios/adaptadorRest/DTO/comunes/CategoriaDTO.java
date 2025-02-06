package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes;

import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CategoriaDTO {
    
    private String titulo;

    private String descripcion;

    //private MultipartFile imagen;
    private ImagenDTO imagen;

    //@JsonIgnore
    private List<CursoDTO> cursos;
}
