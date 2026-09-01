package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Escenario;

import java.util.List;

public interface IEscenarioServicio {
    List<Escenario> listarEscenarios();
    Escenario obtenerEscenario(Integer id);
    Escenario insertarEscenario(Escenario escenario);
    Escenario actualizarEscenario(Integer id, Escenario escenario);
    Escenario eliminarEscenario(Integer id);
}
