package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IImagenServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IImagenGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;

@Service
public class ImagenServicio implements IImagenServicio{

    @Autowired
    private IImagenGateway imagenGateway;

    /***
     * Retorna el listado de imagenes encontrado
     * Si el listado esta vacio, retorna lanza exception de tipo ListadoVacioExcepcion
     */
    @Override
    public List<Imagen> obtenerImagenes() {
        List<Imagen> imagenes = imagenGateway.obtenerImagenes();
        if(imagenes.isEmpty()){
            //Lanzar excepcion, sera capturada por el exceptionHandler.
            throw new ListadoVacioExcepcion("No se encuentran imagenes registradas");
        }
        return imagenes;
    }

    /**
     * Retorna una imagen identificada con id unico
     * @param id: identificador unico de la imagen
     * @return imagen encontrada o excepcion de tipo NoExisteExcepcion
     */
    @Override
    public Imagen obtenerImagen(Integer id) {
        if(!imagenGateway.existeImagen(id)){
            throw new NoExisteExcepcion();
        }
        Imagen imagen = imagenGateway.obtenerImagen(id);
        if(imagen==null){
            //TODO: probablemente lanzar una excepcion de error en el procesamiento sql
            throw new NoExisteExcepcion();
        }
        return imagen;
    }

    @Override
    public Imagen insertarImagen(Imagen imagen) {
        //validaciones de extension
        //validaciones de tipo
        //validaciones de contenido
        return imagenGateway.insertarImagen(imagen);
    }

    @Override
    public Imagen eliminarImagen(Integer id) {
        return imagenGateway.eliminarImagen(id);
    }
    
}
