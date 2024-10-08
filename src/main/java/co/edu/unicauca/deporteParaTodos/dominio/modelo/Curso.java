package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Curso {

    private String nombreCurso;
    
    private String nombreDeporte;

    private String tituloCategoria;

    private Imagen imagen;
    
    private String descripcion;
}