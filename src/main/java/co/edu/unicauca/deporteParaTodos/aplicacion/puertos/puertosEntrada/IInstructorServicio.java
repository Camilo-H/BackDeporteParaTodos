package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Instructor;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.InstructorDto;

public interface IInstructorServicio {

    public List<InstructorDto> obtenerInstructores();

    public Instructor insertarInstructor(Instructor datosInstructor);

    public Instructor obtenerInstructor(String instructorId);

    public Instructor actualizarInstructor(String instructorId, Instructor datosInstructor);

    public Instructor eliminarInstructor(String instructorId);

}
