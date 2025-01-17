package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Coordinador;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICoordinadorServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.ICoordinadorGateway;

@Service
public class CoordinadorServico implements ICoordinadorServicio {

    @Autowired
    private ICoordinadorGateway coordGateway;

    @Override
    public List<Coordinador> obtenerCoordinadores() {
        List<Coordinador> lista = coordGateway.obtenerCoordinadores();
        if (lista.isEmpty()) {
            throw new ListadoVacioExcepcion("No exosten instructores registrados");
        }
        return lista;
    }

    @Override
    public Coordinador insertarCoordinador(Coordinador datosCoordinador) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertarCoordinador'");
    }

    @Override
    public Coordinador obtenerCoordinador(String coorId) {
        return coordGateway.obtenerCoordinador(coorId)
                .orElseThrow(() -> new NoExisteExcepcion("No existe el coodinador con el identificador " + coorId));
    }

    @Override
    public Coordinador actualizarCoordinador(String coordId, Coordinador datosCoordinador) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizarCoordinador'");
    }

    @Override
    public Coordinador eliminarCoordinador(String coordId) {
        if (!coordGateway.existeCoordinador(coordId)) {
            throw new NoExisteExcepcion("No existe el coodinador con el identificador " + coordId);
        }
        return coordGateway.eliminarCoordinador(coordId);
    }

}
