package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IInscripcionServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IInscripcionGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Inscripcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoImplementadoException;

@Service
public class InscripcionServicio implements IInscripcionServicio {

    @Autowired
    private IInscripcionGateway inscrGateway;

    @Override
    public List<Inscripcion> obtenerInscripciones() {
        List<Inscripcion> lista = inscrGateway.obtenerInscripciones();
        return lista;
    }

    @Override
    public Inscripcion obteneInscripcion(Timestamp fecha) {
        return inscrGateway.obteneInscripcion(fecha).orElseThrow(() -> new NoExisteExcepcion());
    }

    @Override
    public Inscripcion insertarInscripcion(Inscripcion datosInscripcion) {
        /* if (inscrGateway.existeInscripcion(datosInscripcion.getFechaInscripcion())) {
            throw new YaExisteElementoExcepcion(null);
        }
        return inscrGateway.insertarInscripcion(datosInscripcion); */
        throw new NoImplementadoException();
    }

    @Override
    public Inscripcion eliminarInscripcion(Timestamp fecha) {
        /* if (inscrGateway.existeInscripcion(fecha)) {
            return inscrGateway.eliminarInscripcion(fecha);
        }
        throw new NoExisteExcepcion(); */
        throw new NoImplementadoException();
    }
}
