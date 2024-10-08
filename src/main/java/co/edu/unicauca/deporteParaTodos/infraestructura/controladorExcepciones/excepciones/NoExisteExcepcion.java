package co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones;

import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.formatoError.CodigoError;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NoExisteExcepcion extends RuntimeException{
    private final String llaveMensaje;
    private final String codigo;

    public NoExisteExcepcion(){
        llaveMensaje = CodigoError.NO_EXISTE.getLlaveMensaje();
        codigo = CodigoError.NO_EXISTE.getCodigo();
    }

    /**
     * Sobrecarga del constructor para agregar informacion complementaria sobre el error
     * @param mensaje informacion complementaria
     */
    public NoExisteExcepcion(final String mensaje){
        super(mensaje);
        llaveMensaje = CodigoError.NO_EXISTE.getLlaveMensaje();
        codigo = CodigoError.NO_EXISTE.getCodigo();
    }
}
