package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;


import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;

public interface IImagenServicio {
    public List<Imagen> obtenerImagenes();
    public Imagen obtenerImagen(Integer id);
    public Imagen insertarImagen(Imagen imagen);
    public Imagen eliminarImagen(Integer id);
}
