package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IEscenarioServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IEscenarioGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Escenario;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.EscenarioDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.YaExisteElementoExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EscenarioServicio implements IEscenarioServicio {

    @Autowired
    private IEscenarioGateway escenarioGateway;

    @Override
    public List<EscenarioDto> listarEscenarios() {
        List<Escenario> escenarios = escenarioGateway.listarEscenarios();
        if (escenarios.isEmpty()) {
            throw new ListadoVacioExcepcion("No se encontraron escenarios registrados");
        }
        List<EscenarioDto> dtos = new ArrayList<>();
        escenarios.forEach(e -> dtos.add(EscenarioDto.fabricarDeModelo(e)));
        return dtos;
    }

    @Override
    public EscenarioDto obtenerEscenario(Integer id) {
        if (!escenarioGateway.existeEscenario(id)) {
            throw new NoExisteExcepcion("El escenario con id " + id + " no existe");
        }
        Escenario escenario = escenarioGateway.obtenerEscenario(id);
        return EscenarioDto.fabricarDeModelo(escenario);
    }

    @Override
    public EscenarioDto insertarEscenario(EscenarioDto dto) {
        if (escenarioGateway.existeEscenarioPorNombre(dto.getNombre())) {
            throw new YaExisteElementoExcepcion("Ya existe un escenario con el nombre '" + dto.getNombre() + "'");
        }
        Escenario escenario = Escenario.fabricarDeDto(dto);
        Escenario guardado = escenarioGateway.insertarEscenario(escenario);
        return EscenarioDto.fabricarDeModelo(guardado);
    }

    @Override
    public EscenarioDto actualizarEscenario(Integer id, EscenarioDto dto) {
        if (!escenarioGateway.existeEscenario(id)) {
            throw new NoExisteExcepcion("El escenario con id " + id + " no existe");
        }
        Escenario actual = escenarioGateway.obtenerEscenario(id);
        if (!dto.getNombre().equals(actual.getNombre()) && escenarioGateway.existeEscenarioPorNombre(dto.getNombre())) {
            throw new YaExisteElementoExcepcion("Ya existe otro escenario con el nombre '" + dto.getNombre() + "'");
        }
        Escenario datos = Escenario.fabricarDeDto(dto);
        Escenario actualizado = escenarioGateway.actualizarEscenario(id, datos);
        return EscenarioDto.fabricarDeModelo(actualizado);
    }

    @Override
    public EscenarioDto eliminarEscenario(Integer id) {
        if (!escenarioGateway.existeEscenario(id)) {
            throw new NoExisteExcepcion("El escenario con id " + id + " no existe");
        }
        Escenario actual = escenarioGateway.obtenerEscenario(id);
        if (actual.getEliminado() != null && actual.getEliminado() == 1) {
            throw new YaExisteElementoExcepcion("El escenario ya se encuentra eliminado");
        }
        Escenario eliminado = escenarioGateway.eliminarEscenario(id);
        return EscenarioDto.fabricarDeModelo(eliminado);
    }
}
