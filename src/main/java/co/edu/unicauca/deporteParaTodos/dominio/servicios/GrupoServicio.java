package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IGrupoServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IGrupoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Grupo;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.GrupoDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoProcesableEntidadException;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.YaExisteElementoExcepcion;

@Service
public class GrupoServicio implements IGrupoServicio {

    @Autowired
    private IGrupoGateway grupoGateway;

    public List<GrupoDto> obtenerTodosGrupos(){
        List<Grupo> grupos = grupoGateway.obtenerTodosGrupos();
        List<GrupoDto> dtos = new ArrayList<>();
        grupos.forEach(grupo -> {
            GrupoDto dto = GrupoDto.fabricarDeModelo(grupo);
            dtos.add(dto);
        });
        return dtos;
    }

    public List<GrupoDto> obtenerGruposDisponibles(){
        List<Grupo> grupos = grupoGateway.obtenerGruposDisponibles();
        List<GrupoDto> dtos = new ArrayList<>();
        grupos.forEach(grupo ->{
            GrupoDto dto = GrupoDto.fabricarDeModelo(grupo);
            dtos.add(dto);
        });
        return dtos;
    }

    public List<GrupoDto> obtenerGruposDeCurso(String categoria, String curso){
        List<Grupo> grupos = grupoGateway.obtenerGruposDeCurso(categoria, curso);
        List<GrupoDto> dtos = new ArrayList<>();
        grupos.forEach(grupo -> {
            GrupoDto dto = GrupoDto.fabricarDeModelo(grupo);
            dtos.add(dto);
        });
        return dtos;
    }

    public List<GrupoDto> obtenerGruposInscripcionDisponible(){
        List<Grupo> grupos = grupoGateway.obtenerGruposInscripcionDisponible();
        List<GrupoDto> dtos = new ArrayList<>();
        grupos.forEach(grupo -> {
            GrupoDto dto = GrupoDto.fabricarDeModelo(grupo);
            dtos.add(dto);
        });
        return dtos;
    }

    public List<GrupoDto> obtenerGruposInstructor(String idInstructor){
        List<Grupo> grupos = grupoGateway.obtenerGruposInstructor(idInstructor);
        List<GrupoDto> dtos = new ArrayList<>();
        grupos.forEach(grupo -> {
            GrupoDto dto = GrupoDto.fabricarDeModelo(grupo);
            dtos.add(dto);
        });
        return dtos;
    }

    public GrupoDto insertarGrupo(GrupoDto datosGrupo){
        Grupo grupo = Grupo.fabricarDeDto(datosGrupo);
        Grupo guardado = grupoGateway.insertarGrupo(grupo);
        GrupoDto respuesta = GrupoDto.fabricarDeModelo(guardado);
        return respuesta;
    }

    public GrupoDto obtenerGrupoPorId(String categoria, String curso, Integer anio, Integer iterable){
        Grupo grupo = grupoGateway.obtenerGrupoPorId(categoria, curso, anio, iterable);
        if(grupo==null){
            throw new NoExisteExcepcion("el objetivo no existe en el sistema");
        }
        GrupoDto dto = GrupoDto.fabricarDeModelo(grupo);
        return dto;
    }

    public GrupoDto actualizarGrupo(String categoria, String curso, Integer anio, Integer iterable, GrupoDto datosGrupo){
        if(!grupoGateway.existeGrupo(categoria, curso, anio, iterable)){
            throw new NoExisteExcepcion("no existe el objetivo a actualizar");
        }
        Grupo grupo = Grupo.fabricarDeDto(datosGrupo);
        if(grupo==null){
            throw new NoProcesableEntidadException("No fue posible convertir de dto a modelo en servicio");
        }
        Grupo actualizado = grupoGateway.actualizarGrupo(categoria, curso, anio, iterable, grupo);
        return GrupoDto.fabricarDeModelo(actualizado);
    }

    public GrupoDto eliminarGrupo(String categoria, String curso, Integer anio, Integer iterable){
        if(!grupoGateway.existeGrupo(categoria, curso, anio, iterable)){
            throw new NoExisteExcepcion("el objetivo a eliminar no existe");
        }
        Grupo grupo = grupoGateway.eliminarGrupo(categoria, curso, anio, iterable);
        return GrupoDto.fabricarDeModelo(grupo);
    }
}
