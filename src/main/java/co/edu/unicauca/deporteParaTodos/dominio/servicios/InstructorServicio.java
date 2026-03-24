package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IInstructorServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IInstructorGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Instructor;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.YaExisteElementoExcepcion;

@Service
public class InstructorServicio implements IInstructorServicio {

    @Autowired
    private IInstructorGateway instructorsGateway;

    @Override
    public List<Instructor> obtenerInstructores() {
        System.out.println("consultando instructores");
        List<Instructor> listInstructors = instructorsGateway.obtenerInstructores();
        if (listInstructors.isEmpty()) {
            throw new ListadoVacioExcepcion("No se encuentran instructores registrados");
        }
        return listInstructors;
    }

    @Override
    public Instructor insertarInstructor(Instructor datosInstructor) {
        return null;
    }

    @Override
    public Instructor obtenerInstructor(String instructorId) {
        return instructorsGateway.obtenerInstructor(instructorId).orElseThrow(
                () -> new NoExisteExcepcion("No exoste el instructor con el identificador " + instructorId));
    }

    @Override
    public Instructor actualizarInstructor(String instructorId, Instructor datosInstructor) {
        if (!instructorsGateway.existeInstructor(instructorId)) {
            throw new NoExisteExcepcion("No exoste el instructor con el identificador " + instructorId);
        }
        return instructorsGateway.actualizarInstructor(instructorId, datosInstructor);
    }

    @Override
    public Instructor eliminarInstructor(String instructorId) {
        if (!instructorsGateway.existeInstructor(instructorId)) {
            throw new NoExisteExcepcion("No exoste el instructor con el identificador " + instructorId);
        }
        return instructorsGateway.eliminarInstructor(instructorId);
    }

}
