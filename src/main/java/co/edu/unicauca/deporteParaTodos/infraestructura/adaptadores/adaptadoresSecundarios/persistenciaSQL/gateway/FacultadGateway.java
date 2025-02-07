package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IFacultadGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Facultad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.FacultadEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IFacultadRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;

@Service
public class FacultadGateway implements IFacultadGateway {

    @Autowired
    private IFacultadRepositorio repoFacultad;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @Override
    public boolean existeFacultad(String nombre) {
        return repoFacultad.existsById(nombre);
    }

    @Override
    public List<Facultad> obtenerFacultades() {
        Iterable<FacultadEntidad> entidades = repoFacultad.findAll();
        List<Facultad> facultades = mapper.map(entidades, new TypeToken<List<Facultad>>() {
        }.getType());
        return facultades;
    }

    @Override
    public Optional<Facultad> obtenerFacultad(String nombre) {
        if (existeFacultad(nombre)) {
            Optional<FacultadEntidad> entidadRecuperada = repoFacultad.findById(nombre);
            return entidadRecuperada.map(facultadEntidad -> mapper.map(facultadEntidad, Facultad.class));
        }
        return Optional.empty();
    }

    @Override
    public Facultad insertarFacultad(Facultad datosFacultad) {
        FacultadEntidad nuevaFacultad = mapper.map(datosFacultad, FacultadEntidad.class);
        FacultadEntidad facInsertada = repoFacultad.save(nuevaFacultad);
        return mapper.map(facInsertada, Facultad.class);
    }

    @Override
    public Facultad eliminarFacultad(String nombre) {
        if (existeFacultad(nombre)) {
            Optional<FacultadEntidad> facultadExistente = repoFacultad.findById(nombre);
            FacultadEntidad entidad = facultadExistente.get();
            repoFacultad.delete(entidad);
            return mapper.map(entidad, Facultad.class);
        }
        throw new NoExisteExcepcion();
    }

}
