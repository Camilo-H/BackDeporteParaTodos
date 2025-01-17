package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inscripcion {

    private Timestamp fechaInscripcion;

    private Timestamp fechaDesvinculacion;

    private Timestamp fechaDesvinculado;

    private Alumno alumno;

    private Grupo grupo;
}
