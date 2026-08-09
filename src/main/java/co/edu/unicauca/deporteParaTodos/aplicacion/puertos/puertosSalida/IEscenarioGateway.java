package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Escenario;

import java.util.List;

public interface IEscenarioGateway {
    List<Escenario> listarEscenarios();
    Escenario obtenerEscenario(Integer id);
    boolean existeEscenario(Integer id);
    boolean existeEscenarioPorNombre(String nombre);
    Escenario insertarEscenario(Escenario escenario);
    Escenario actualizarEscenario(Integer id, Escenario escenario);
    Escenario eliminarEscenario(Integer id);
}
