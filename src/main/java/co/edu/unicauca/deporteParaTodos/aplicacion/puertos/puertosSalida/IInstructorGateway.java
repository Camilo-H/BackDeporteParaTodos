package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida;

import java.util.List;
import java.util.Optional;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Instructor;

public interface IInstructorGateway {
    public boolean existeInstructor(String instructorId);

    public List<Instructor> obtenerInstructores();

    public Instructor insertarInstructor(Instructor datosInstructor);

    public Optional<Instructor> obtenerInstructor(String instructorId);

    public Instructor actualizarInstructor(String instructorId, Instructor datosInstructor);

    public Instructor eliminarInstructor(String instructorId);
}
