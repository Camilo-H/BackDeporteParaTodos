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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IClaseServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Clase;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Instructor;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.ClaseDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@RequestMapping("api")
@Validated
public class ClaseRestControlador {

    @Autowired
    private IClaseServicio servicio;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @GetMapping("/clases")
    public ResponseEntity<List<ClaseDTO>> obtenerClases() {
        List<Clase> clases = servicio.obtenerClases();
        List<ClaseDTO> lista = mapper.map(clases, new TypeToken<List<ClaseDTO>>() {
        }.getType());
        ResponseEntity<List<ClaseDTO>> respuesta = new ResponseEntity<>(lista, HttpStatus.OK);
        return respuesta;
    }

    @GetMapping("/clases/{id}")
    public ResponseEntity<ClaseDTO> obtenerClase(@PathVariable int id) {
        Clase claseExistente = servicio.obtenerClase(id);
        if (claseExistente == null) {
            throw new NoExisteExcepcion();
        }
        ClaseDTO respuesta = mapper.map(claseExistente, ClaseDTO.class);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/clases")
    public ResponseEntity<ClaseDTO> insertarClase(@RequestBody ClaseDTO datosClase) {
        Clase nuevaClase = mapper.map(datosClase, Clase.class);
        ClaseDTO respuesta = mapper.map(nuevaClase, ClaseDTO.class);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PutMapping("/clases/{id}")
    public ResponseEntity<ClaseDTO> actualizarClase(@PathVariable int id, @RequestBody ClaseDTO datosClase) {
        Clase claseExistente = servicio.obtenerClase(id);
        if (claseExistente == null) {
            throw new NoExisteExcepcion();
        }
        Instructor instructor = mapper.map(claseExistente.getInstructor(), Instructor.class);
        claseExistente.setInstructor(instructor);
        claseExistente.setFecha(datosClase.getFecha());
        claseExistente.setHoraInicio(datosClase.getHoraInicio());
        claseExistente.setHoraFin(datosClase.getHoraFin());
        claseExistente.setObservacion(datosClase.getObservacion());
        servicio.actualizarClase(id, claseExistente);
        ClaseDTO respuesta = mapper.map(claseExistente, ClaseDTO.class);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/clases/{id}")
    public ResponseEntity<ClaseDTO> eliminarClase(@PathVariable int id) {
        Clase claseExistente = servicio.obtenerClase(id);
        if (claseExistente == null) {
            throw new NoExisteExcepcion();
        }
        Clase claseEliminada = servicio.eliminarClase(id);
        ClaseDTO respuesta = mapper.map(claseEliminada, ClaseDTO.class);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

}
