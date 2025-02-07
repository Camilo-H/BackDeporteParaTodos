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

public class Curso {

    private String nombre;

    private Deporte deporte;

    private Categoria categoriaCurso;

    private String descripcion;

    private Imagen objImagen;

    private List<Grupo> grupos;
}