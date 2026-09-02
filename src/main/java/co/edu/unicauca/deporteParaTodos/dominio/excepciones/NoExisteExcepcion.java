package co.edu.unicauca.deporteParaTodos.dominio.excepciones;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NoExisteExcepcion extends RuntimeException {
    private final String llaveMensaje;
    private final String codigo;

    public NoExisteExcepcion() {
        llaveMensaje = CodigoError.NO_EXISTE.getLlaveMensaje();
        codigo = CodigoError.NO_EXISTE.getCodigo();
    }

    public NoExisteExcepcion(final String mensaje) {
        super(mensaje);
        llaveMensaje = CodigoError.NO_EXISTE.getLlaveMensaje();
        codigo = CodigoError.NO_EXISTE.getCodigo();
    }
}
