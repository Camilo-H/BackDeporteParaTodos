package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.GrupoDto;

public interface IGrupoServicio {
    /**
     * Obtiene todos los grupos del sistema, marcados como eliminados o no eliminados
     * @return listados de grupos
     */
    public List<GrupoDto> obtenerTodosGrupos();


    /***
     * Obtiene todos los grupos del sistema marcados como no eliminados
     * @return listados de grupos
     */
    public List<GrupoDto> obtenerGruposDisponibles();

    /****
     * Obtiene los grupos asociados a curso que esten disponibles
     * @param categoria identificador de la categoria
     * @param curso identificador del curso
     * @return listado de grupos
     */
    public List<GrupoDto> obtenerGruposDeCurso(String categoria, String curso);

    public List<GrupoDto> obtenerGruposInscripcionDisponible();

    /***
     * Obtener los grupos asociados a un instructor
     * @return listado de grupos
     */
    public List<GrupoDto> obtenerGruposInstructor(String idInstructor);

    /**
     * Agrega un grupo al sistema, el identificador de la imagen debe existir
     * @param datosGrupo datos del grupo
     * @return grupo insertado
     */
    public GrupoDto insertarGrupo(GrupoDto datosGrupo);

    /***
     * Obtiene un grupo identificado con
     * @param categoria identificador de la categoria
     * @param curso identificador del curso
     * @param anio año para identificar grupo
     * @param iterable secuencial para identificar grupo
     * @return grupo obtenido
     */
    public GrupoDto obtenerGrupoPorId(String categoria, String curso, Integer anio, Integer iterable);

    /***
     * Obtiene la informacion de un grupo identificado con
     * @param categoria identificador de la categoria
     * @param curso identificador del curso
     * @param anio anio para identificar el grupo
     * @param iterable secuencial para identificar el grupo
     * @param datosGrupo datos del grupo
     * @return grupo actualizado
     */
    public GrupoDto actualizarGrupo(String categoria, String curso, Integer anio, Integer iterable, GrupoDto datosGrupo);

    /***
     * Marca como elimnado un grupo en el sistema, identficado con
     * @param categoria identificador de la categoria,
     * @param curso identificador del curso
     * @param anio año para identificar el grupo
     * @param iterable secuencial para identificar el grupo
     * @return grupo marcado
     */
    public GrupoDto eliminarGrupo(String categoria, String curso, Integer anio, Integer iterable);

    /***
     * Recupera un curso del sistema identificado con los parametros
     * @param categoria
     * @param curso
     * @param anio
     * @param iterable
     * @return grupo encontrado
     */
    public GrupoDto obtenerGrupo(String categoria, String curso, Integer anio, Integer iterable);

}
