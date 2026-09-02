package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Coordinador;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.ICoordinadorGateway;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CoordinadorEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICoordinadorRepositorio;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;

@Service
public class CoordinadorGateway implements ICoordinadorGateway {

    @Autowired
    private ICoordinadorRepositorio repoCoordinador;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @Override
    public boolean existeCoordinador(String coordId) {
        return repoCoordinador.existsById(coordId);
    }

    @Override
    public List<Coordinador> obtenerCoordinadores() {
        Iterable<CoordinadorEntidad> entidades = repoCoordinador.findAll();
        List<Coordinador> lista = new ArrayList<>();
        lista = mapper.map(entidades, new TypeToken<List<Coordinador>>() {
        }.getType());
        return lista;
    }

    @Override
    public Coordinador insertarCoordinador(Coordinador datosCoordinador) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertarCoordinador'");
    }

    @Override
    public Optional<Coordinador> obtenerCoordinador(String coorId) {
        if (existeCoordinador(coorId)) {
            Optional<CoordinadorEntidad> coordExistente = repoCoordinador.findById(coorId);
            return coordExistente.map(coordinadorEntidad -> mapper.map(coordinadorEntidad, Coordinador.class));
        }
        throw new NoExisteExcepcion();
    }

    @Override
    public Coordinador actualizarCoordinador(String coordId, Coordinador datosCoordinador) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizarCoordinador'");
    }

    @Override
    public Coordinador eliminarCoordinador(String coordId) {
        Optional<CoordinadorEntidad> entidadExistente = repoCoordinador.findById(coordId);
        if (entidadExistente.isPresent()) {
            CoordinadorEntidad entidad = entidadExistente.get();
            repoCoordinador.delete(entidad);
            return mapper.map(entidad, Coordinador.class);
        }
        throw new NoExisteExcepcion();
    }

}
