package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IHorarioServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IHorarioGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Horario;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.HorarioDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.YaExisteElementoExcepcion;

@Service
public class HorarioServicio implements IHorarioServicio {

    @Autowired
    private IHorarioGateway horarioGateway;

    @Override
    public List<HorarioDto> listarHorariosPorGrupo(String categoria, String curso, int anio, int iterable) {
        List<Horario> horarios = horarioGateway.listarHorariosPorGrupo(categoria, curso, anio, iterable);
        if (horarios.isEmpty()) {
            throw new ListadoVacioExcepcion("No hay horarios registrados para el grupo indicado");
        }
        List<HorarioDto> dtos = new ArrayList<>();
        horarios.forEach(h -> dtos.add(HorarioDto.fabricarDeModelo(h)));
        return dtos;
    }

    @Override
    public HorarioDto obtenerHorario(Integer id) {
        if (!horarioGateway.existeHorario(id)) {
            throw new NoExisteExcepcion("El horario con id " + id + " no existe");
        }
        return HorarioDto.fabricarDeModelo(horarioGateway.obtenerHorario(id));
    }

    @Override
    public HorarioDto insertarHorario(HorarioDto datosHorario) {
        Horario horario = Horario.fabricarDeDto(datosHorario);
        Horario guardado = horarioGateway.insertarHorario(horario);
        return HorarioDto.fabricarDeModelo(guardado);
    }

    @Override
    public HorarioDto actualizarHorario(Integer id, HorarioDto datosHorario) {
        if (!horarioGateway.existeHorario(id)) {
            throw new NoExisteExcepcion("El horario con id " + id + " no existe");
        }
        Horario horario = Horario.fabricarDeDto(datosHorario);
        Horario actualizado = horarioGateway.actualizarHorario(id, horario);
        return HorarioDto.fabricarDeModelo(actualizado);
    }

    @Override
    public HorarioDto eliminarHorario(Integer id) {
        if (!horarioGateway.existeHorario(id)) {
            throw new NoExisteExcepcion("El horario con id " + id + " no existe");
        }
        Horario actual = horarioGateway.obtenerHorario(id);
        if (actual.getEliminado() != null && actual.getEliminado() == 1) {
            throw new YaExisteElementoExcepcion("El horario ya se encuentra eliminado");
        }
        Horario eliminado = horarioGateway.eliminarHorario(id);
        return HorarioDto.fabricarDeModelo(eliminado);
    }
}
