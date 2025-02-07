package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes;

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
public class DeporteDTO {

    @NotNull(message = "{deporte.nombre.null}") //no existe el campo, la informacion
    @NotEmpty(message = "{deporte.nombre.empty}") //no se ha digitado nada
    @NotBlank(message = "{deporte.nombre.blank}") //no se ha digitado nada o solo son espacios en blanco
    private String nombre;

}
