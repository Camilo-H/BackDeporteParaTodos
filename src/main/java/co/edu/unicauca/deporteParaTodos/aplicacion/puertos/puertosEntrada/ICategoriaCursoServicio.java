package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.CategoriaDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.peticion.CategoriaInDTO;
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

    public Categoria obtenerCategoriaCursoPorId(String tituloCategoria);

    public CategoriaDTO registrarCategoria(CategoriaInDTO datos);

    public CategoriaDTO actualizarCategoria(String titulo, CategoriaInDTO datosCategoria);

    public CategoriaDTO eliminarCategoria(String tituloCategoria);

}