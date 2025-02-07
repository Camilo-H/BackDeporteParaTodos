package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IClaseGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Clase;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ClaseEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IClaseRepositorio;

@Service
public class ClaseGateway implements IClaseGateway {

    @Autowired
    private IClaseRepositorio repoClase;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @Override
    public boolean existeClase(int id) {
        return repoClase.existsById(id);
    }

    @Override
    public List<Clase> obtenerClases() {
        Iterable<ClaseEntidad> entidades = repoClase.findAll();
        List<Clase> listado = new ArrayList<>();
        listado = mapper.map(entidades, new TypeToken<List<Clase>>() {
        }.getType());
        return listado;
    }

    @Override
    public Optional<Clase> obtenerClase(int id) {
        if (existeClase(id)) {
            Optional<ClaseEntidad> entidad = repoClase.findById(id);
            return entidad.map(claseEntidad -> mapper.map(claseEntidad, Clase.class));
        }
        return Optional.empty();
    }

    @Override
    public Clase insertarClase(Clase datoClase) {
        ClaseEntidad entidad = mapper.map(datoClase, ClaseEntidad.class);
        ClaseEntidad claseInsertada = repoClase.save(entidad);
        return mapper.map(claseInsertada, Clase.class);
    }

    @Override
    public Clase actualizarClase(int id, Clase datoClase) {
        ClaseEntidad entidad = mapper.map(datoClase, ClaseEntidad.class);
        ClaseEntidad claseActualizada = repoClase.save(entidad);
        return mapper.map(claseActualizada, Clase.class);
    }

    @Override
    public Clase eliminarClase(int id) {
        Optional<ClaseEntidad> entidad = repoClase.findById(id);
        ClaseEntidad entidadEliminada = entidad.get();
        repoClase.delete(entidadEliminada);
        return mapper.map(entidadEliminada, Clase.class);

    }

}
