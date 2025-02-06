package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CursoDTO {

    private String nombre;

    private DeporteDTO deporte;

    @JsonIgnore
    private CategoriaDTO categoriaCurso;

    private String descripcion;

    private ImagenDTO imagen;
    //@JsonIgnore
    private List<GrupoDTO> grupos;
}
