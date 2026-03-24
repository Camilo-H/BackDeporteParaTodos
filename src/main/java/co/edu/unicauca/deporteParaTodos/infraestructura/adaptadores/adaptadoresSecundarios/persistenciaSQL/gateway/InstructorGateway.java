package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Perfil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IInstructorGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Instructor;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Perfil;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.InstructorEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IInstructorRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoImplementadoException;

@Service
public class InstructorGateway implements IInstructorGateway {

    @Autowired
    private IInstructorRepositorio repoInstructor;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    // ── Mapeo manual Entidad → Dominio ──────────────────────────────────────
    private Instructor mapearEntidadADominio(InstructorEntidad entidad) {
        Instructor instructor = new Instructor();
        instructor.setInst_codigo(entidad.getIdPerfil());

        if (entidad.getPerfil() != null) {
            instructor.setPerfil(Perfil.fabricarDeEntidad(entidad.getPerfil()));
        }

        return instructor;
    }

   /* @Override
    public List<Instructor> obtenerInstructores() {
        List<Instructor> instructores = new ArrayList<>();
        List<Object[]> resultados = repoInstructor.buscarInstructoresRaw(0);
        resultados.forEach(registro -> {
            Perfil perfil = new Perfil();
            perfil.setId((String) registro[0]);
            perfil.setNombre((String) registro[1]);
            perfil.setCorreo((String) registro[2]);
            perfil.setSexo((String) registro[3]);

            Instructor instructor = new Instructor();
            instructor.setPerfil(perfil);
            instructores.add(instructor);
        });
        return instructores;
    }*/
    //Nueva Implementacion para obtener instructores
   @Override
   public List<Instructor> obtenerInstructores() {
       List<Instructor> lista = new ArrayList<>();
       repoInstructor.findAll()
               .forEach(entidad -> lista.add(mapearEntidadADominio(entidad)));
       return lista;
   }


    @Override
    public boolean existeInstructor(String instructorId) {
        return repoInstructor.existsById(instructorId);
    }

    @Override
    public Instructor insertarInstructor(Instructor datosInstructor) {
        throw new NoImplementadoException();
        /* InstructorEntidad entidad = mapper.map(datosInstructor, InstructorEntidad.class);
        if (datosInstructor.getPerfil().getPerf_imagen() != null) {
            ImagenEntidad imagen = mapper.map(datosInstructor.getPerfil().getPerf_imagen(), ImagenEntidad.class);
            entidad.getPerfil().setPerf_imagen(imagen);
        }
        InstructorEntidad entidadGuardada = repoInstructor.save(entidad);
        return mapper.map(entidadGuardada, Instructor.class); */
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
        throw new NoImplementadoException();
        /* Optional<InstructorEntidad> entidadExistente = repoInstructor.findById(instructorId);
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
        throw new NoExisteExcepcion(); */

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
