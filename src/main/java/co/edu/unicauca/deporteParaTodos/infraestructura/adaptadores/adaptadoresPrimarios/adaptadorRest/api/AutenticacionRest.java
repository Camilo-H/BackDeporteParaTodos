package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.util.List;

import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.AlumnoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.PerfilEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.v2DTO.V2PerfilDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IAlumnoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICoordinadorRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IInstructorRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IPerfilRepositorio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class AutenticacionRest {
    @Autowired
    IPerfilRepositorio repositorioPerfil;
    @Autowired
    IAlumnoRepositorio repositorioAlumno;
    @Autowired
    IInstructorRepositorio repositorioInstructor;
    @Autowired
    ICoordinadorRepositorio repositorioCoordinador;

    @GetMapping("/login")
    public ResponseEntity<V2PerfilDTO> getLogin(@RequestParam String email) {
        List<PerfilEntidad> entidades;
        //TODO: el correo debe ser unique o identificador, de momento no lo es y la consulta genera una lista
        entidades = repositorioPerfil.findByPerfcorreo(email);
        if(entidades.size()>0){
            PerfilEntidad entidad = entidades.get(0);
            V2PerfilDTO dto = V2PerfilDTO.fabricaFromPerfilEntidad(entidad);
            if(repositorioCoordinador.existsById(entidad.getPerf_id())){
                dto.setRole("Coordinador");
                return new ResponseEntity<>(dto,HttpStatus.OK);
            }
            if(repositorioInstructor.existsById(entidad.getPerf_id())){
                dto.setRole("Instructor");
                return new ResponseEntity<>(dto,HttpStatus.OK);
            }
            if(repositorioAlumno.existsById(entidad.getPerf_id())){
                dto.setRole("Alumno");
                return new ResponseEntity<>(dto,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/RegistroPerfilAlumno")
    public ResponseEntity<V2PerfilDTO> postRegistroPerfilAlumno(@RequestBody V2PerfilDTO dto) {
        //TODO: process POST request
        PerfilEntidad entidad = new PerfilEntidad();
        entidad.setEliminado(0);
        entidad.setPerf_id(dto.getId());
        entidad.setPerf_nombre(dto.getNombre());
        entidad.setPerf_tipo(dto.getTipoId());
        entidad.setPerf_Sexo(dto.getSexo());
        entidad.setPerfcorreo(dto.getCorreo());
        PerfilEntidad entidadPerfilGuardado = repositorioPerfil.save(entidad);
        //ALM_TIPO IN('Estudiante','Administrativo','Docente')
        AlumnoEntidad entidadAlumno = new AlumnoEntidad();
        entidadAlumno.setEliminado(0);
        entidadAlumno.setIdPerfil(dto.getId());
        entidadAlumno.setTipoAlumno(dto.getTipoAlumno());
        AlumnoEntidad entidadAlumnoGuardado = repositorioAlumno.save(entidadAlumno);
        V2PerfilDTO respuestaDTO = V2PerfilDTO.fabricaFromPerfilEntidad(entidadPerfilGuardado);
        respuestaDTO.setRole("Alumno");
        respuestaDTO.setTipoAlumno(entidadAlumnoGuardado.getTipoAlumno());
        return new ResponseEntity<>(respuestaDTO, HttpStatus.CREATED);
    }
    
    
}
