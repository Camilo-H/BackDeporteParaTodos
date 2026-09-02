package co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones;

import co.edu.unicauca.deporteParaTodos.dominio.excepciones.CodigoError;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NoConvertibleException extends RuntimeException{
    private final String llaveMensaje;
    private final String codigo;

    public NoConvertibleException(){
        this.llaveMensaje = CodigoError.NO_CONVERTIBLE.getLlaveMensaje();
        this.codigo = CodigoError.NO_CONVERTIBLE.getCodigo();
    }
}
