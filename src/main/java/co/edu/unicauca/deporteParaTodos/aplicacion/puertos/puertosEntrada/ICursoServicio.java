package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.CursoDto;

public interface ICursoServicio {
    /***
     * Obtiene todos los cursos del sistema sin restricciones
     * @return lista de cursos en formato Dto
     */
    public List<CursoDto> recuperarCursos();
    /***
     * Obteine todos los cursos de una categoria que esten disponibles
     * @param categoria
     * @return lista de cursos en formato Dto
     */
    public List<CursoDto> cursosDeCategoria(String categoria);
    /***
     * Obtien un curso del sistema a partir del titulo de la categoria y el nombre del curso que fungen como identificadores
     * @param titulo identificador de la categoria
     * @param nombre identificador del curso
     * @return curso encontrado
     */
    public CursoDto obtenerCurso(String titulo, String nombre);
    /***
     * Inserta un curso en el sistema
     * @param datosCurso informacion del curso
     * @return curso registrado
     */
    public CursoDto insertarCurso(CursoDto datosCurso);
    public CursoDto actualizarCurso(String categoria, String curso, CursoDto datCurso);
    public CursoDto eliminarCurso(String titulo);
}
