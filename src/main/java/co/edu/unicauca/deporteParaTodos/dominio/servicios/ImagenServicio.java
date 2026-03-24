package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IImagenServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IImagenGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.ImagenDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.InsercionFallidaExepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;

@Service
public class ImagenServicio implements IImagenServicio {

    @Autowired
    private IImagenGateway imagenGateway;

    /***
     * Retorna el listado de imagenes encontrado
     * Si el listado esta vacio, retorna lanza exception de tipo
     * ListadoVacioExcepcion
     */
    @Override
    public List<ImagenDto> obtenerImagenes() {
        List<Imagen> imagenes = imagenGateway.obtenerImagenes();
        if (imagenes.isEmpty()) {
            // Lanzar excepcion, sera capturada por el exceptionHandler.
            throw new ListadoVacioExcepcion("No se encuentran imagenes registradas");
        }
        List<ImagenDto> listaDtos = new ArrayList<>();
        imagenes.forEach(modelo ->{
            ImagenDto dto = ImagenDto.fabricaFromImagenModelo(modelo);
            listaDtos.add(dto);
        });
        return listaDtos;
    }

    /**
     * Retorna una imagen identificada con id unico
     * 
     * @param id: identificador unico de la imagen
     * @return imagen encontrada o excepcion de tipo NoExisteExcepcion
     */
    @Override
    public ImagenDto obtenerImagen(Integer id) {
        if (!imagenGateway.existeImagen(id)) {
            throw new NoExisteExcepcion();
        }
        Imagen imagen = imagenGateway.obtenerImagen(id);
        if (imagen == null) {
            // TODO: probablemente lanzar una excepcion de error en el procesamiento sql
            throw new NoExisteExcepcion();
        }
        ImagenDto dto = new ImagenDto();
        dto = ImagenDto.fabricaFromImagenModelo(imagen);
        return dto;
    }

    @Override
    public ImagenDto insertarImagen(ImagenDto imagen) {
        Imagen modelo = Imagen.fabricaFromImagenDto(imagen);
        if(modelo==null){
            throw new InsercionFallidaExepcion("no fue posible convertir el archivo");
        }

        Imagen objImagen = imagenGateway.insertarImagen(modelo);

        if (objImagen == null) {
            throw new InsercionFallidaExepcion("La insersion no se pudo realizar");
        }

        return ImagenDto.fabricaFromImagenModelo(objImagen);
    }

    @Override
    public ImagenDto eliminarImagen(Integer id) {
        Imagen modelo = imagenGateway.eliminarImagen(id);
        ImagenDto dto = ImagenDto.fabricaFromImagenModelo(modelo);
        return dto;
    }

}
