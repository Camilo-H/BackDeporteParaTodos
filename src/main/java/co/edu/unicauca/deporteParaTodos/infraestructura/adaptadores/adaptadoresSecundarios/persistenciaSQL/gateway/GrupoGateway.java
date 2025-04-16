package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IGrupoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Grupo;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.GrupoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ImagenEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids.GrupoId;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IGrupoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;

@Service
public class GrupoGateway implements IGrupoGateway {

    @Autowired
    private IGrupoRepositorio repoGrupo;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @Override
    public List<Grupo> obtenerGrupos() {
        Iterable<GrupoEntidad> grupoEntidad = repoGrupo.findAll();
        List<Grupo> grupos = new ArrayList<>();
        grupos = mapper.map(grupoEntidad, new TypeToken<List<Grupo>>() {
        }.getType());
        return grupos;
    }

    @Override
    public boolean existeGrupo(String nombre, int anio, int iterable) {

        GrupoId grupoId = new GrupoId();
        //grupoId.setNombre(nombre);
        grupoId.setAnio(anio);
        grupoId.setIterable(iterable);
        return repoGrupo.existsById(grupoId);
    }

    @Override
    public Grupo insertarGrupo(Grupo datosGrupo) {
        GrupoEntidad entidad = mapper.map(datosGrupo, GrupoEntidad.class);
        if (datosGrupo.getImagen() != null) {
            ImagenEntidad imagen = mapper.map(datosGrupo.getImagen(), ImagenEntidad.class);
            //entidad.setImagenGrupo(imagen);
        }
        GrupoEntidad entidadGuardada = repoGrupo.save(entidad);
        return mapper.map(entidadGuardada, Grupo.class);
    }

    @Override
    public Optional<Grupo> obtenerGrupoPorId(String nombre, int anio, int iterable) {
        GrupoId grupoId = new GrupoId();
        //grupoId.setNombre(nombre);
        grupoId.setAnio(anio);
        grupoId.setIterable(iterable);

        Optional<GrupoEntidad> entidadRecuperada = repoGrupo.findById(grupoId);
        return entidadRecuperada.map(grupoEntidad -> mapper.map(grupoEntidad, Grupo.class));
    }

    @Override
    public Grupo actualizarGrupo(String nombre, int anio, int iterable, Grupo datosGrupo) {

        GrupoId grupoId = new GrupoId();
        //grupoId.setNombre(nombre);
        grupoId.setAnio(anio);
        grupoId.setIterable(iterable);

        Optional<GrupoEntidad> entidadExistente = repoGrupo.findById(grupoId);

        if (entidadExistente.isPresent()) {
            GrupoEntidad entidadActualizar = entidadExistente.get();
            // Actualizar los campos de la entidad con los datos de datosGrupo
            entidadActualizar.setCupos(datosGrupo.getCupos());
            //entidadActualizar.setEstado(datosGrupo.getEstado());
            entidadActualizar.setFechaCreacion(datosGrupo.getFechaCreacion());
            entidadActualizar.setFechaFinalizacion(datosGrupo.getFechaFinalizacion());
            if (datosGrupo.getImagen() != null) {
                ImagenEntidad imagenEntidad = mapper.map(datosGrupo.getImagen(), ImagenEntidad.class);
                //entidadActualizar.setImagenGrupo(imagenEntidad);
            }

            GrupoEntidad grupoActualizado = repoGrupo.save(entidadActualizar);
            return mapper.map(grupoActualizado, Grupo.class);
        }
        throw new NoExisteExcepcion("El grupo con el nombre " + nombre + " no existe");
    }

    @Override
    public Grupo eliminarGrupo(String nombre, int anio, int iterable) {
        GrupoId grupoId = new GrupoId();
        //grupoId.setNombre(nombre);
        grupoId.setAnio(anio);
        grupoId.setIterable(iterable);
        Optional<GrupoEntidad> entidadExistente = repoGrupo.findById(grupoId);

        if (entidadExistente.isPresent()) {
            GrupoEntidad entidad = entidadExistente.get();
            repoGrupo.delete(entidad);
            return mapper.map(entidad, Grupo.class);
        } else {
            throw new NoExisteExcepcion("El grupo con el nombre " + nombre + " no existe");
        }
    }

}
