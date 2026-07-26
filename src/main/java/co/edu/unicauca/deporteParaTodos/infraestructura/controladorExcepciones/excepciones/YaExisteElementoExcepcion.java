package co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones;

import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.formatoError.CodigoError;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YaExisteElementoExcepcion extends RuntimeException {
    private final String llaveMensaje;
    private final String codigo;

    public YaExisteElementoExcepcion(final String mensaje) {
        super(mensaje);
        llaveMensaje = CodigoError.YA_EXISTE.getLlaveMensaje();
        codigo = CodigoError.YA_EXISTE.getCodigo();
    }
}
