package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Horario;

public interface IHorarioServicio {

    List<Horario> listarHorariosPorGrupo(String categoria, String curso, int anio, int iterable);

    Horario obtenerHorario(Integer id);

    Horario insertarHorario(Horario datosHorario);

    Horario actualizarHorario(Integer id, Horario datosHorario);

    Horario eliminarHorario(Integer id);
}
