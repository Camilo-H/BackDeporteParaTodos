package co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones;

import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.formatoError.CodigoError;
import lombok.Getter;

@Getter
public class ArchivoNoConvertibleExcepcion extends RuntimeException{
    private final String llaveMensaje;
    private final String codigo;
    public ArchivoNoConvertibleExcepcion(){
        llaveMensaje = CodigoError.ARCHIVO_NO_CONVERTIBLE.getLlaveMensaje();
        codigo = CodigoError.ARCHIVO_NO_CONVERTIBLE.getCodigo();
    }

    /***
     * Sobrecarga constructor para añadir informacion a la excepcion
     * @param mensaje informacion complementaria sobre el error
     */
    public ArchivoNoConvertibleExcepcion(final String mensaje){
        super(mensaje);
        llaveMensaje = CodigoError.ARCHIVO_NO_CONVERTIBLE.getLlaveMensaje();
        codigo = CodigoError.ARCHIVO_NO_CONVERTIBLE.getCodigo();
    }
}
