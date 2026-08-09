package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.HorarioDto;

public interface IHorarioServicio {

    List<HorarioDto> listarHorariosPorGrupo(String categoria, String curso, int anio, int iterable);

    HorarioDto obtenerHorario(Integer id);

    HorarioDto insertarHorario(HorarioDto datosHorario);

    HorarioDto actualizarHorario(Integer id, HorarioDto datosHorario);

    HorarioDto eliminarHorario(Integer id);
}
