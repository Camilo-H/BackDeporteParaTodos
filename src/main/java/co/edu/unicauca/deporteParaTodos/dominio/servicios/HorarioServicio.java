package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IHorarioServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IHorarioGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Horario;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.YaExisteElementoExcepcion;

@Service
public class HorarioServicio implements IHorarioServicio {

    @Autowired
    private IHorarioGateway horarioGateway;

    @Override
    public List<Horario> listarHorariosPorGrupo(String categoria, String curso, int anio, int iterable) {
        List<Horario> horarios = horarioGateway.listarHorariosPorGrupo(categoria, curso, anio, iterable);
        if (horarios.isEmpty()) {
            throw new ListadoVacioExcepcion("No hay horarios registrados para el grupo indicado");
        }
        return horarios;
    }

    @Override
    public Horario obtenerHorario(Integer id) {
        if (!horarioGateway.existeHorario(id)) {
            throw new NoExisteExcepcion("El horario con id " + id + " no existe");
        }
        return horarioGateway.obtenerHorario(id);
    }

    @Override
    public Horario insertarHorario(Horario datosHorario) {
        return horarioGateway.insertarHorario(datosHorario);
    }

    @Override
    public Horario actualizarHorario(Integer id, Horario datosHorario) {
        if (!horarioGateway.existeHorario(id)) {
            throw new NoExisteExcepcion("El horario con id " + id + " no existe");
        }
        return horarioGateway.actualizarHorario(id, datosHorario);
    }

    @Override
    public Horario eliminarHorario(Integer id) {
        if (!horarioGateway.existeHorario(id)) {
            throw new NoExisteExcepcion("El horario con id " + id + " no existe");
        }
        Horario actual = horarioGateway.obtenerHorario(id);
        if (actual.getEliminado() != null && actual.getEliminado() == 1) {
            throw new YaExisteElementoExcepcion("El horario ya se encuentra eliminado");
        }
        return horarioGateway.eliminarHorario(id);
    }
}
