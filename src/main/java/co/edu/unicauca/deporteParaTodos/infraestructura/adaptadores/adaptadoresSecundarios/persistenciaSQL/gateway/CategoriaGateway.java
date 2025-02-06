package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.ICategoriaCursoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;
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
    public Categoria insertarCategoria(Categoria categoria) {

        CategoriaCursoEntidad entidad = mapper.map(categoria, CategoriaCursoEntidad.class);
        // Opcional: Validar y asignar manualmente la imagen en caso de problemas con
        // ModelMapper
        if (categoria.getImagen() != null) {
            ImagenEntidad imagenEntidad = mapper.map(categoria.getImagen(), ImagenEntidad.class);
            entidad.setObjImagenCategoria(imagenEntidad);
        }
        CategoriaCursoEntidad entidadGuardada = repoCategoria.save(entidad);
        return mapper.map(entidadGuardada, Categoria.class);
    }

    @Override
    public Categoria actualizarCategoria(String titulo, Categoria categoria) {

        if (!existeCategoria(titulo)) {
            throw new NoExisteExcepcion("La categoría no existe.");
        }
        // Obtener la entidad existente o lanzar una excepción si no se encuentra
        CategoriaCursoEntidad entidadExistente = repoCategoria.findById(titulo)
                .orElseThrow(() -> new NoExisteExcepcion("La categoría no existe."));

        // Actualizar los datos de la entidad con los datos del modelo
        entidadExistente.setDescripcion(categoria.getDescripcion());
        if (categoria.getImagen() != null) {
            ImagenEntidad imagenEntidad = mapper.map(categoria.getImagen(), ImagenEntidad.class);
            entidadExistente.setObjImagenCategoria(imagenEntidad);
        }
        // Guarda y mapea la entidad actualizada de nuevo al modelo
        CategoriaCursoEntidad entidadActualizada = repoCategoria.save(entidadExistente);
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

    @Override
    public Categoria registrarCategoria(Categoria datosCategoria) {
        if (existeCategoria(datosCategoria.getTitulo())) {
            throw new YaExisteElementoExcepcion(null);
        }

        CategoriaCursoEntidad entidadInsertar = mapper.map(datosCategoria, CategoriaCursoEntidad.class);
        ImagenEntidad imgEntidad = mapper.map(datosCategoria.getImagen(), ImagenEntidad.class);
        entidadInsertar.setObjImagenCategoria(imgEntidad);
        repoCategoria.save(entidadInsertar);

        Categoria categoriacreada = mapper.map(entidadInsertar, Categoria.class);
        Imagen  ent = categoriacreada.getImagen();
        System.out.println("--NOMBRE DE CATEGORÍA INSERTADA: "+categoriacreada.getTitulo() +" , "+ categoriacreada.getDescripcion());
        System.out.println("--ID IMAGEN INSERTADA: "+ ent.getId() + ", "+ ent.getNombre()+ " , "+ ent.getTipoArchivo());
        System.out.println("--Longitud IMAGEN INSERTADA: "+ categoriacreada.getImagen().getLongitud() );
        return categoriacreada;
    }
}
