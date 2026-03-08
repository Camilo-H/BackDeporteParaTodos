package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IAlumnoServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IAlumnoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Alumno;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.YaExisteElementoExcepcion;

@Service
public class AlumnoServicio implements IAlumnoServicio {

    @Autowired
    private IAlumnoGateway alumnoGateway;

    @Override
    public List<Alumno> obtenerAlumnos() {

        List<Alumno> listAlumnos = alumnoGateway.obtenerAlumnos();
        if (listAlumnos.isEmpty()) {
            throw new ListadoVacioExcepcion("No se encuentran alumnos registrados");
        }
        return listAlumnos;
    }

    @Override
    public Alumno insertAlumno(Alumno datosAlumno) {
        return null;
    }

    @Override
    public Alumno obtenerAlumno(String alumnoId) {
        return alumnoGateway.obtenerAlumno(alumnoId).orElseThrow(
                () -> new NoExisteExcepcion("No exoste el alumno con el identificador " + alumnoId));
    }

    @Override
    public Alumno actualizarAlumno(String alumnoId, Alumno datosAlumno) {
        if (!alumnoGateway.existeAlumno(alumnoId)) {
            throw new NoExisteExcepcion("No exoste el alumno con el identificador " + alumnoId);
        }
        return alumnoGateway.actualizarAlumno(alumnoId, datosAlumno);
    }

    @Override
    public Alumno eliminarAlumno(String alumnoId) {
        if (!alumnoGateway.existeAlumno(alumnoId)) {
            throw new NoExisteExcepcion("No exoste el alumno con el identificador " + alumnoId);
        }
        return alumnoGateway.eliminarAlumno(alumnoId);
    }

}
