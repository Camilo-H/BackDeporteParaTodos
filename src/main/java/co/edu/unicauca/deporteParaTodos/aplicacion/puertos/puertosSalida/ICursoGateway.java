package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida;

import java.util.List;
import java.util.Optional;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Curso;

public interface ICursoGateway{
    public boolean existeCurso(String nombreCurso);
    public List<Curso> obtenerCursos();
    public Optional<Curso> obtenerCurso(String nombreCurso);
    public Curso insertarCurso(Curso curso);
    public Curso actualizarCurso(Curso curso, String nombreCurso);
    public Curso eliminarCurso(String nombre);
}
