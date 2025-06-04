package co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones;

import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.formatoError.CodigoError;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DependenciaFallida extends RuntimeException{
/* 
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
    } */
    private final String llaveMensaje;
    private final String codigo;

    public DependenciaFallida(CodigoError code){
        super(code.getCodigo());
        this.llaveMensaje = code.getLlaveMensaje();
        this.codigo = code.getCodigo();
    }

    public DependenciaFallida(final String message){
        super(message);
        this.llaveMensaje = CodigoError.DEPENDICEA_FALLIDA.getLlaveMensaje();
        this.codigo = CodigoError.DEPENDICEA_FALLIDA.getCodigo();
    }
}
