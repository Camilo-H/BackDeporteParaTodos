package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Inscripcion {

    private String alumnoId;
    private String categoria;
    private String curso;
    private int anio;
    private int iterable;
    private Timestamp fechaInscripcion;
    private Timestamp fechaDesvinculacion;
}
