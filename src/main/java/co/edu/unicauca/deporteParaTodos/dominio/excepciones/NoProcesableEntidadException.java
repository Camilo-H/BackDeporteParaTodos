package co.edu.unicauca.deporteParaTodos.dominio.excepciones;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NoProcesableEntidadException extends RuntimeException {
    private final String llaveMensaje;
    private final String codigo;

    public NoProcesableEntidadException(CodigoError code) {
        super(code.getCodigo());
        this.llaveMensaje = code.getLlaveMensaje();
        this.codigo = code.getCodigo();
    }

    public NoProcesableEntidadException(final String message) {
        super(message);
        this.llaveMensaje = CodigoError.ENTIDAD_NO_PROCESABLE.getLlaveMensaje();
        this.codigo = CodigoError.ENTIDAD_NO_PROCESABLE.getCodigo();
    }
}
