package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InscripcionEnEspera {
    private String alumnoId;
    private String nombre;
    private String correo;
    private Timestamp fechaInscripcion;
}
