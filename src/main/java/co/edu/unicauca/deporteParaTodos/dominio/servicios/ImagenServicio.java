package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IImagenServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IImagenGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.ImagenDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.mappers.ImagenMapper;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.InsercionFallidaExepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;

@Service
public class ImagenServicio implements IImagenServicio {

    private final IImagenGateway imagenGateway;

    public ImagenServicio(IImagenGateway imagenGateway) {
        this.imagenGateway = imagenGateway;
    }

    @Override
    public List<ImagenDto> obtenerImagenes() {
        List<Imagen> imagenes = imagenGateway.obtenerImagenes();
        if (imagenes.isEmpty()) {
            throw new ListadoVacioExcepcion("No se encuentran imagenes registradas");
        }
        List<ImagenDto> listaDtos = new ArrayList<>();
        imagenes.forEach(modelo -> listaDtos.add(ImagenMapper.toDto(modelo)));
        return listaDtos;
    }

    @Override
    public ImagenDto obtenerImagen(Integer id) {
        if (!imagenGateway.existeImagen(id)) {
            throw new NoExisteExcepcion();
        }
        Imagen imagen = imagenGateway.obtenerImagen(id);
        if (imagen == null) {
            throw new NoExisteExcepcion();
        }
        return ImagenMapper.toDto(imagen);
    }

    @Override
    public ImagenDto insertarImagen(ImagenDto imagenDto) {
        Imagen modelo = ImagenMapper.fromDto(imagenDto);
        Imagen objImagen = imagenGateway.insertarImagen(modelo);
        if (objImagen == null) {
            throw new InsercionFallidaExepcion("La insercion no se pudo realizar");
        }
        return ImagenMapper.toDto(objImagen);
    }

    @Override
    public ImagenDto eliminarImagen(Integer id) {
        if (!imagenGateway.existeImagen(id)) {
            throw new NoExisteExcepcion("La imagen con id " + id + " no existe");
        }
        Imagen modelo = imagenGateway.eliminarImagen(id);
        if (modelo == null) {
            throw new NoExisteExcepcion("La imagen con id " + id + " no existe");
        }
        return ImagenMapper.toDto(modelo);
    }
}
