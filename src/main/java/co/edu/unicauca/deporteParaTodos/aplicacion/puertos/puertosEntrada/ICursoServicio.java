package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Curso;
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
    public CursoDto obtenerCurso(String titulo);
    public CursoDto insertarCurso(CursoDto datosCurso);
    public CursoDto actualizarCurso(CursoDto datCurso);
    public CursoDto eliminarCurso(String titulo);
}
