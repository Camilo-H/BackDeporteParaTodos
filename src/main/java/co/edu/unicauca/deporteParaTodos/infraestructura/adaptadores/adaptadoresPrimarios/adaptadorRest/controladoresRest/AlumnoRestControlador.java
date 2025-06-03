package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.controladoresRest;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IAlumnoServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Alumno;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.AlumnoDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Hidden
@RestController
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@RequestMapping("api")
@Validated
public class AlumnoRestControlador {

    @Autowired
    private IAlumnoServicio servicio;

    // Use mapper generico para conversiones directas en los tipos
    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @GetMapping("/alumnos")
    public ResponseEntity<List<AlumnoDTO>> obtenerAlumnos() {
        List<Alumno> resultado = servicio.obtenerAlumnos();
        List<AlumnoDTO> listDTO = mapper.map(resultado, new TypeToken<List<AlumnoDTO>>() {
        }.getType());
        ResponseEntity<List<AlumnoDTO>> respuesta = new ResponseEntity<List<AlumnoDTO>>(listDTO, HttpStatus.OK);
        return respuesta;
    }

    @GetMapping("/alumnos/{id}")
    public ResponseEntity<AlumnoDTO> obtenerAlumno(@PathVariable String id) {
        Alumno alumnoExistente = servicio.obtenerAlumno(id);
        if (alumnoExistente == null) {
            throw new NoExisteExcepcion("No existe el alumno con el identificador " + id);
        }
        AlumnoDTO respuestaDTO = mapper.map(alumnoExistente, AlumnoDTO.class);
        return new ResponseEntity<AlumnoDTO>(respuestaDTO, HttpStatus.OK);

    }

    @PostMapping("/alumnos")
    public ResponseEntity<AlumnoDTO> insertarAlumno(@Valid @RequestBody AlumnoDTO datosAlumno) {

        return null;
    }

    @PutMapping("/alumnos/{id}")
    public ResponseEntity<AlumnoDTO> actualizarAlumno(@PathVariable String id, @RequestBody AlumnoDTO datosAlumno) {

        return null;
    }

    @DeleteMapping("/alumnos/{id}")
    public ResponseEntity<AlumnoDTO> eliminarAlumno(String id) {
        Alumno alumnoExistente = servicio.obtenerAlumno(id);
        if (alumnoExistente == null) {
            throw new NoExisteExcepcion("No existe el alumno con el identificador " + id);
        }
        Alumno alumnoEliminar = servicio.eliminarAlumno(id);
        AlumnoDTO respuestaDto = mapper.map(alumnoEliminar, AlumnoDTO.class);
        return new ResponseEntity<>(respuestaDto, HttpStatus.OK);
    }

}
