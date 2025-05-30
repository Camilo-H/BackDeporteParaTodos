package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.ICategoriaCursoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CategoriaCursoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ImagenEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICategoriaCursoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoConvertibleException;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.YaExisteElementoExcepcion;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class CategoriaGateway implements ICategoriaCursoGateway {

    @Autowired
    private ICategoriaCursoRepositorio repoCategoria;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @Override
    public boolean existeCategoria(String nombreCategoria) {
        return repoCategoria.existsById(nombreCategoria);
    }

    /***
     * Retorna una lista de categorias disponibles en el sistema
     * @return lista de categorias encontradas
     */
    @Override
    public List<Categoria> obtenerCategorias() {
        List<CategoriaCursoEntidad> listaEntidades = repoCategoria.findByEliminado(0);
        List<Categoria> listaCategorias = new ArrayList<>();
        listaEntidades.forEach(entidad ->{
            Categoria categoria = Categoria.fabricarDeEntidad(entidad);
            if(categoria!=null){
                listaCategorias.add(categoria);
            }
        });
        return listaCategorias;
    }

    @Override
    public Optional<Categoria> obtenerCategoria(String nombreCategoria) {
        Optional<CategoriaCursoEntidad> entidadRecuperada = repoCategoria.findById(nombreCategoria);
        return entidadRecuperada.map(categoriaEntidad -> mapper.map(categoriaEntidad, Categoria.class));
    }

    @Override
    public Categoria registrarCategoria(Categoria datosCategoria) {
        //verificacion a la insercion
        if (existeCategoria(datosCategoria.getTitulo())) {
            throw new YaExisteElementoExcepcion(null);
        }
        //conversion de datos
        CategoriaCursoEntidad entidadInsertar = CategoriaCursoEntidad.fabricarDeModelo(datosCategoria, 0);
        if(entidadInsertar == null){
            throw new NoConvertibleException();
        }

        //insertar
        CategoriaCursoEntidad entidadInsertada = repoCategoria.save(entidadInsertar);

        //conversion
        Categoria categoriacreada = Categoria.fabricarDeEntidad(entidadInsertada);

        return categoriacreada;
    }

    @Override
    public Categoria actualizarCategoria(String titulo, Categoria prmcategoria) {
        if (!existeCategoria(titulo)) {
            throw new NoExisteExcepcion("No se encuentra el registro de la categoria ");
        }

        CategoriaCursoEntidad entidadCategoriaExistente = repoCategoria.findById(titulo)
                .orElseThrow(() -> new NoExisteExcepcion("No se encuentra el registro de la categoria"));

        entidadCategoriaExistente.setDescripcion(prmcategoria.getDescripcion());

       

        CategoriaCursoEntidad entidadActualizada = repoCategoria.save(entidadCategoriaExistente);
        return mapper.map(entidadActualizada, Categoria.class);
    }

    @Override
    public Categoria eliminarCategoria(String nombreCategoria) {
        Optional<CategoriaCursoEntidad> entidadExistente = repoCategoria.findById(nombreCategoria);
        if (entidadExistente.isPresent()) {
            // Si existe, obtenemos la entidad y la eliminamos
            CategoriaCursoEntidad entidad = entidadExistente.get();
            repoCategoria.delete(entidad);
            // Devuelve la categoría eliminada mapeada a modelo
            return mapper.map(entidad, Categoria.class);
        } else {
            // Si no existe, puedes lanzar una excepción o retornar null
            throw new NoExisteExcepcion("La categoría con el título " + nombreCategoria + " no existe.");
        }
    }

}
