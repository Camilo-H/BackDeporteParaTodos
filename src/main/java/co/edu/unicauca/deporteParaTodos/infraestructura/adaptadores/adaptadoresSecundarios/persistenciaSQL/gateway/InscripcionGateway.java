package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IInscripcionGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Inscripcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.InscripcionEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IInscripcionRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoImplementadoException;
import jakarta.servlet.UnavailableException;

@Service
public class InscripcionGateway implements IInscripcionGateway {

    @Autowired
    private IInscripcionRepositorio repoInscrp;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    public boolean existeInscripcion(Timestamp fecha) throws Exception{
        //TODO: modificar por cambio en base
        //return false;
        throw new NoImplementadoException();
        //return repoInscrp.existsById(fecha);
    }

    @Override
    public List<Inscripcion> obtenerInscripciones() {
        Iterable<InscripcionEntidad> entidades = repoInscrp.findAll();
        List<Inscripcion> lista = mapper.map(entidades, new TypeToken<List<Inscripcion>>() {
        }.getType());
        return lista;
    }

    @Override
    public Optional<Inscripcion> obteneInscripcion(Timestamp fecha) {
        throw new NoImplementadoException();
        /*if (existeInscripcion(fecha)) {
            Optional<InscripcionEntidad> entidad = repoInscrp.findById(fecha);
            return entidad.map(inscripcionEntidad -> mapper.map(inscripcionEntidad, Inscripcion.class));
        }
        return Optional.empty();*/
    }

    @Override
    public Inscripcion insertarInscripcion(Inscripcion datosInscripcion) {
        InscripcionEntidad entidad = mapper.map(datosInscripcion, InscripcionEntidad.class);
        InscripcionEntidad insertada = repoInscrp.save(entidad);
        return mapper.map(insertada, Inscripcion.class);
    }

    @Override
    public Inscripcion eliminarInscripcion(Timestamp fecha) {
        throw new NoImplementadoException();
        /*Optional<InscripcionEntidad> entidadExistente = repoInscrp.findById(fecha);
        if (entidadExistente.isPresent()) {
            InscripcionEntidad entidad = entidadExistente.get();
            repoInscrp.delete(entidad);
            return mapper.map(entidad, Inscripcion.class);
        }
        Inscripcion retorno = null;
        return retorno;*/
    }

}
