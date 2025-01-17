package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IHorarioGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Horario;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.HorarioEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IHorarioRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;

@Service
public class HorarioGateway implements IHorarioGateway {

    @Autowired
    private IHorarioRepositorio repoHorario;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @Override
    public boolean existeHorario(int id) {
        return repoHorario.existsById(id);
    }

    @Override
    public List<Horario> obtenerHorarios() {
        Iterable<HorarioEntidad> entidades = repoHorario.findAll();
        List<Horario> horarios = new ArrayList<>();
        horarios = mapper.map(entidades, new TypeToken<List<Horario>>() {
        }.getType());
        return horarios;
    }

    @Override
    public Optional<Horario> obtenerHorario(int id) {
        Optional<HorarioEntidad> entidad = repoHorario.findById(id);
        if (entidad.isPresent()) {
            return entidad.map(horarioEntidad -> mapper.map(horarioEntidad, Horario.class));
        }
        return Optional.empty();
    }

    @Override
    public Horario insertatarHorario(Horario datosHorario) {
        HorarioEntidad entidad = mapper.map(datosHorario, HorarioEntidad.class);
        HorarioEntidad nuevoHorario = repoHorario.save(entidad);
        return mapper.map(nuevoHorario, Horario.class);
    }

    @Override
    public Horario actualizarHorario(int id, Horario datosHorario) {
        if (existeHorario(id)) {
            HorarioEntidad entidad = mapper.map(datosHorario, HorarioEntidad.class);
            HorarioEntidad horarioActualizado = repoHorario.save(entidad);
            return mapper.map(horarioActualizado, Horario.class);
        }
        throw new NoExisteExcepcion();
    }

    @Override
    public Horario eliminarHorario(int id) {
        Optional<HorarioEntidad> entidadExistente = repoHorario.findById(null);
        HorarioEntidad respuesta = entidadExistente.get();
        repoHorario.delete(respuesta);
        return mapper.map(respuesta, Horario.class);
    }

}
