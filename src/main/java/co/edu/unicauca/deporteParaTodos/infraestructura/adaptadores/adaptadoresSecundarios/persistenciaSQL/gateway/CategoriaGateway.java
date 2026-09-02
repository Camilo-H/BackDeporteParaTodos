package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.ICategoriaCursoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CategoriaCursoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICategoriaCursoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IImagenRepositorio;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.DependenciaFallida;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoConvertibleException;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.mappers.CategoriaMapper;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.YaExisteElementoExcepcion;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class CategoriaGateway implements ICategoriaCursoGateway {

    @Autowired
    private ICategoriaCursoRepositorio repoCategoria;

    @Autowired
    private IImagenRepositorio repoImagen;


    @Override
    public boolean existeCategoria(String nombreCategoria) {
        return repoCategoria.existsById(nombreCategoria);
    }

    @Override
    public List<Categoria> obtenerCategorias() {
        List<CategoriaCursoEntidad> listaEntidades = repoCategoria.findByEliminado(0);
        List<Categoria> listaCategorias = new ArrayList<>();
        listaEntidades.forEach(entidad ->{
            Categoria categoria = CategoriaMapper.toDominio(entidad);
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
        return CategoriaMapper.toDominio(entidadRecuperada);
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
        CategoriaCursoEntidad entidadInsertar = CategoriaMapper.toEntidad(datosCategoria);
        if(entidadInsertar == null){
            throw new NoConvertibleException();
        }

        //insertar
        CategoriaCursoEntidad entidadInsertada = repoCategoria.save(entidadInsertar);

        //conversion
        Categoria categoriacreada = CategoriaMapper.toDominio(entidadInsertada);

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
        return CategoriaMapper.toDominio(entidadActualizada);
    }

    @Override
    public Categoria eliminarCategoria(String nombreCategoria) {
        Optional<CategoriaCursoEntidad> entidadExistente = repoCategoria.findById(nombreCategoria);
        if (entidadExistente.isEmpty()) {
            throw new NoExisteExcepcion("La categoria con el titulo " + nombreCategoria + " no existe.");
        }
        CategoriaCursoEntidad entidad = entidadExistente.get();

        if (entidad.getEliminado() == 1) {
            throw new YaExisteElementoExcepcion("La categoria ya se encuentra eliminada");
        }

        repoCategoria.marcarComoEliminado(nombreCategoria);
        entidad.setEliminado(1);
        return CategoriaMapper.toDominio(entidad);
    }

}
