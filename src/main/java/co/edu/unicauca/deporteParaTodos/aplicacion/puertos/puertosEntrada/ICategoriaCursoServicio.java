package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.CategoriaDto;

public interface ICategoriaCursoServicio {

    /**
     * retorna las categorias disponibles en el sistema, aquellas marcadas como eliminadas no seran retornadas
     * @return lista de categorias
     */
    public List<CategoriaDto> recuperarCategoriasCurso();

    /**
     * Insertar una categoria en el sistema
     * @param categoria categoria a insertar
     * @return categoria insertada
     */
    public CategoriaDto insertarCategoria(CategoriaDto categoria);

    /**
     * Recupera una categoria a partir de su identificador
     * @param tituloCategoria identificador
     * @return categoria recuperada
     */
    public CategoriaDto obtenerCategoriaCursoPorId(String tituloCategoria);

    /**
     * actualiza la infomracion de la categoria y retorna sus datos actualizados
     * @param titulo
     * @param datosCategoria
     * @return
     */
    public CategoriaDto actualizarCategoria(String titulo, CategoriaDto datosCategoria);

    /**
     * elimina o marca como eliminada una categoria, dependiendo de sus dependencias asociadas
     * @param tituloCategoria identificador de la categoria
     * @return categoria eliminada o marcada
     */
    public CategoriaDto eliminarCategoria(String tituloCategoria);

}