package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IEstadisticasGateway;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.EstadisticaDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IAsistenciaRepositorio;

@Service
public class EstadisticasGateway implements IEstadisticasGateway{
    @Autowired
    private IAsistenciaRepositorio repoAsistencia;

    @Override
    public List<EstadisticaDto> estadisticasCategorias(LocalDate fechaInicio, LocalDate fechaFin) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'estadisticasCategorias'");
    }

    @Override
    public List<EstadisticaDto> estadisticasCursos(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Object[]> objetos = repoAsistencia.estadisticasCursos(fechaInicio, fechaFin);
        List<EstadisticaDto> estadisticas = new ArrayList<>();
        objetos.forEach((objeto)->{
            EstadisticaDto estadistica = EstadisticaDto.fromObjectCursos(objeto);
            estadisticas.add(estadistica);
        });
        return estadisticas;
    }

    @Override
    public List<EstadisticaDto> estadisticasGrupos(LocalDate fechaInicio, LocalDate fechaFin, String categoria, String curso, Integer anio, Integer iterable) {
        List<Object[]> objetos = repoAsistencia.estadisticasGrupos(fechaInicio, fechaFin, categoria, curso, anio, iterable);
        List<EstadisticaDto> estadisticas = new ArrayList<>();
        objetos.forEach((objeto)->{
            EstadisticaDto estadistica = EstadisticaDto.fromObjectGrupos(objeto);
            estadisticas.add(estadistica);
        });
        return estadisticas;
    }

    @Override
    public EstadisticaDto estadisticaAlumno(String alumno, LocalDate fechaInicio, LocalDate fechaFin) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'estadisticaAlumno'");
    }

    @Override
    public EstadisticaDto estadisticaInstructor(LocalDate fechaInicio, LocalDate fechaFin) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'estadisticaInstructor'");
    }

    @Override
    public EstadisticaDto estadisticaFacultad(String facultad, LocalDate fechaInicio, LocalDate fechaFin) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'estadisticaFacultad'");
    }

    @Override
    public List<EstadisticaDto> estadisticasPrograma(String facultad, String programa, LocalDate fechaInicio,
            LocalDate fechaFin) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'estadisticasPrograma'");
    }

}
