package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IInstructorGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Instructor;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ImagenEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.InstructorEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IInstructorRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;

@Service
public class InstructorGateway implements IInstructorGateway {

    @Autowired
    private IInstructorRepositorio repoInstructor;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @Override
    public List<Instructor> obtenerInstructores() {
        Iterable<InstructorEntidad> entidades = repoInstructor.findAll();
        List<Instructor> instructores = new ArrayList<>();
        instructores = mapper.map(entidades, new TypeToken<List<Instructor>>() {
        }.getType());
        return instructores;
    }

    @Override
    public boolean existeInstructor(String instructorId) {
        return repoInstructor.existsById(instructorId);
    }

    @Override
    public Instructor insertarInstructor(Instructor datosInstructor) {
        InstructorEntidad entidad = mapper.map(datosInstructor, InstructorEntidad.class);
        if (datosInstructor.getPerfil().getPerf_imagen() != null) {
            ImagenEntidad imagen = mapper.map(datosInstructor.getPerfil().getPerf_imagen(), ImagenEntidad.class);
            entidad.getPerfil().setPerf_imagen(imagen);
        }
        InstructorEntidad entidadGuardada = repoInstructor.save(entidad);
        return mapper.map(entidadGuardada, Instructor.class);
    }

    @Override
    public Optional<Instructor> obtenerInstructor(String instructorId) {
        if (existeInstructor(instructorId)) {
            Optional<InstructorEntidad> entidadRecuperada = repoInstructor.findById(instructorId);
            return entidadRecuperada.map(instructorEntidad -> mapper.map(instructorEntidad, Instructor.class));
        }
        return Optional.empty();
    }

    @Override
    public Instructor actualizarInstructor(String instructorId, Instructor datosInstructor) {
        Optional<InstructorEntidad> entidadExistente = repoInstructor.findById(instructorId);
        if (entidadExistente.isPresent()) {
            InstructorEntidad entidad = entidadExistente.get();
            entidad.getPerfil().setPerf_nombre(datosInstructor.getPerfil().getPerf_nombre());
            entidad.getPerfil().setPerfcorreo(datosInstructor.getPerfil().getPerf_correo());
            entidad.getPerfil().setPerf_tipo(datosInstructor.getPerfil().getPerf_tipo());
            entidad.getPerfil().setPerf_Sexo(datosInstructor.getPerfil().getPerf_Sexo());
            if (datosInstructor.getPerfil().getPerf_imagen() != null) {
                ImagenEntidad imagen = mapper.map(datosInstructor.getPerfil().getPerf_imagen(), ImagenEntidad.class);
                entidad.getPerfil().setPerf_imagen(imagen);
            }
            InstructorEntidad actulizado = repoInstructor.save(entidad);
            return mapper.map(actulizado, Instructor.class);
        }
        throw new NoExisteExcepcion();

    }

    @Override
    public Instructor eliminarInstructor(String instructorId) {
        Optional<InstructorEntidad> entidadExistente = repoInstructor.findById(instructorId);
        if (entidadExistente.isPresent()) {
            InstructorEntidad entidad = entidadExistente.get();
            repoInstructor.delete(entidad);
            return mapper.map(entidad, Instructor.class);
        }
        throw new NoExisteExcepcion();
    }

}
