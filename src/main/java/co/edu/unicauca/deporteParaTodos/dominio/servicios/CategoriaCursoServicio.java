package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICategoriaCursoServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.ICategoriaCursoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.CategoriaDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.ImagenDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.peticion.CategoriaInDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.InsercionFallidaExepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.mappers.MapperImagen;

import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaCursoServicio implements ICategoriaCursoServicio {

    // @Autowired

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ICategoriaCursoGateway categoriaCursoGateway;

    @Override
    public List<Categoria> recuperarCategoriasCurso() {
        List<Categoria> categorias = categoriaCursoGateway.obtenerCategorias();
        if (categorias.isEmpty()) {
            throw new ListadoVacioExcepcion("No se encuentran categorias registradas");
        }
        return categorias;
    }

    @Override
    @Transactional
    public Categoria insertarCategoria(Categoria datosCategoria) {
        Categoria categoriaInsertada = categoriaCursoGateway.insertarCategoria(datosCategoria);
        if (categoriaInsertada == null) {
            throw new InsercionFallidaExepcion("La insercion no se pudo realizar con exito");
        }
        return categoriaInsertada;
    }

    @Override
    public Categoria obtenerCategoriaCursoPorId(String tituloCategoria) {
        // TODO Auto-generated method stub
        return categoriaCursoGateway.obtenerCategoria(tituloCategoria)
                .orElseThrow(
                        () -> new NoExisteExcepcion("La categoria con el titulo " + tituloCategoria + " no existe"));
    }

    @Override
    @Transactional
    public Categoria actualizarCategoria(String titulo, Categoria datosCategoria) {
        // TODO Auto-generated method stub
        Optional<Categoria> entidadExistente = categoriaCursoGateway.obtenerCategoria(titulo);
        if (entidadExistente.isEmpty()) {
            throw new NoExisteExcepcion("La categoría con el título " + titulo + " no existe.");
        }
        return categoriaCursoGateway.actualizarCategoria(titulo, datosCategoria);
    }

    @Override
    public Categoria eliminarCategoria(String tituloCategoria) {
        // TODO Auto-generated method stub
        if (!categoriaCursoGateway.existeCategoria(tituloCategoria)) {
            throw new NoExisteExcepcion("La categoría con el título " + tituloCategoria + " no existe.");
        }
        // Procede a eliminar
        return categoriaCursoGateway.eliminarCategoria(tituloCategoria);
    }

    @Override
    public CategoriaDTO registrarCategoria(CategoriaInDTO datos) {
        // TODO Auto-generated method stub

        Categoria nuevaCategoria = new Categoria();
        ImagenDTO imagen = new ImagenDTO();
        Imagen img;
        if (datos.getImagen() != null || datos.getImagen().isEmpty()) {
            imagen = MapperImagen.multiparfileToImagenDTO(datos.getImagen());
            img = mapper.map(imagen, Imagen.class);
            nuevaCategoria.setImagen(img);
           
        }
        nuevaCategoria.setTitulo(datos.getNombre());
        nuevaCategoria.setDescripcion(datos.getDescripcion());

        Categoria regitro = categoriaCursoGateway.registrarCategoria(nuevaCategoria);
        ImagenDTO dtoimagen = null;
        if(regitro.getImagen() !=null){
            dtoimagen = mapper.map(regitro.getImagen(), ImagenDTO.class);
            System.out.println("******* esta es una vga DTO "+ dtoimagen.getLongitud());
        }
        

        CategoriaDTO resp = new CategoriaDTO();
        resp.setTitulo(regitro.getTitulo());
        resp.setDescripcion(regitro.getDescripcion());
        resp.setImagen(dtoimagen);

        return resp;
    }
}
