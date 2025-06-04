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
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IImagenRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.DependenciaFallida;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ErrorInternoException;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.InsercionFallidaExepcion;
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

    @Autowired
    private IImagenRepositorio repoImagen;

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
    public Categoria obtenerCategoria(String nombreCategoria) {
        CategoriaCursoEntidad entidadRecuperada = repoCategoria.findById(nombreCategoria).orElseThrow(
            () -> new NoExisteExcepcion()
        );
        return Categoria.fabricarDeEntidad(entidadRecuperada);
    }

    @Override
    public Categoria registrarCategoria(Categoria datosCategoria) {
        //verificacion a la insercion
        if (existeCategoria(datosCategoria.getTitulo())) {
            throw new YaExisteElementoExcepcion(null);
        }

        //verificamos imagen
        if(!repoImagen.existsById(datosCategoria.getImagen())){
            throw new DependenciaFallida("La imagen no se encuentra registrada");
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
        //encuentro el item objetivo - lanzar excepcion en caso de no encontrarlo
        CategoriaCursoEntidad entidadCategoriaExistente = repoCategoria.findById(titulo)
                .orElseThrow(() -> new NoExisteExcepcion("No se encuentra el registro de la categoria"));
        
        //verifico la existencia de la imagen
        //verificamos imagen
        if(!repoImagen.existsById(prmcategoria.getImagen())){
            throw new DependenciaFallida("La imagen no se encuentra registrada");
        }
                //fijo las propiedades a editar
        entidadCategoriaExistente.setDescripcion(prmcategoria.getDescripcion());
        //
        entidadCategoriaExistente.setCat_imagen(prmcategoria.getImagen());
        //actualizo
        CategoriaCursoEntidad entidadActualizada = repoCategoria.save(entidadCategoriaExistente);
        return Categoria.fabricarDeEntidad(entidadActualizada);
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
