package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;

public interface ICategoriaCursoServicio {

    /**
     * retorna las categorias disponibles en el sistema, aquellas marcadas como eliminadas no seran retornadas
     * @return lista de categorias
     */
    public List<Categoria> recuperarCategoriasCurso();

    /**
     * Insertar una categoria en el sistema
     * @param categoria categoria a insertar
     * @return categoria insertada
     */
    public Categoria insertarCategoria(Categoria categoria);

    /**
     * Recupera una categoria a partir de su identificador
     * @param tituloCategoria identificador
     * @return categoria recuperada
     */
    public Categoria obtenerCategoriaCursoPorId(String tituloCategoria);

    /**
     * actualiza la informacion de la categoria y retorna sus datos actualizados
     * @param titulo
     * @param datosCategoria
     * @return
     */
    public Categoria actualizarCategoria(String titulo, Categoria datosCategoria);

    /**
     * elimina o marca como eliminada una categoria, dependiendo de sus dependencias asociadas
     * @param tituloCategoria identificador de la categoria
     * @return categoria eliminada o marcada
     */
    public Categoria eliminarCategoria(String tituloCategoria);

}
