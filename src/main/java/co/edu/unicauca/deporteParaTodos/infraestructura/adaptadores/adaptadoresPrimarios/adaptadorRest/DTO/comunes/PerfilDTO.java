package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerfilDTO {
    
    private String perf_id;

    private String perf_nombre;

    private String perf_correo;

    private ImagenDTO perf_imagen;

    private String perf_tipo;

    private String perf_Sexo;
}
