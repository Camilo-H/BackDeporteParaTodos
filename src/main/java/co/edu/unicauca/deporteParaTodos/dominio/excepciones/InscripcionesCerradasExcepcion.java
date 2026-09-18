package co.edu.unicauca.deporteParaTodos.dominio.excepciones;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InscripcionesCerradasExcepcion extends RuntimeException {
    private final String llaveMensaje;
    private final String codigo;

    public InscripcionesCerradasExcepcion(final String mensaje) {
        super(mensaje);
        llaveMensaje = CodigoError.INSCRIPCIONES_CERRADAS.getLlaveMensaje();
        codigo = CodigoError.INSCRIPCIONES_CERRADAS.getCodigo();
    }
}
