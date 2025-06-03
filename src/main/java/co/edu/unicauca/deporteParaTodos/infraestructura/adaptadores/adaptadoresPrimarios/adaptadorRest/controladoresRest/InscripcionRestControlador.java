package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.controladoresRest;

import java.sql.Timestamp;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IInscripcionServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Inscripcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.InscripcionDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.YaExisteElementoExcepcion;
import io.swagger.v3.oas.annotations.Hidden;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Hidden
@RestController
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@RequestMapping("api")
@Validated
public class InscripcionRestControlador {

    @Autowired
    private IInscripcionServicio servicio;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @GetMapping("/inscripciones")
    public ResponseEntity<List<InscripcionDTO>> obtenerInscripciones() {
        List<Inscripcion> inscripciones = servicio.obtenerInscripciones();
        List<InscripcionDTO> listaInscripcionDTOs = mapper.map(inscripciones, new TypeToken<List<InscripcionDTO>>() {
        }.getType());
        ResponseEntity<List<InscripcionDTO>> respuesta = new ResponseEntity<>(listaInscripcionDTOs, HttpStatus.OK);
        return respuesta;
    }

    @GetMapping("/inscripciones/{fecha}")
    public ResponseEntity<InscripcionDTO> obtenerInscripcion(@PathVariable Timestamp fecha) {
        Inscripcion existente = servicio.obteneInscripcion(fecha);
        InscripcionDTO respuesta = mapper.map(existente, InscripcionDTO.class);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/inscripciones")
    public ResponseEntity<InscripcionDTO> insertarInscripcion(@RequestBody InscripcionDTO datosInscripcion) {
        if (servicio.obteneInscripcion(datosInscripcion.getFechaInscripcion()) != null) {
            Inscripcion modelo = mapper.map(datosInscripcion, Inscripcion.class);
            Inscripcion nuevaInscripcion = servicio.insertarInscripcion(modelo);
            InscripcionDTO respuesta = mapper.map(nuevaInscripcion, InscripcionDTO.class);
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        }
        throw new YaExisteElementoExcepcion("Ya existe la inscripcion");
    }

    @DeleteMapping()
    public ResponseEntity<InscripcionDTO> eliminarInscripcion(@PathVariable Timestamp fecha) {
        if (servicio.obteneInscripcion(fecha) != null) {
            Inscripcion eliminada = servicio.eliminarInscripcion(fecha);
            InscripcionDTO respuesta = mapper.map(eliminada, InscripcionDTO.class);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }
        throw new NoExisteExcepcion();
    }

}
