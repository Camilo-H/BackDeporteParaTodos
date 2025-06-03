package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.controladoresRest;

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
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IProgramaServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Programa;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.ProgramaDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@Hidden
@RestController
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@RequestMapping("api")
@Validated
public class ProgramaRestControlador {

    @Autowired
    private IProgramaServicio servicio;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @GetMapping("programas")
    public ResponseEntity<List<ProgramaDTO>> obtenerProgramas() {
        List<Programa> lista = servicio.obtenerProgramas();
        List<ProgramaDTO> listaDTO = mapper.map(lista, new TypeToken<List<ProgramaDTO>>() {
        }.getType());
        ResponseEntity<List<ProgramaDTO>> respuesta = new ResponseEntity<List<ProgramaDTO>>(listaDTO, HttpStatus.OK);
        return respuesta;
    }

    @GetMapping("/programas/{nombre}")
    public ResponseEntity<ProgramaDTO> obtenerPrograma(@PathVariable String nombre) {
        Programa programaExistente = servicio.obtenerPrograma(nombre);
        ProgramaDTO respuestaDto = mapper.map(programaExistente, ProgramaDTO.class);
        return new ResponseEntity<>(respuestaDto, HttpStatus.OK);
    }

    @PostMapping("/programas")
    public ResponseEntity<ProgramaDTO> insertarPrograma(@Valid @RequestBody ProgramaDTO datosPrograma) {
        Programa nuevoPrograma = mapper.map(datosPrograma, Programa.class);
        Programa programaInsertado = servicio.insertarPrograma(nuevoPrograma);
        ProgramaDTO respuesta = mapper.map(programaInsertado, ProgramaDTO.class);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @DeleteMapping("/programas/{nombre}")
    public ResponseEntity<ProgramaDTO> eliminarPrograma(@PathVariable String nombre) {
        Programa programaExistente = servicio.obtenerPrograma(nombre);
        if (programaExistente == null) {
            throw new NoExisteExcepcion("No existe el programa con el nombre " + nombre);
        }
        Programa programaBorrado = servicio.eliminarPrograma(nombre);
        ProgramaDTO respuesta = mapper.map(programaBorrado, ProgramaDTO.class);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

}
