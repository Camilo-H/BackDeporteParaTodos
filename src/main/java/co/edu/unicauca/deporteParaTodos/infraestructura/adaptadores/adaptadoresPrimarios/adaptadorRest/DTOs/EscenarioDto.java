package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Escenario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EscenarioDto {

    private Integer id;

    @NotBlank(message = "El nombre del escenario no puede estar vacío")
    private String nombre;

    private String descripcion;

    @PositiveOrZero(message = "El número de tribunas debe ser cero o positivo")
    private Integer numTribunas;

    private Boolean disponible;

    public static EscenarioDto fabricarDeModelo(Escenario escenario) {
        try {
            EscenarioDto dto = new EscenarioDto();
            dto.setId(escenario.getId());
            dto.setNombre(escenario.getNombre());
            dto.setDescripcion(escenario.getDescripcion());
            dto.setNumTribunas(escenario.getNumTribunas());
            dto.setDisponible(escenario.isDisponible());
            return dto;
        } catch (Exception e) {
            return null;
        }
    }
}
