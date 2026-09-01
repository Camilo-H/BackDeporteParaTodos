package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IEscenarioGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Escenario;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.EscenarioEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IEscenarioRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.mappers.EscenarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EscenarioGateway implements IEscenarioGateway {

    @Autowired
    private IEscenarioRepositorio repoEscenario;

    @Override
    public List<Escenario> listarEscenarios() {
        List<EscenarioEntidad> entidades = repoEscenario.findByEliminado(0);
        List<Escenario> escenarios = new ArrayList<>();
        entidades.forEach(entidad -> escenarios.add(EscenarioMapper.toDominio(entidad)));
        return escenarios;
    }

    @Override
    public Escenario obtenerEscenario(Integer id) {
        Optional<EscenarioEntidad> op = repoEscenario.findById(id);
        if (op.isEmpty()) {
            throw new NoExisteExcepcion("El escenario con id " + id + " no existe");
        }
        return EscenarioMapper.toDominio(op.get());
    }

    @Override
    public boolean existeEscenario(Integer id) {
        return repoEscenario.existsById(id);
    }

    @Override
    public boolean existeEscenarioPorNombre(String nombre) {
        return repoEscenario.existsByNombre(nombre);
    }

    @Override
    public Escenario insertarEscenario(Escenario escenario) {
        EscenarioEntidad entidad = EscenarioMapper.toEntidad(escenario);
        entidad.setEliminado(0);
        EscenarioEntidad guardado = repoEscenario.save(entidad);
        return EscenarioMapper.toDominio(guardado);
    }

    @Override
    public Escenario actualizarEscenario(Integer id, Escenario escenario) {
        Optional<EscenarioEntidad> op = repoEscenario.findById(id);
        if (op.isEmpty()) {
            throw new NoExisteExcepcion("El escenario con id " + id + " no existe");
        }
        EscenarioEntidad entidad = op.get();
        entidad.setNombre(escenario.getNombre());
        entidad.setDescripcion(escenario.getDescripcion());
        entidad.setNumTribunas(escenario.getNumTribunas());
        entidad.setDisponible(escenario.isDisponible() ? 1 : 0);
        EscenarioEntidad guardado = repoEscenario.save(entidad);
        return EscenarioMapper.toDominio(guardado);
    }

    @Override
    public Escenario eliminarEscenario(Integer id) {
        Optional<EscenarioEntidad> op = repoEscenario.findById(id);
        if (op.isEmpty()) {
            throw new NoExisteExcepcion("El escenario con id " + id + " no existe");
        }
        EscenarioEntidad entidad = op.get();
        entidad.setEliminado(1);
        EscenarioEntidad guardado = repoEscenario.save(entidad);
        return EscenarioMapper.toDominio(guardado);
    }
}
