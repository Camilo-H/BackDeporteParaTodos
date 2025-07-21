package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.v2DTO.V2AlumnoDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.v2DTO.V2AtencionDTO;
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
    public List<V2AtencionDTO> getatenciones(String categoria, String curso, int anio, int iterable, int claseid) {
        //lista total alumnos clase
        List<V2AlumnoDTO> listaAlumnos = getAlumnosGrupo(categoria, curso, anio, iterable);
        //lista asistencias, solo id asistentes
        List<AsistenciaEntidad> listaAsistencia = getAsistenciasClase(claseid);
        //atenciones inicializadas en false
        List<V2AtencionDTO> list = new ArrayList<>();
        for(V2AlumnoDTO alumno: listaAlumnos){
            list.add(new V2AtencionDTO(alumno,claseid,false));
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

    /**
     * para el registro de atenciones de una clase, recibo una lista de atenciones y el id de la clase indicada.
     * @param atenciones listado de atenciones, cada atencion posee una bandera para indicar si se ha realizado la atencion o no
     * @param idclase clase involucrada
     * @return TODO: se deberia retornar un acuse para la aperacion, pendiente
     */
    @PostMapping("/atenciones")
    public String postAtenciones(@RequestBody List<V2AtencionDTO> atenciones, @RequestParam int idclase) {
        boolean estado = true;
        //se recorre el listado de atenciones para evaluar cada atencion.
        for(V2AtencionDTO atencion: atenciones){
            //Para construir una atencion se necesita una clase ID
            AsistenciaId idAsistencia = new AsistenciaId(atencion.getAlumno().getId(), atencion.getIdClase());
            //a partir de la id se crea una asistencia, marcada con eliminado 0, de modo que se parte de todas las asistencias no eliminadas
            AsistenciaEntidad asistencia = new AsistenciaEntidad(idAsistencia.getPerfilId(), idAsistencia.getClaseCodigo(), 0);
            //se evalua si la asistencia evaluada existe en la tabla de asistencias, si existe significa que esta atendido, lo contrario si no existe.
            //si coincide la existencia en la tabla con la bandera de la atencion evaluada, significa que no ha sufrido cambios en esta actualizacion
            if(repositorioAsistencias.existsById(idAsistencia)!=atencion.isEstaAtendido()){
                //la atencion ha sido modificada
                //si es true significa que se debe insertar en la tabla la asistencia
                if(atencion.isEstaAtendido()==true){
                    AsistenciaEntidad check1;
                    check1 = repositorioAsistencias.save(asistencia);
                    if(check1.getClaseCodigo()==null){
                        estado = false;
                    }
                //si es false, significa que se debe eliminar de la tabla.
                }else{
                    //para el caso de la asistencias si se debe realizar un delete sobre la base de datos, pero hay que evaluar para las otras tablas si se puede realizar o solo se marca su eliminacion.
                    repositorioAsistencias.delete(asistencia);
                    if(repositorioAsistencias.existsById(idAsistencia)){
                        estado = false;
                    }
                }
            }
        }
        //TODO: con los estado se da un acuse al cliente
        return "";
    }
    
    

    public List<V2AlumnoDTO> getAlumnosGrupo(String categoria, String curso, int anio, int iterable) {
        List<Object[]> objetos = repositorioAlumnos.buscarAlumnosGrupoRaw(categoria, curso, anio, iterable,0);
        List<V2AlumnoDTO> alumnoDTOs = new ArrayList<>();
        for (Object[] objects : objetos) {
            V2AlumnoDTO alumno = new V2AlumnoDTO();
            alumno = V2AlumnoDTO.fromObjectSQL(objects); //uso de static fabrica
            alumnoDTOs.add(alumno);
        }
        return alumnoDTOs;
    }

    public List<AsistenciaEntidad> getAsistenciasClase(Integer codigoClase){
        return repositorioAsistencias.findByClaseCodigo(codigoClase);
    }
}
