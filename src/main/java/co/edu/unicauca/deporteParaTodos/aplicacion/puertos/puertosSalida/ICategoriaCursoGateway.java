package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida;

import java.util.List;
import java.util.Optional;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;

public interface ICategoriaCursoGateway {

    public boolean existeCategoria(String nombreCategoria);

    public List<Categoria> obtenerCategorias();

    public Optional<Categoria> obtenerCategoria(String nombreCategoria);

    public Categoria actualizarCategoria(String titulo, Categoria categoria);

    public Categoria eliminarCategoria(String nombreCategoria);

    public Categoria registrarCategoria (Categoria datosCategoria);
}
