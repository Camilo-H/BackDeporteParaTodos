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

    @Override
    public List<Categoria> obtenerCategorias() {
        Iterable<CategoriaCursoEntidad> categoriasEntidad = repoCategoria.findAll();
        List<Categoria> categorias = new ArrayList<Categoria>();
        categorias = mapper.map(categoriasEntidad, new TypeToken<List<Categoria>>() {
        }.getType());
        return categorias;
    }

    @Override
    public Optional<Categoria> obtenerCategoria(String nombreCategoria) {
        Optional<CategoriaCursoEntidad> entidadRecuperada = repoCategoria.findById(nombreCategoria);
        return entidadRecuperada.map(categoriaEntidad -> mapper.map(categoriaEntidad, Categoria.class));
    }

    @Override
    public Categoria registrarCategoria(Categoria datosCategoria) {
        if (existeCategoria(datosCategoria.getTitulo())) {
            throw new YaExisteElementoExcepcion(null);
        }
        CategoriaCursoEntidad entidadInsertar = mapper.map(datosCategoria, CategoriaCursoEntidad.class);
        ImagenEntidad imgEntidad = mapper.map(datosCategoria.getImagen(), ImagenEntidad.class);
        //entidadInsertar.setObjImagenCategoria(imgEntidad);
        CategoriaCursoEntidad entidadInsertada = repoCategoria.save(entidadInsertar);
        Categoria categoriacreada = mapper.map(entidadInsertada, Categoria.class);
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

        System.out.println("---"+prmcategoria.getDescripcion());
        System.out.println("---"+prmcategoria.getImagen().getNombre());
        System.out.println("---"+prmcategoria.getImagen().getTipoArchivo());

        if (prmcategoria.getImagen() != null) {
            /*if (entidadCategoriaExistente.getObjImagenCategoria() != null) {

                ImagenEntidad entidadImagenExistente = entidadCategoriaExistente.getObjImagenCategoria();
                
                entidadImagenExistente.setNombre(prmcategoria.getImagen().getNombre());
                entidadImagenExistente.setTipoArchivo(prmcategoria.getImagen().getTipoArchivo());
                entidadImagenExistente.setLongitud(prmcategoria.getImagen().getLongitud());
                entidadImagenExistente.setDatos(prmcategoria.getImagen().getDatos());
                System.out.println("----ID IMAGEN ENTIDAD "+entidadImagenExistente.getId());
                entidadCategoriaExistente.setObjImagenCategoria(entidadImagenExistente);
            } else {
                ImagenEntidad nuevaImagen = mapper.map(prmcategoria.getImagen(), ImagenEntidad.class);
                entidadCategoriaExistente.setObjImagenCategoria(nuevaImagen);
            }*/
        }

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
