package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.AlumnoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.PerfilEntidad;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IAutenticacionServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.v2DTO.V2PerfilDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.PerfilDto;
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

    @Autowired
    IAutenticacionServicio autenticacionServicio;

    @GetMapping("/login")
    public ResponseEntity<PerfilDto> getLogin(@RequestParam String email) {
        PerfilDto dto = autenticacionServicio.login(email);
        return new ResponseEntity<>(dto, HttpStatus.OK);
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
