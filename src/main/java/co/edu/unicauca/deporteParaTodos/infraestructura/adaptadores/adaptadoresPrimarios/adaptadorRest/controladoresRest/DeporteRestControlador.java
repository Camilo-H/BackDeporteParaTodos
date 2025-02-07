package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.controladoresRest;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IDeporteServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Deporte;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.DeporteDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import java.util.List;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@RequestMapping("api")
@Validated
public class DeporteRestControlador {

    @Autowired
    private IDeporteServicio servicio;

    // Use mapper generico para conversiones directas en los tipos
    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    // Obtener lista de deportes
    @GetMapping("/deportes")
    public ResponseEntity<List<DeporteDTO>> obtenerDeportes() {
        List<Deporte> listaDeportes = servicio.listaDeportes();
        List<DeporteDTO> listDTO = mapper.map(listaDeportes, new TypeToken<List<DeporteDTO>>() {
        }.getType());
        return new ResponseEntity<>(listDTO, HttpStatus.OK);
    }

    // Obtener deporte por nombre
    @GetMapping("/deportes/{nombre}")
    public ResponseEntity<DeporteDTO> obtenerDeportePorNombre(@PathVariable String nombre) {
        Deporte deporte = servicio.obtenerDeportePorId(nombre);
        if (deporte == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        DeporteDTO deporteDTO = mapper.map(deporte, DeporteDTO.class);
        return new ResponseEntity<>(deporteDTO, HttpStatus.OK);
    }

    // Insertar un nuevo deporte
    @PostMapping("/deportes")
    public ResponseEntity<DeporteDTO> insertarDeporte(@Valid @RequestBody DeporteDTO deporteDTO) {
        Deporte deporte = mapper.map(deporteDTO, Deporte.class);
        Deporte nuevoDeporte = servicio.insertarDeporte(deporte);
        DeporteDTO nuevoDeporteDTO = mapper.map(nuevoDeporte, DeporteDTO.class);
        return new ResponseEntity<>(nuevoDeporteDTO, HttpStatus.CREATED);
    }

    // Actualizar un deporte existente
    @PutMapping("/deportes/{nombre}")
    public ResponseEntity<DeporteDTO> actualizarDeporte(@PathVariable String nombre,
            @Valid @RequestBody DeporteDTO deporteDTO) {
        Deporte deporteExistente = servicio.obtenerDeportePorId(nombre);
        if (deporteExistente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Actualizamos los datos del deporte existente con los datos del DTO
        deporteExistente.setNombre(deporteDTO.getNombre());
        Deporte deporteActualizado = servicio.actualizarDeporte(nombre, deporteExistente);
        DeporteDTO deporteActualizadoDTO = mapper.map(deporteActualizado, DeporteDTO.class);
        return new ResponseEntity<>(deporteActualizadoDTO, HttpStatus.OK);
    }

    // Eliminar un deporte por nombre
    @DeleteMapping("/deportes/{nombre}")
    public ResponseEntity<Void> eliminarDeporte(@PathVariable String nombre) {
        Deporte deporte = servicio.eliminarDeporte(nombre);
        if (deporte == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
