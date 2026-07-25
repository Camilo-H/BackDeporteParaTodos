package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IInscripcionServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IInscripcionGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Inscripcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;

@Service
public class InscripcionServicio implements IInscripcionServicio {

    @Autowired
    private IInscripcionGateway gateway;

    @Override
    public Inscripcion inscribir(Inscripcion datos) {
        if (gateway.existeInscripcion(datos.getAlumnoId(), datos.getCategoria(), datos.getCurso(), datos.getAnio(), datos.getIterable())) {
            Inscripcion existente = gateway.obtenerInscripcion(datos.getAlumnoId(), datos.getCategoria(), datos.getCurso(), datos.getAnio(), datos.getIterable());
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
