package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.ImagenDto;

public interface IImagenServicio {
    // TODO: estos métodos exponen ImagenDto intencionalmente en esta interfaz;
    // refactorizarlos requiere cambiar también los callers en ImagenRest (fuera del alcance de este PR).
    public List<ImagenDto> obtenerImagenes();
    public ImagenDto obtenerImagen(Integer id);
    public ImagenDto insertarImagen(ImagenDto imagen);
    public ImagenDto eliminarImagen(Integer id);
}
