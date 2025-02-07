package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class InscripcionDTO {
    private Timestamp fechaInscripcion;

    private Timestamp fechaDesvinculacion;

    private Timestamp fechaDesvinculado;

    private AlumnoDTO alumnoDTO;

    private GrupoDTO grupoDto;
}
