package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes;

import java.sql.Date;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ClaseDTO {

    private Integer id;

    private InstructorDTO instructor;

    private Date fecha;

    private Timestamp horaInicio;

    private Timestamp horaFin;

    private String observacion;
}
