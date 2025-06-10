package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IEstadisticaServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IEstadisticasGateway;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.EstadisticaDto;

@Service
public class EstadisticaServicio implements IEstadisticaServicio{

    @Autowired
    private IEstadisticasGateway gateEstadistica;

    @Override
    public List<EstadisticaDto> estadisticasCategorias(LocalDate fechaInicio, LocalDate fechaFin, String categoria) {
        return gateEstadistica.estadisticasCategorias(fechaInicio, fechaFin, categoria);
    }

    @Override
    public List<EstadisticaDto> estadisticasCursos(LocalDate fechaInicio, LocalDate fechaFin, String categoria, String curso) {
        return gateEstadistica.estadisticasCursos(fechaInicio, fechaFin, categoria, curso);
    }

    @Override
    public List<EstadisticaDto> estadisticasGrupos(LocalDate fechaInicio, LocalDate fechaFin, String categoria, String curso, Integer anio, Integer iterable){
        return gateEstadistica.estadisticasGrupos(fechaInicio, fechaFin, categoria, curso, anio, iterable);
    }

    @Override
    public List<EstadisticaDto> estadisticaAlumno(String alumno, LocalDate fechaInicio, LocalDate fechaFin) {
        return gateEstadistica.estadisticaAlumno(alumno, fechaInicio, fechaFin);
    }

    @Override
    public EstadisticaDto estadisticaInstructor(LocalDate fechaInicio, LocalDate fechaFin) {
        return gateEstadistica.estadisticaInstructor(fechaInicio, fechaFin);
    }

    @Override
    public EstadisticaDto estadisticaFacultad(String facultad, LocalDate fechaInicio, LocalDate fechaFin) {
        return gateEstadistica.estadisticaFacultad(facultad, fechaInicio, fechaFin);
    }

    @Override
    public List<EstadisticaDto> estadisticasPrograma(String facultad, String programa, LocalDate fechaInicio,
            LocalDate fechaFin) {
        return gateEstadistica.estadisticasPrograma(facultad, programa, fechaInicio, fechaFin);
    }

}
