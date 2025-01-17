package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.controladoresRest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IFacultadServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Facultad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.FacultadDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.YaExisteElementoExcepcion;
import jakarta.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@RequestMapping("api")
@Validated
public class FacultadRestControlador {

    @Autowired
    private IFacultadServicio servicio;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @GetMapping("/facultades")
    public ResponseEntity<List<FacultadDTO>> obtenerFacultades() {
        List<Facultad> listaFacultades = servicio.obtenerFacultades();
        List<FacultadDTO> listDTO = mapper.map(listaFacultades, new TypeToken<List<FacultadDTO>>() {
        }.getType());
        return new ResponseEntity<List<FacultadDTO>>(listDTO, HttpStatus.OK);
    }

    @GetMapping("/facultades/{nombre}")
    public ResponseEntity<FacultadDTO> obtenerFacultad(@PathVariable String nombre) {
        Facultad facultadExistente = servicio.obtenerFacultad(nombre);
        if (facultadExistente == null) {
            throw new NoExisteExcepcion("No existe la facultad con el nombre " + nombre);
        }
        FacultadDTO respuesta = mapper.map(facultadExistente, FacultadDTO.class);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/facultades/{nombre}")
    public ResponseEntity<FacultadDTO> insertarFacultad(@PathVariable String nombre,
            @Valid @RequestBody FacultadDTO datosFacultad) {
        Facultad nuevaFacultad = mapper.map(datosFacultad, Facultad.class);
        Facultad existe = servicio.obtenerFacultad(nombre);
        if (existe != null) {
            throw new YaExisteElementoExcepcion("Ya existe el registro de la facultad");
        }
        Facultad facultadInsertada = servicio.insertarFacultad(nuevaFacultad);
        FacultadDTO respuesta = mapper.map(facultadInsertada, FacultadDTO.class);
        return new ResponseEntity<FacultadDTO>(respuesta, HttpStatus.CREATED);
    }

    @DeleteMapping("/facultades/{nombre}")
    public ResponseEntity<FacultadDTO> eliminarFacultad(@PathVariable String nombre) {
        Facultad verificacion = servicio.obtenerFacultad(nombre);
        if (verificacion == null) {
            throw new NoExisteExcepcion("No existe la facultad con el nombre " + nombre);
        }
        Facultad facultadEliminar = servicio.eliminarFacultad(nombre);
        FacultadDTO respuesta = mapper.map(facultadEliminar, FacultadDTO.class);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
