package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IHorarioServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IHorarioGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Horario;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;

@Service
public class HorarioServicio implements IHorarioServicio {

    @Autowired
    private IHorarioGateway horarioGateway;

    @Override
    public List<Horario> obtenerHorarios() {
        List<Horario> horarios = horarioGateway.obtenerHorarios();
        if (horarios.isEmpty()) {
            throw new ListadoVacioExcepcion("No hay horarios registrados");
        }
        return horarios;
    }

    @Override
    public Horario obtenerHorario(int id) {
        return horarioGateway.obtenerHorario(id).orElseThrow(() -> new NoExisteExcepcion());
    }

    @Override
    public Horario insertatarHoratio(Horario datosHorario) {
        return horarioGateway.insertatarHorario(datosHorario);
    }

    @Override
    public Horario actualizarHorario(int id, Horario datosHorario) {
        if (!horarioGateway.existeHorario(id)) {
            throw new NoExisteExcepcion();
        }
        return horarioGateway.actualizarHorario(id, datosHorario);
    }

    @Override
    public Horario eliminarHorario(int id) {
        if (!horarioGateway.existeHorario(id)) {
            throw new NoExisteExcepcion();
        }
        return horarioGateway.eliminarHorario(id);
    }

}
