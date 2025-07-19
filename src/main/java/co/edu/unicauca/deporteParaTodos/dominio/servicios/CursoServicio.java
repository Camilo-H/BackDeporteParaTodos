package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICursoServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.ICursoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Curso;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.CursoDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.InsercionFallidaExepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;

@Service
public class CursoServicio implements ICursoServicio {

    @Autowired
    private ICursoGateway cursoGateway;

    /***
     * Retorna todos los cursos del sistema
     */
    @Override
    public List<CursoDto> recuperarCursos() {
        List<Curso> cursos = cursoGateway.obtenerCursos();
        List<CursoDto> dtos = new ArrayList<>();

        cursos.forEach(curso -> {
            CursoDto dto = CursoDto.fabricarDeModelo(curso);
            dtos.add(dto);
        });
        return dtos;
    }

    @Override
    public List<CursoDto> cursosDeCategoria(String categoria) {
        List<Curso> cursos = cursoGateway.obtenerCursoDeCategoria(categoria);
        List<CursoDto> dtos = new ArrayList<>();
        cursos.forEach(curso -> {
            CursoDto dto = CursoDto.fabricarDeModelo(curso);
            dtos.add(dto);
        });
        return dtos;
    }

    @Override
    public CursoDto obtenerCurso(String titulo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerCurso'");
    }

    @Override
    public CursoDto insertarCurso(CursoDto datosCurso) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertarCurso'");
    }

    @Override
    public CursoDto actualizarCurso(CursoDto datCurso) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizarCurso'");
    }

    @Override
    public CursoDto eliminarCurso(String titulo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarCurso'");
    }

    

}
