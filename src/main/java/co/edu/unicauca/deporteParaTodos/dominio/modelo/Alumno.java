package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Alumno {
    private Integer eliminadoestado;
    private String alm_codigo;
    private String tipoAlumno;
    private Perfil perfil;
}
