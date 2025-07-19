package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida;

import java.util.List;
import java.util.Optional;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Curso;

public interface ICursoGateway{
    /***
     * verifica la existencia de un curso a partir de su nombre
     */
    public boolean existeCurso(String nombreCurso);
    /**
     * Obtinen toda los cursos en el sistema sin restricciones
     * @return lista de cursos.
     */
    public List<Curso> obtenerCursos();
    public Optional<Curso> obtenerCurso(String nombreCurso);
    /***
     * Obteine todos los cursos de una categoria que esten disponibles
     * @param categoria
     * @return lista de cursos en formato del dominio
     */
    public List<Curso> obtenerCursoDeCategoria(String nombreCategoria);
    public Curso insertarCurso(Curso curso);
    public Curso actualizarCurso(Curso curso, String nombreCurso);
    public Curso eliminarCurso(String nombre);
}
