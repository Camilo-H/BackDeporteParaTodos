package co.edu.unicauca.deporteParaTodos.dominio.modelo;

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

public class Clase {

    private Integer id;

    private Instructor instructor;

    private Date fecha;

    private Timestamp horaInicio;

    private Timestamp horaFin;

    private String observacion;
}
