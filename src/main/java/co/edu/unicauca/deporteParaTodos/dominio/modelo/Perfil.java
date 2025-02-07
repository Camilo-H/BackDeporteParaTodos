package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Perfil {

    private String perf_id;

    private String perf_nombre;

    private String perf_correo;

    private Imagen perf_imagen;

    private String perf_tipo;

    private String perf_Sexo;
}
