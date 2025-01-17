package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;

public interface ICategoriaCursoServicio  {
    public List<Categoria> recuperarCategoriasCurso();

    public Categoria insertarCategoria(Categoria datosCategoria);

    public Categoria obtenerCategoriaCursoPorId(String tituloCategoria);

    public Categoria actualizarCategoria(String titulo, Categoria datosCategoria);

    public Categoria eliminarCategoria(String tituloCategoria);
}