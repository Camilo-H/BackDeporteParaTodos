package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IInscripcionServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.ICursoGateway;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IGrupoGateway;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IInscripcionGateway;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.CuposAgotadosExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.InscripcionesCerradasExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.LimiteCursosExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Curso;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.EstadoInscripciones;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Grupo;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Inscripcion;

@Service
public class InscripcionServicio implements IInscripcionServicio {

    @Autowired
    private IInscripcionGateway gateway;

    @Autowired
    private ICursoGateway cursoGateway;

    @Autowired
    private IGrupoGateway grupoGateway;

    @Value("${inscripciones.limite-cursos-alumno:3}")
    private int limiteCursosAlumno;

    @Override
    @Transactional
    public Inscripcion inscribir(Inscripcion datos) {
        Curso curso = cursoGateway.obtenerCurso(datos.getCategoria(), datos.getCurso());
        if (curso == null || !EstadoInscripciones.ABIERTO.equals(curso.getEstadoInscripciones())) {
            throw new InscripcionesCerradasExcepcion(
                    "Las inscripciones para el curso " + datos.getCurso() + " estan cerradas");
        }

        Grupo grupo = grupoGateway.obtenerGrupoConLock(
                datos.getCategoria(), datos.getCurso(), datos.getAnio(), datos.getIterable());
        if (grupo.getCupos() == null) {
            throw new CuposAgotadosExcepcion("El grupo no tiene cupos configurados");
        }
        long inscritos = gateway.contarInscripcionesActivasGrupo(
                datos.getCategoria(), datos.getCurso(), datos.getAnio(), datos.getIterable());
        if (inscritos >= grupo.getCupos()) {
            throw new CuposAgotadosExcepcion("No hay cupos disponibles en el grupo solicitado");
        }

        long cursosActivos = gateway.contarCursosActivosAlumno(datos.getAlumnoId());
        if (cursosActivos >= limiteCursosAlumno) {
            throw new LimiteCursosExcepcion(
                    "El alumno ya esta inscrito en el maximo de " + limiteCursosAlumno + " cursos activos");
        }

        if (gateway.existeInscripcion(datos.getAlumnoId(), datos.getCategoria(),
                datos.getCurso(), datos.getAnio(), datos.getIterable())) {
            Inscripcion existente = gateway.obtenerInscripcion(
                    datos.getAlumnoId(), datos.getCategoria(),
                    datos.getCurso(), datos.getAnio(), datos.getIterable());
            existente.setFechaDesvinculacion(null);
            return gateway.guardarInscripcion(existente);
        }
        datos.setFechaInscripcion(Timestamp.from(Instant.now()));
        return gateway.guardarInscripcion(datos);
    }

    @Override
    public boolean validarInscripcion(String alumnoId, String categoria, String curso, int anio, int iterable) {
        return gateway.existeInscripcionActiva(alumnoId, categoria, curso, anio, iterable);
    }

    @Override
    public Inscripcion desvincularInscripcion(String alumnoId, String categoria, String curso, int anio, int iterable) {
        if (!gateway.existeInscripcion(alumnoId, categoria, curso, anio, iterable)) {
            throw new NoExisteExcepcion("La inscripcion a desvincular no existe");
        }
        return gateway.desvincularInscripcion(alumnoId, categoria, curso, anio, iterable);
    }
}
