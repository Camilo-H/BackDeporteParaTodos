package co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones;

import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.formatoError.CodigoError;
import lombok.Getter;

@Getter
public class ListadoVacioExcepcion extends RuntimeException{
    
    private final String llaveMensaje;
    private final String codigo;

    public ListadoVacioExcepcion(CodigoError code) {
        super(code.getCodigo());
        this.llaveMensaje = code.getLlaveMensaje();
        this.codigo = code.getCodigo();
    }

    public ListadoVacioExcepcion(final String message) {
        super(message);
        this.llaveMensaje = CodigoError.LISTADO_VACIO.getLlaveMensaje();
        this.codigo = CodigoError.LISTADO_VACIO.getCodigo();
    }
}
