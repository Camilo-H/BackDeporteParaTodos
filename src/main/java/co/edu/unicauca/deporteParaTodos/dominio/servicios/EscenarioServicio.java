package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IEscenarioServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IEscenarioGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Escenario;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.YaExisteElementoExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EscenarioServicio implements IEscenarioServicio {

    @Autowired
    private IEscenarioGateway escenarioGateway;

    @Override
    public List<Escenario> listarEscenarios() {
        List<Escenario> escenarios = escenarioGateway.listarEscenarios();
        if (escenarios.isEmpty()) {
            throw new ListadoVacioExcepcion("No se encontraron escenarios registrados");
        }
        return escenarios;
    }

    @Override
    public Escenario obtenerEscenario(Integer id) {
        if (!escenarioGateway.existeEscenario(id)) {
            throw new NoExisteExcepcion("El escenario con id " + id + " no existe");
        }
        return escenarioGateway.obtenerEscenario(id);
    }

    @Override
    public Escenario insertarEscenario(Escenario escenario) {
        if (escenarioGateway.existeEscenarioPorNombre(escenario.getNombre())) {
            throw new YaExisteElementoExcepcion("Ya existe un escenario con el nombre '" + escenario.getNombre() + "'");
        }
        return escenarioGateway.insertarEscenario(escenario);
    }

    @Override
    public Escenario actualizarEscenario(Integer id, Escenario escenario) {
        if (!escenarioGateway.existeEscenario(id)) {
            throw new NoExisteExcepcion("El escenario con id " + id + " no existe");
        }
        Escenario actual = escenarioGateway.obtenerEscenario(id);
        if (!escenario.getNombre().equals(actual.getNombre()) && escenarioGateway.existeEscenarioPorNombre(escenario.getNombre())) {
            throw new YaExisteElementoExcepcion("Ya existe otro escenario con el nombre '" + escenario.getNombre() + "'");
        }
        return escenarioGateway.actualizarEscenario(id, escenario);
    }

    @Override
    public Escenario eliminarEscenario(Integer id) {
        if (!escenarioGateway.existeEscenario(id)) {
            throw new NoExisteExcepcion("El escenario con id " + id + " no existe");
        }
        Escenario actual = escenarioGateway.obtenerEscenario(id);
        if (actual.getEliminado() != null && actual.getEliminado() == 1) {
            throw new YaExisteElementoExcepcion("El escenario ya se encuentra eliminado");
        }
        return escenarioGateway.eliminarEscenario(id);
    }
}
