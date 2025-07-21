package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida;

import java.util.List;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Curso;

public interface ICursoGateway{
    /**
     * Verifica la existencia de un curso en el sistema
     * @param categoria identificador de la categoria
     * @param nombreCurso identifiacdor del curso
     * @return true si existe, false de lo contrario
     */
    public boolean existeCurso(String categoria, String nombreCurso);
    /**
     * Obtinen toda los cursos en el sistema sin restricciones
     * @return lista de cursos.
     */
    public List<Curso> obtenerCursos();
    /**
     * Obtiene un curso del sistema a partir de sus identificadores
     * @param categoria identificador de la categoria
     * @param nombreCurso identificador del curso
     * @return curso enconstrado
     */
    public Curso obtenerCurso(String categoria, String nombreCurso);
    /***
     * Obteine todos los cursos de una categoria que esten disponibles
     * @param categoria
     * @return lista de cursos en formato del dominio
     */
    public List<Curso> obtenerCursosDeCategoria(String nombreCategoria);
    /***
     * Registra un curso en el sistema
     * @param curso entidad a ser insertada
     * @return curso insertado
     */
    public Curso insertarCurso(Curso curso);
    public Curso actualizarCurso(String categoria, String nombre, Curso curso);
    public Curso eliminarCurso(String nombre);
}
