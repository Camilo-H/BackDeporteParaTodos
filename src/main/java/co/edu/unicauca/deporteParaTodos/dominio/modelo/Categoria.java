package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Categoria {

    private String titulo;

    private String descripcion;

    private String rutaImagen;

    private Imagen imagen;

    private List<Curso> cursos;
    
}
