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
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IHorarioServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Grupo;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Horario;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.HorarioDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.NoExisteElementoDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@RequestMapping("api")
@Validated
public class HorarioRestControlador {

    @Autowired
    private IHorarioServicio servicio;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @GetMapping("/horarios")
    public ResponseEntity<List<HorarioDTO>> obtenerHorarios() {
        List<Horario> horaios = servicio.obtenerHorarios();
        List<HorarioDTO> resultado = mapper.map(horaios, new TypeToken<List<HorarioDTO>>() {
        }.getType());
        ResponseEntity<List<HorarioDTO>> respuesta = new ResponseEntity<>(resultado, HttpStatus.OK);
        return respuesta;
    }

    @GetMapping("/horarios/{id}")
    public ResponseEntity<?> obtenerHorario(@PathVariable int id) {
        Horario horarioExistente = servicio.obtenerHorario(id);
        if (horarioExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NoExisteElementoDTO("No se encontró el horario con ID: " + id));
        }
        HorarioDTO respuesta = mapper.map(horarioExistente, HorarioDTO.class);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/horarios")
    public ResponseEntity<HorarioDTO> insertarHorario(@Valid @RequestBody HorarioDTO datosHorario) {
        Horario nuevoHorario = mapper.map(datosHorario, Horario.class);
        Horario horaioInsertado = servicio.insertatarHoratio(nuevoHorario);
        HorarioDTO restpuesta = mapper.map(horaioInsertado, HorarioDTO.class);
        return new ResponseEntity<>(restpuesta, HttpStatus.CREATED);
    }

    @PutMapping("horarios/{id}")
    public ResponseEntity<HorarioDTO> actualizarHorario(@PathVariable int id, @RequestBody HorarioDTO datosHorario) {
        Horario actual = servicio.obtenerHorario(id);
        if (actual == null) {
            throw new NoExisteExcepcion();
        }
        Grupo grupoHorario = mapper.map(datosHorario.getGrupo(), Grupo.class);
        actual.setGrupo(grupoHorario);
        actual.setDia(datosHorario.getDia());
        actual.setHoraInicio(datosHorario.getHoraInicio());
        actual.setHoraFin(datosHorario.getHoraFin());
        actual.setEscenario(datosHorario.getEscenario());
        Horario nuevoHorario = servicio.actualizarHorario(id, actual);
        HorarioDTO respuesta = mapper.map(nuevoHorario, HorarioDTO.class);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/horarios/{id}")
    public ResponseEntity<HorarioDTO> eliminarHorario(@PathVariable int id) {
        Horario horarioExistente = servicio.obtenerHorario(id);
        if (horarioExistente == null) {
            throw new NoExisteExcepcion();
        }
        Horario horarioEliminado = servicio.eliminarHorario(id);
        HorarioDTO respuesDto = mapper.map(horarioEliminado, HorarioDTO.class);
        return new ResponseEntity<>(respuesDto, HttpStatus.OK);
    }
}
