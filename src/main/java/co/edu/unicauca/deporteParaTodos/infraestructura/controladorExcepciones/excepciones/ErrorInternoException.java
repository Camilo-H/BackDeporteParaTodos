package co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones;

import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.formatoError.CodigoError;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorInternoException extends RuntimeException{
    private final String llaveMensaje;
    private final String codigo;

    public ErrorInternoException(){
        this.llaveMensaje = CodigoError.NO_CONVERTIBLE.getLlaveMensaje();
        this.codigo = CodigoError.NO_CONVERTIBLE.getCodigo();
    }
}
