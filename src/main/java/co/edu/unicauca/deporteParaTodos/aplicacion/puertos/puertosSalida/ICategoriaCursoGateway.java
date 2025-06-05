package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida;

import java.util.List;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;

public interface ICategoriaCursoGateway {

    /**
     * Verifica la existencia de una categoria a partir del nombre
     * @param nombreCategoria identificador de la categoria
     * @return true si existe, false de lo contrario
     */
    public boolean existeCategoria(String nombreCategoria);

    /***
     * retorna las categorias habiles en el sistema
     * @return lista de categorias
     */
    public List<Categoria> obtenerCategorias();

    /**
     * Recupera una categoria a partir del nombre
     * @param nombreCategoria identificador de la categoria
     * @return categoria obtenida
     */
    public Categoria obtenerCategoria(String nombreCategoria);

    /**
     * Actualiza la una categoria
     * @param titulo identificador de la categoria, debe coincidir con el conteniddo de cuerpo y no es modificable
     * @param categoria cuerpo a actualizar
     * @return categoria actualizada
     */
    public Categoria actualizarCategoria(String titulo, Categoria categoria);

    /**
     * Elimina o marca como eliminada una categoria
     * @param nombreCategoria identificador de la categoria
     * @return categoria eliminada
     */
    public Categoria eliminarCategoria(String nombreCategoria);

    /**
     * inserta una categoria en el sistema
     * el id de la imagen debe pertenecer a una imagen existente
     * @param datosCategoria informacion a insertar
     * @return categoria registrada
     */
    public Categoria registrarCategoria (Categoria datosCategoria);
}
