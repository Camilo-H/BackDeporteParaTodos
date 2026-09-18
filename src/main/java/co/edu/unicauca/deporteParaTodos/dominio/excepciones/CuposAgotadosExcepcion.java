package co.edu.unicauca.deporteParaTodos.dominio.excepciones;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CuposAgotadosExcepcion extends RuntimeException {
    private final String llaveMensaje;
    private final String codigo;

    public CuposAgotadosExcepcion(final String mensaje) {
        super(mensaje);
        llaveMensaje = CodigoError.CUPOS_AGOTADOS.getLlaveMensaje();
        codigo = CodigoError.CUPOS_AGOTADOS.getCodigo();
    }
}
