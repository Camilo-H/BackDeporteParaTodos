package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IAlumnoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Alumno;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Perfil;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.AlumnoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IAlumnoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;

@Service
public class AlumnoGateway implements IAlumnoGateway {

    @Autowired
    private IAlumnoRepositorio repoAlumno;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @Override
    public boolean existeAlumno(String alumnoId) {
        return repoAlumno.existsById(alumnoId);
    }

    @Override
    public List<Alumno> obtenerAlumnos() {
        Iterable<AlumnoEntidad> entidades = repoAlumno.findAll();
        List<Alumno> alumnos = new ArrayList<>();
        alumnos = mapper.map(entidades, new TypeToken<List<Alumno>>() {
        }.getType());
        return alumnos;
    }

    @Override
    public List<Alumno> obtenerAlumnosGrupo(String categoria, String curso, Integer anio, Integer iterable) {
        List<Object[]> resultados = repoAlumno.buscarAlumnosGrupoRaw(categoria, curso, anio, iterable, 0);
        List<Alumno> alumnos = new ArrayList<>();
        resultados.forEach(registro -> {
            Alumno alumno = construirAlumnoDesdeRegistro(registro);
            if (alumno != null) {
                alumnos.add(alumno);
            }
        });
        return alumnos;
    }

    @Override
    public Alumno insertAlumno(Alumno datosAlumno) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertAlumno'");
    }

    @Override
    public Optional<Alumno> obtenerAlumno(String alumnoId) {
        if (existeAlumno(alumnoId)) {
            Object[] registro = repoAlumno.buscarAlumnoPorIdRaw(alumnoId);
            Alumno alumno = construirAlumnoDesdeRegistro(registro);
            return Optional.ofNullable(alumno);
        }
        return Optional.empty();
    }

    @Override
    public Alumno actualizarAlumno(String alumnoId, Alumno datosAlumno) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizarAlumno'");
    }

    @Override
    public Alumno eliminarAlumno(String alumnoId) {
        Optional<AlumnoEntidad> entidadRecuperada = repoAlumno.findById(alumnoId);
        if (entidadRecuperada.isPresent()) {
            AlumnoEntidad entidad = entidadRecuperada.get();
            repoAlumno.delete(entidad);
            return mapper.map(entidad, Alumno.class);
        }
        throw new NoExisteExcepcion();
    }

    private Alumno construirAlumnoDesdeRegistro(Object[] registro) {
        if (registro == null) {
            return null;
        }
        Perfil perfil = new Perfil();
        perfil.setId((String) registro[1]);
        perfil.setNombre((String) registro[4]);
        perfil.setCorreo((String) registro[5]);
        perfil.setSexo((String) registro[6]);
        perfil.setTipoId((String) registro[7]);
        perfil.setImagen(registro[8] != null ? ((Number) registro[8]).intValue() : null);

        Alumno alumno = new Alumno();
        alumno.setEliminadoestado(registro[0] != null ? ((Number) registro[0]).intValue() : null);
        alumno.setAlm_codigo((String) registro[2]);
        alumno.setTipoAlumno((String) registro[3]);
        alumno.setPerfil(perfil);
        return alumno;
    }

}
