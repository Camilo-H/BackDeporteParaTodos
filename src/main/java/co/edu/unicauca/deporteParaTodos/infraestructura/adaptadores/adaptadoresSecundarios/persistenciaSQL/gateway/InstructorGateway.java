package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Perfil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IInstructorGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Instructor;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.AlumnoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.InstructorEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.PerfilEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IAlumnoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IInstructorRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IPerfilRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoImplementadoException;

@Service
public class InstructorGateway implements IInstructorGateway {

    @Autowired
    private IInstructorRepositorio repoInstructor;

    @Autowired
    private IPerfilRepositorio repoPerfil;

    @Autowired
    private IAlumnoRepositorio repoAlumno;

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

    // Nueva Implementacion para obtener instructores
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
    }

    @Override
    public Optional<Instructor> obtenerInstructor(String instructorId) {
        if (existeInstructor(instructorId)) {
            Optional<InstructorEntidad> entidadRecuperada = repoInstructor.findById(instructorId);
            return entidadRecuperada.map(this::mapearEntidadADominio);
        }
        return Optional.empty();
    }

    @Override
    public Instructor actualizarInstructor(String instructorId, Instructor datosInstructor) {
        throw new NoImplementadoException();
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

    @Override
    public Instructor registrarInstructor(Perfil perfil, String tipoAlumno) {
        // [1] Crear perfil
        PerfilEntidad entidadPerfil = PerfilEntidad.fabricarDeModelo(perfil, 0);
        PerfilEntidad perfilGuardado = repoPerfil.save(entidadPerfil);
        String perfilId = perfilGuardado.getPerf_id();

        // [2] Registrar como alumno (vinculación al programa) - usando solo el ID
        AlumnoEntidad entidadAlumno = new AlumnoEntidad();
        entidadAlumno.setIdPerfil(perfilId);
        entidadAlumno.setTipoAlumno(tipoAlumno);
        entidadAlumno.setEliminado(0);
        repoAlumno.save(entidadAlumno);

        // [3] Registrar como instructor - usando solo el ID
        InstructorEntidad entidadInstructor = new InstructorEntidad();
        entidadInstructor.setIdPerfil(perfilId);
        entidadInstructor.setEliminado(0);
        InstructorEntidad instructorGuardado = repoInstructor.save(entidadInstructor);

        // Construir objeto de dominio para retornar
        Instructor instructorRegistrado = new Instructor();
        instructorRegistrado.setInst_codigo(instructorGuardado.getIdPerfil());
        instructorRegistrado.setPerfil(Perfil.fabricarDeEntidad(perfilGuardado));
        instructorRegistrado.getPerfil().setTipoAlumno(tipoAlumno);

        return instructorRegistrado;
    }

}
