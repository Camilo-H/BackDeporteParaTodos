package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IPerfilGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Perfil;
import co.edu.unicauca.deporteParaTodos.dominio.servicios.valores.Roles;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.AlumnoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.PerfilEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IAlumnoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICoordinadorRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IImagenRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IInstructorRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IPerfilRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.DependenciaFallida;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoConvertibleException;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.YaExisteElementoExcepcion;

@Service
public class PerfilGateway implements IPerfilGateway {

    @Autowired
    private IPerfilRepositorio repoPerfil;

    @Autowired
    private ICoordinadorRepositorio repoCoordinador;

    @Autowired
    private IInstructorRepositorio repoInstructor;
    
    @Autowired
    private IAlumnoRepositorio repoAlumno;

    @Autowired
    private IImagenRepositorio repoImagen;

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
        //verificar insercion
        if(existePerfil(perfil.getId())){
            throw new YaExisteElementoExcepcion("El perfil con la identificacion ya se encuentra registrado");
        }

        //verificar imagen
        if(!repoImagen.existsById(perfil.getImagen())){
            throw new DependenciaFallida("la imgen no se encuentra registrada");
        }

        //conversion de datos
        PerfilEntidad entidadInsertar = PerfilEntidad.fabricarDeModelo(perfil, 0);
        if(entidadInsertar==null){
            throw new NoConvertibleException();
        }

        //insercion
        PerfilEntidad guardado = repoPerfil.save(entidadInsertar);

        //conversion
        Perfil perfilCreado = Perfil.fabricarDeEntidad(guardado);
        if(perfilCreado == null){
            throw new InternalError("No se ha logrado retornar la respuesta desde gateway");
        }
        
        return perfilCreado;
    }

    @Override
    public Perfil registrarAlumno(Perfil perfil) {
        if (existePerfil(perfil.getId())) {
            throw new YaExisteElementoExcepcion("El perfil con la identificacion ya se encuentra registrado");
        }

        PerfilEntidad entidadPerfil = PerfilEntidad.fabricarDeModelo(perfil, 0);
        if (entidadPerfil == null) {
            throw new NoConvertibleException();
        }

        AlumnoEntidad entidadAlumno = AlumnoEntidad.fabricarDePerfil(perfil, 0);
        if (entidadAlumno == null) {
            throw new NoConvertibleException();
        }

        PerfilEntidad perfilGuardado = repoPerfil.save(entidadPerfil);
        AlumnoEntidad alumnoGuardado = repoAlumno.save(entidadAlumno);

        if (perfilGuardado == null || alumnoGuardado == null) {
            throw new InternalError("No se ha logrado registrar el alumno");
        }

        Perfil perfilRegistrado = Perfil.fabricarDeEntidad(perfilGuardado);
        if (perfilRegistrado == null) {
            throw new NoConvertibleException();
        }
        perfilRegistrado.setRol(Roles.ALUMNO.getValor());
        perfilRegistrado.setTipoAlumno(alumnoGuardado.getTipoAlumno());
        perfilRegistrado.setFacultad(null);
        return perfilRegistrado;
    }

    @Override
    public Optional<Perfil> obtenerPerfil(String perfilId) {
        Optional<PerfilEntidad> entidadRecuperada = repoPerfil.findById(perfilId);
        return entidadRecuperada.map(perfilEndidad -> mapper.map(perfilEndidad, Perfil.class));
    }

    @Override
    public Perfil actualizarPerfil(String perfilId, Perfil datosPerfil) {
        //confirmar existencia de registro
        if (!existePerfil(perfilId)) {
            throw new NoExisteExcepcion("No existe el perfil con el identificador " + perfilId);
        }
        
        //obtener datos
        PerfilEntidad entidadExistente = repoPerfil.findById(perfilId)
                .orElseThrow(() -> new NoExisteExcepcion("No exoste el perfil"));

        entidadExistente.setPerf_nombre(datosPerfil.getNombre());
        entidadExistente.setPerfcorreo(datosPerfil.getCorreo());
        //solo actualizar imagen en caso de existir dicha informacion
        if(datosPerfil.getImagen()!=null){
            if(!repoImagen.existsById(datosPerfil.getImagen())){
                throw new DependenciaFallida("la imagen a actualizar no existe");
            }
            entidadExistente.setPerf_imagen(datosPerfil.getImagen());
        }
        // solo actualizar tipo e id si vienen informados (null = no modificar)
        if (datosPerfil.getTipoId() != null) {
            entidadExistente.setPerf_tipo(datosPerfil.getTipoId());
        }
        if (datosPerfil.getSexo() != null) {
            entidadExistente.setPerf_Sexo(datosPerfil.getSexo());
        }
        entidadExistente.setEliminado(0);

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

    @Override
    public Perfil obtenerUsuario(String email) {
        List<PerfilEntidad> perfiles = repoPerfil.findByPerfcorreo(email);
        if(perfiles.size()>0){
            PerfilEntidad entidad = perfiles.get(0);
            Perfil perfil = Perfil.fabricarDeEntidad(entidad);
            String id = perfil.getId();
            if(repoCoordinador.existsById(id)){
                perfil.setRol(Roles.ADMINISTRADOR.getValor());
                return perfil;
            }
            if(repoInstructor.existsById(id)){
                perfil.setRol(Roles.INSTRUCTOR.getValor());
                return perfil;
            }
            if(repoAlumno.existsById(id)){
                AlumnoEntidad alumno = repoAlumno.findById(id).orElse(null);
                perfil.setRol(Roles.ALUMNO.getValor());
                if(alumno != null){
                    perfil.setTipoAlumno(alumno.getTipoAlumno());
                }
                perfil.setFacultad(repoAlumno.obtenerFacultadPorPerfilId(id));
                return perfil;
            }
        }
        return null;
    }

}
