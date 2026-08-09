package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida;

import java.util.List;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Horario;

public interface IHorarioGateway {

    boolean existeHorario(Integer id);

    List<Horario> listarHorariosPorGrupo(String categoria, String curso, int anio, int iterable);

    Horario obtenerHorario(Integer id);

    Horario insertarHorario(Horario datosHorario);

    Horario actualizarHorario(Integer id, Horario datosHorario);

    Horario eliminarHorario(Integer id);
}
