package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.InscripcionEnEspera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InscripcionEnEsperaDto {
    private String alumnoId;
    private String nombre;
    private String correo;
    private Timestamp fechaInscripcion;

    public static InscripcionEnEsperaDto fabricarDeModelo(InscripcionEnEspera modelo) {
        return new InscripcionEnEsperaDto(
                modelo.getAlumnoId(),
                modelo.getNombre(),
                modelo.getCorreo(),
                modelo.getFechaInscripcion());
    }
}
