package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.time.LocalDate;
import java.util.List;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.EstadisticaDto;

public interface IEstadisticaServicio {
    //categorias
    List<EstadisticaDto> estadisticasCategorias(LocalDate fechaInicio, LocalDate fechaFin);
    //cursos
    List<EstadisticaDto> estadisticasCursos(LocalDate fechaInicio, LocalDate fechaFin);
    //grupos
    List<EstadisticaDto> estadisticasGrupos(LocalDate fechaInicio, LocalDate fechaFin, String categoria, String curso, Integer anio, Integer iterable);
    //alumno
    EstadisticaDto estadisticaAlumno(String alumno, LocalDate fechaInicio, LocalDate fechaFin);
    //instructor
    EstadisticaDto estadisticaInstructor(LocalDate fechaInicio, LocalDate fechaFin);
    //facultad
    EstadisticaDto estadisticaFacultad(String facultad, LocalDate fechaInicio, LocalDate fechaFin);
    //programa
    List<EstadisticaDto> estadisticasPrograma(String facultad, String programa, LocalDate fechaInicio, LocalDate fechaFin);
}
