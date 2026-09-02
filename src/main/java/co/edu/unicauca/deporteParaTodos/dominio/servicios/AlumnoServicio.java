package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IAlumnoServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IAlumnoGateway;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IPerfilGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Alumno;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Perfil;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.AlumnoDto;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.YaExisteElementoExcepcion;

@Service
public class AlumnoServicio implements IAlumnoServicio {

    @Autowired
    private IAlumnoGateway alumnoGateway;

    @Autowired
    private IPerfilGateway perfilGateway;

    @Override
    public List<Alumno> obtenerAlumnos() {

        List<Alumno> listAlumnos = alumnoGateway.obtenerAlumnos();
        if (listAlumnos.isEmpty()) {
            throw new ListadoVacioExcepcion("No se encuentran alumnos registrados");
        }
        return listAlumnos;
    }

    @Override
    public List<AlumnoDto> obtenerAlumnosGrupo(String categoria, String curso, Integer anio, Integer iterable) {
        List<Alumno> alumnos = alumnoGateway.obtenerAlumnosGrupo(categoria, curso, anio, iterable);
        if (alumnos.isEmpty()) {
            throw new ListadoVacioExcepcion("No se encuentran alumnos registrados para el grupo consultado");
        }
        List<AlumnoDto> listaDtos = new ArrayList<>();
        alumnos.forEach(modelo -> {
            AlumnoDto dto = AlumnoDto.fabricarDeModelo(modelo);
            listaDtos.add(dto);
        });
        return listaDtos;
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
    @Transactional
    public Alumno actualizarAlumno(String alumnoId, Alumno datosAlumno) {
        if (!alumnoGateway.existeAlumno(alumnoId)) {
            throw new NoExisteExcepcion("No existe el alumno con el identificador " + alumnoId);
        }
        // 1. Actualizar nombre y correo en tbl_perfil
        //    Se pasa un Perfil con solo nombre y correo; perf_tipo y perf_sexo
        //    vienen null — PerfilGateway los ignorará gracias a los null-checks
        Perfil datosPerfilActualizar = new Perfil();
        datosPerfilActualizar.setNombre(datosAlumno.getPerfil().getNombre());
        datosPerfilActualizar.setCorreo(datosAlumno.getPerfil().getCorreo());
        perfilGateway.actualizarPerfil(alumnoId, datosPerfilActualizar);

        // 2. Actualizar alm_tipo en tbl_alumno y retornar el alumno completo
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
