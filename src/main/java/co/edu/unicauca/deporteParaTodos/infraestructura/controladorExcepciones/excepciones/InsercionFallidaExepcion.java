package co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones;

import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.formatoError.CodigoError;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsercionFallidaExepcion extends RuntimeException{
    private final String llaveMensaje;
    private final String codigo;

    /**
     * Sobrecarga del constructor para agregar informacion complementaria sobre el error
     * @param mensaje informacion complementaria
     */
    public InsercionFallidaExepcion(final String mensaje){
        super(mensaje);
        llaveMensaje = CodigoError.INSERCION_FALLIDA.getLlaveMensaje();
        codigo = CodigoError.INSERCION_FALLIDA.getCodigo();
    }
}
