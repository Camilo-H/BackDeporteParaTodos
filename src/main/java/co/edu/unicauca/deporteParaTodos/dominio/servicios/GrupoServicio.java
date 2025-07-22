package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IGrupoServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IGrupoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Grupo;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.GrupoDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.InsercionFallidaExepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoImplementadoException;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.YaExisteElementoExcepcion;

@Service
public class GrupoServicio implements IGrupoServicio {

    @Autowired
    private IGrupoGateway grupoGateway;

    public List<GrupoDto> obtenerTodosGrupos(){
        throw new NoImplementadoException();
    }

    public List<GrupoDto> obtenerGruposDisponibles(){
        throw new NoImplementadoException();
    }

    public List<GrupoDto> obtenerGruposDeCurso(String categoria, String curso){
        throw new NoImplementadoException();
    }

    public List<GrupoDto> obtenerGruposInscripcionDisponible(){
        throw new NoImplementadoException();
    }

    public List<GrupoDto> obtenerGruposInstructor(String idInstructor){
        throw new NoImplementadoException();
    }

    public GrupoDto insertarGrupo(GrupoDto datosGrupo){
        throw new NoImplementadoException();
    }

    public GrupoDto obtenerGrupoPorId(String categoria, String curso, Integer anio, Integer iterable){
        throw new NoImplementadoException();
    }

    public GrupoDto actualizarGrupo(String categoria, String curso, Integer anio, Integer iterable, GrupoDto datosGrupo){
        throw new NoImplementadoException();
    }

    public GrupoDto eliminarGrupo(String categoria, String curso, Integer anio, Integer iterable){
        throw new NoImplementadoException();
    }
}
