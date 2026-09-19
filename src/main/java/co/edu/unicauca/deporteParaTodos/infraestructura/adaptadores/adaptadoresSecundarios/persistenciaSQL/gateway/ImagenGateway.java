package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IImagenGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ImagenEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IImagenRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.mappers.ImagenMapper;
import jakarta.transaction.Transactional;

@Service
public class ImagenGateway implements IImagenGateway {

    private final IImagenRepositorio repoImagen;

    public ImagenGateway(IImagenRepositorio repoImagen) {
        this.repoImagen = repoImagen;
    }

    @Override
    public List<Imagen> obtenerImagenes() {
        Iterable<ImagenEntidad> iterable = repoImagen.findAll();
        List<Imagen> lista = new ArrayList<>();
        iterable.forEach(entidad -> lista.add(ImagenMapper.toDominio(entidad)));
        return lista;
    }

    @Override
    public boolean existeImagen(Integer id) {
        return repoImagen.existsById(id);
    }

    @Transactional
    @Override
    public Imagen insertarImagen(Imagen imagen) {
        ImagenEntidad entidad = ImagenMapper.toEntidad(imagen);
        ImagenEntidad entidadGuardada = repoImagen.save(entidad);
        if (repoImagen.findById(entidadGuardada.getId()).isEmpty()) {
            return null;
        }
        return ImagenMapper.toDominio(entidadGuardada);
    }

    @Override
    public Imagen obtenerImagen(Integer id) {
        Optional<ImagenEntidad> entidadRecuperada = repoImagen.findById(id);
        if (entidadRecuperada.isEmpty()) {
            return null;
        }
        return ImagenMapper.toDominio(entidadRecuperada.get());
    }

    @Override
    public Imagen eliminarImagen(Integer id) {
        Imagen imagenEliminada = this.obtenerImagen(id);
        if (imagenEliminada == null) {
            return null;
        }
        repoImagen.deleteById(id);
        if (this.existeImagen(id)) {
            return null;
        }
        return imagenEliminada;
    }
}
