package co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones;

import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.formatoError.CodigoError;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class NoImplementadoException extends RuntimeException{
    private final String llaveMensaje;
    private final String codigo;

    public NoImplementadoException(){
        llaveMensaje = CodigoError.NO_IMPLEMENTADO.getLlaveMensaje();
        codigo = CodigoError.NO_IMPLEMENTADO.getCodigo();
    }
}
