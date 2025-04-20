package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.dtoProcedimientos.alumnoDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.dtoProcedimientos.atencionDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.AsistenciaEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids.AsistenciaId;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IAlumnoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IAsistenciaRepositorio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class AtencionesRest {
    @Autowired 
    private IAlumnoRepositorio repositorioAlumnos;

    @Autowired
    private IAsistenciaRepositorio repositorioAsistencias;

    @GetMapping("/atencionesporclase")
    public List<atencionDTO> getatenciones(String categoria, String curso, int anio, int iterable, int claseid) {
        //lista total alumnos clase
        List<alumnoDTO> listaAlumnos = getAlumnosGrupo(categoria, curso, anio, iterable);
        //lista asistencias, solo id asistentes
        List<AsistenciaEntidad> listaAsistencia = getAsistenciasClase(claseid);
        //atenciones inicializadas en false
        List<atencionDTO> list = new ArrayList<>();
        for(alumnoDTO alumno: listaAlumnos){
            list.add(new atencionDTO(alumno,claseid,false));
        }
        for(AsistenciaEntidad asistencia: listaAsistencia){
            for(int i=0; i < list.size(); i++){
                if(asistencia.getPerfilId().equals(list.get(i).getAlumno().getId())){
                    list.get(i).setEstaAtendido(true);
                    break;
                }
            }
        }
        return list;
    }

    @PostMapping("/atenciones")
    public String postAtenciones(@RequestBody List<atencionDTO> atenciones, @RequestParam int idclase) {
        boolean estado = true;
        for(atencionDTO atencion: atenciones){
            AsistenciaId idAsistencia = new AsistenciaId(atencion.getAlumno().getId(), atencion.getIdClase());
            AsistenciaEntidad asistencia = new AsistenciaEntidad(idAsistencia.getPerfilId(), idAsistencia.getClaseCodigo(), 0);
            if(repositorioAsistencias.existsById(idAsistencia)!=atencion.isEstaAtendido()){
                if(atencion.isEstaAtendido()==true){
                    AsistenciaEntidad check1;
                    check1 = repositorioAsistencias.save(asistencia);
                    if(check1.getClaseCodigo()==null){
                        estado = false;
                    }
                }else{
                    repositorioAsistencias.delete(asistencia);
                    if(repositorioAsistencias.existsById(idAsistencia)){
                        estado = false;
                    }
                }
            }
        }
        return "";
    }
    
    

    public List<alumnoDTO> getAlumnosGrupo(String categoria, String curso, int anio, int iterable) {
        List<Object[]> objetos = repositorioAlumnos.buscarAlumnosGrupoRaw(categoria, curso, anio, iterable,0);
        List<alumnoDTO> alumnoDTOs = new ArrayList<>();
        for (Object[] objects : objetos) {
            alumnoDTO alumno = new alumnoDTO();
            alumno = alumnoDTO.fromObjectSQL(objects); //uso de static fabrica
            alumnoDTOs.add(alumno);
        }
        return alumnoDTOs;
    }

    public List<AsistenciaEntidad> getAsistenciasClase(Integer codigoClase){
        return repositorioAsistencias.findByClaseCodigo(codigoClase);
    }
}
