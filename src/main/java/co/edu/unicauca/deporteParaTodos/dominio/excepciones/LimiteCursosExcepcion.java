package co.edu.unicauca.deporteParaTodos.dominio.excepciones;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LimiteCursosExcepcion extends RuntimeException {
    private final String llaveMensaje;
    private final String codigo;

    public LimiteCursosExcepcion(final String mensaje) {
        super(mensaje);
        llaveMensaje = CodigoError.LIMITE_CURSOS_ALUMNO.getLlaveMensaje();
        codigo = CodigoError.LIMITE_CURSOS_ALUMNO.getCodigo();
    }
}
