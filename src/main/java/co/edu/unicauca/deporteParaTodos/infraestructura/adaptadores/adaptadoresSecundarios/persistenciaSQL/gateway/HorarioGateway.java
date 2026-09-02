package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IHorarioGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Horario;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.HorarioEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IHorarioRepositorio;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.mappers.HorarioMapper;

@Service
public class HorarioGateway implements IHorarioGateway {

    @Autowired
    private IHorarioRepositorio repoHorario;

    @Override
    public boolean existeHorario(Integer id) {
        return repoHorario.existsById(id);
    }

    @Override
    public List<Horario> listarHorariosPorGrupo(String categoria, String curso, int anio, int iterable) {
        List<HorarioEntidad> entidades = repoHorario
                .findByCategoriaAndCursoAndAnioAndIterableAndEliminado(categoria, curso, anio, iterable, 0);
        List<Horario> horarios = new ArrayList<>();
        entidades.forEach(e -> horarios.add(HorarioMapper.toDominio(e)));
        return horarios;
    }

    @Override
    public Horario obtenerHorario(Integer id) {
        Optional<HorarioEntidad> op = repoHorario.findById(id);
        if (op.isEmpty()) {
            throw new NoExisteExcepcion("El horario con id " + id + " no existe");
        }
        return HorarioMapper.toDominio(op.get());
    }

    @Override
    public Horario insertarHorario(Horario datosHorario) {
        HorarioEntidad entidad = HorarioMapper.toEntidad(datosHorario);
        entidad.setEliminado(0);
        HorarioEntidad guardado = repoHorario.save(entidad);
        return HorarioMapper.toDominio(guardado);
    }

    @Override
    public Horario actualizarHorario(Integer id, Horario datosHorario) {
        Optional<HorarioEntidad> op = repoHorario.findById(id);
        if (op.isEmpty()) {
            throw new NoExisteExcepcion("El horario con id " + id + " no existe");
        }
        HorarioEntidad entidad = op.get();
        entidad.setDia(datosHorario.getDia());
        entidad.setHoraInicio(datosHorario.getHoraInicio());
        entidad.setHoraFin(datosHorario.getHoraFin());
        entidad.setEscenario(datosHorario.getEscenario());
        HorarioEntidad guardado = repoHorario.save(entidad);
        return HorarioMapper.toDominio(guardado);
    }

    @Override
    public Horario eliminarHorario(Integer id) {
        Optional<HorarioEntidad> op = repoHorario.findById(id);
        if (op.isEmpty()) {
            throw new NoExisteExcepcion("El horario con id " + id + " no existe");
        }
        HorarioEntidad entidad = op.get();
        entidad.setEliminado(1);
        HorarioEntidad guardado = repoHorario.save(entidad);
        return HorarioMapper.toDominio(guardado);
    }
}
