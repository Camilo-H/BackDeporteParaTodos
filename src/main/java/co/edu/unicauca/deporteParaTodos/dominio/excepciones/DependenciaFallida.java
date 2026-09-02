package co.edu.unicauca.deporteParaTodos.dominio.excepciones;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DependenciaFallida extends RuntimeException {
    private final String llaveMensaje;
    private final String codigo;

    public DependenciaFallida(CodigoError code) {
        super(code.getCodigo());
        this.llaveMensaje = code.getLlaveMensaje();
        this.codigo = code.getCodigo();
    }

    public DependenciaFallida(final String message) {
        super(message);
        this.llaveMensaje = CodigoError.DEPENDICEA_FALLIDA.getLlaveMensaje();
        this.codigo = CodigoError.DEPENDICEA_FALLIDA.getCodigo();
    }
}
