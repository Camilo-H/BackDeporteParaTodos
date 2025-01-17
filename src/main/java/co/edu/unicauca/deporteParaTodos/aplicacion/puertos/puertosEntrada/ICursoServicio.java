package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Curso;

public interface ICursoServicio {
    public List<Curso> recuperarCursos();
    public Curso insertarCurso(Curso datosCurso);
    public Curso obtenerCurso(String titulo);
    public Curso actualizarCurso(Curso datCurso);
    public Curso eliminarCurso(String titulo);
}
