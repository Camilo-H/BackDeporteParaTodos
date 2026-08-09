package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.EscenarioDto;

import java.util.List;

public interface IEscenarioServicio {
    List<EscenarioDto> listarEscenarios();
    EscenarioDto obtenerEscenario(Integer id);
    EscenarioDto insertarEscenario(EscenarioDto dto);
    EscenarioDto actualizarEscenario(Integer id, EscenarioDto dto);
    EscenarioDto eliminarEscenario(Integer id);
}
