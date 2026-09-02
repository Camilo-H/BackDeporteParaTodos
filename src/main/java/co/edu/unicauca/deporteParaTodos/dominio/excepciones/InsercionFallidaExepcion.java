package co.edu.unicauca.deporteParaTodos.dominio.excepciones;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsercionFallidaExepcion extends RuntimeException {
    private final String llaveMensaje;
    private final String codigo;

    public InsercionFallidaExepcion(final String mensaje) {
        super(mensaje);
        llaveMensaje = CodigoError.INSERCION_FALLIDA.getLlaveMensaje();
        codigo = CodigoError.INSERCION_FALLIDA.getCodigo();
    }
}
