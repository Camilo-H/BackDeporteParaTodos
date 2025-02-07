package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida;

import java.util.List;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;

public interface IImagenGateway {
    public List<Imagen> obtenerImagenes();
    public boolean existeImagen(Integer id);
    public Imagen insertarImagen(Imagen imagen);
    public Imagen obtenerImagen(Integer id);
    public Imagen actualizarImagen(Integer id, Imagen imagen);
    public Imagen eliminarImagen(Integer id);
}
