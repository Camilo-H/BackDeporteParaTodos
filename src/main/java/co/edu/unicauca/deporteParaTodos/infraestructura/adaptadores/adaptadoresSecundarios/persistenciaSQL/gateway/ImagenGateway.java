package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IImagenGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ImagenEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IImagenRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.mappers.MapperImagen;

/***Implemtacion del puerto de salida del dominio */
@Service
public class ImagenGateway implements IImagenGateway{
    
    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IImagenRepositorio repoImagen;

    @Override
    public List<Imagen> obtenerImagenes() {
        Iterable<ImagenEntidad> iterable = repoImagen.findAll();
        List<Imagen> list = new ArrayList<Imagen>();
        //mapeo mediante modelmapper
        list = mapper.map(iterable, new TypeToken<List<Imagen>>(){}.getType());
        System.out.println(list.size());
        return list;
    }

    @Override
    public boolean existeImagen(Integer id) {
        return repoImagen.existsById(id);
    }

    @Override
    public Imagen insertarImagen(Imagen imagen) {
        //mapeo de Imagen a entidad mediante ModelMapper
        ImagenEntidad entidad = mapper.map(imagen, ImagenEntidad.class);
        ImagenEntidad entidadGuardada = repoImagen.save(entidad);
        //el retorno de la insercion es una imagen entidad, por tanto se mapea a imagen correspondiente al modelo
        Imagen imagenGuardada = mapper.map(entidadGuardada, Imagen.class);
        return imagenGuardada;
    }

    @Override
    public Imagen obtenerImagen(Integer id) {
        Optional<ImagenEntidad> entidadRecuperada = repoImagen.findById(id);
        if(entidadRecuperada.isEmpty()){
            return null;
        }
        //se hace un mapeo mediante ModelMapper, recordando que se obtiene en la consulta un optional<ImagenEntidad>
        Imagen imagenRecuperada = mapper.map(entidadRecuperada.get(), Imagen.class);
        return imagenRecuperada;
    }

    @Override
    public Imagen actualizarImagen(Integer id, Imagen imagen) {
        
        if(!repoImagen.existsById(id)){
            return null;
        }
        
        ImagenEntidad entidad = MapperImagen.imagenToentidad(imagen);
        ImagenEntidad imagenActualizada = repoImagen.save(entidad);
        return MapperImagen.entidadToImagen(imagenActualizada);
    }

    @Override
    public Imagen eliminarImagen(Integer id) {
        Imagen imagenEliminada = this.obtenerImagen(id);
        if(imagenEliminada==null){
            return null;
        }
        repoImagen.deleteById(id);
        if(this.existeImagen(id)){
            return null;
        }
        return imagenEliminada;
    }    
}
