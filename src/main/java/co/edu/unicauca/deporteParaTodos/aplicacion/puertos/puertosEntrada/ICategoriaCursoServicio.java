package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.CategoriaDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.peticion.CategoriaInDTO;

public interface ICategoriaCursoServicio {

    public List<Categoria> recuperarCategoriasCurso();

    public Categoria obtenerCategoriaCursoPorId(String tituloCategoria);

    public CategoriaDTO registrarCategoria(CategoriaInDTO datos);

    public CategoriaDTO actualizarCategoria(String titulo, CategoriaInDTO datosCategoria);

    public CategoriaDTO eliminarCategoria(String tituloCategoria);

}