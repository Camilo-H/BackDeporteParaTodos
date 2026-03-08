package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.ImagenDto;

public interface IImagenServicio {
    public List<ImagenDto> obtenerImagenes();
    public ImagenDto obtenerImagen(Integer id);
    public ImagenDto insertarImagen(ImagenDto imagen);
    public ImagenDto eliminarImagen(Integer id);
}
