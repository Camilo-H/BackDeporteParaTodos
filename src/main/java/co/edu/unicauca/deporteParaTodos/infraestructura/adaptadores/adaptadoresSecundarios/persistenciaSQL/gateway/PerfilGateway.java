package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IperfilGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Perfil;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ImagenEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.PerfilEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IPerfilRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;

@Service
public class PerfilGateway implements IperfilGateway {

    @Autowired
    private IPerfilRepositorio repoPerfil;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @Override
    public boolean existePerfil(String perfilId) {
        return repoPerfil.existsById(perfilId);
    }

    @Override
    public List<Perfil> obtenerPerfiles() {
        Iterable<PerfilEntidad> respuesta = repoPerfil.findAll();
        List<Perfil> perfiles = new ArrayList<>();
        perfiles = mapper.map(respuesta, new TypeToken<List<Perfil>>() {
        }.getType());
        return perfiles;
    }

    @Override
    public Perfil insertarPerfil(Perfil perfil) {
        PerfilEntidad entidad = mapper.map(perfil, PerfilEntidad.class);
        if (perfil.getPerf_imagen() != null) {
            ImagenEntidad imagen = mapper.map(perfil.getPerf_imagen(), ImagenEntidad.class);
            entidad.setPerf_imagen(imagen);
        }
        PerfilEntidad entidadGuardad = repoPerfil.save(entidad);
        Perfil respuesta = mapper.map(entidadGuardad, Perfil.class);
        return respuesta;
    }

    @Override
    public Optional<Perfil> obtenerPerfil(String perfilId) {
        Optional<PerfilEntidad> entidadRecuperada = repoPerfil.findById(perfilId);
        return entidadRecuperada.map(perfilEndidad -> mapper.map(perfilEndidad, Perfil.class));
    }

    @Override
    public Perfil actualizarPerfil(String perfilId, Perfil datosPerfil) {
        if (!existePerfil(perfilId)) {
            throw new NoExisteExcepcion("No existe el perfil con el identificador " + perfilId);
        }
        PerfilEntidad entidadExistente = repoPerfil.findById(perfilId)
                .orElseThrow(() -> new NoExisteExcepcion("No exoste el perfil"));

        entidadExistente.setPerf_nombre(datosPerfil.getPerf_nombre());
        entidadExistente.setPerf_correo(datosPerfil.getPerf_correo());
        if (datosPerfil.getPerf_imagen() != null) {
            ImagenEntidad perfimagen = mapper.map(datosPerfil.getPerf_imagen(), ImagenEntidad.class);
            entidadExistente.setPerf_imagen(perfimagen);
        }
        entidadExistente.setPerf_tipo(datosPerfil.getPerf_tipo());
        entidadExistente.setPerf_Sexo(datosPerfil.getPerf_Sexo());

        PerfilEntidad perfilActualizado = repoPerfil.save(entidadExistente);
        return mapper.map(perfilActualizado, Perfil.class);
    }

    @Override
    public Perfil eliminarPerfil(String perfilId) {
        Optional<PerfilEntidad> entidadExistente = repoPerfil.findById(perfilId);
        if (entidadExistente.isPresent()) {
            PerfilEntidad entidad = entidadExistente.get();
            repoPerfil.delete(entidad);
            return mapper.map(entidad, Perfil.class);
        }
        throw new NoExisteExcepcion("No existe el perfil con el identificador " + perfilId);
    }

}
