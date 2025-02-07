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
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICoordinadorServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Coordinador;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.CoordinadorDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@RequestMapping("api")
@Validated
public class CoordinadorRestControlador {

    @Autowired
    private ICoordinadorServicio servicio;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @GetMapping("/coordinadores")
    public ResponseEntity<List<CoordinadorDTO>> obtenerCoordinadores() {
        List<Coordinador> coordinadores = servicio.obtenerCoordinadores();
        List<CoordinadorDTO> listDTO = mapper.map(coordinadores, new TypeToken<List<CoordinadorDTO>>() {
        }.getType());
        ResponseEntity<List<CoordinadorDTO>> respuesta = new ResponseEntity<List<CoordinadorDTO>>(listDTO,
                HttpStatus.OK);
        return respuesta;
    }

    @GetMapping("/coordinadores/{id}")
    public ResponseEntity<CoordinadorDTO> obtenerCoordinador(@PathVariable String id) {
        Coordinador cordExistente = servicio.obtenerCoordinador(id);
        if (cordExistente == null ) {
            throw new NoExisteExcepcion("No existe el coodinador con el identificador "+id);
        }
        CoordinadorDTO respuesta = mapper.map(cordExistente, CoordinadorDTO.class);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    


}
