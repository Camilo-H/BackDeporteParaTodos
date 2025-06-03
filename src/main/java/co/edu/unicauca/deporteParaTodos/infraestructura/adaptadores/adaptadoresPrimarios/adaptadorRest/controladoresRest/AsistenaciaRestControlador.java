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
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IAsistenciaServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Asistencia;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.AsistenciaDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import io.swagger.v3.oas.annotations.Hidden;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@Hidden
@RestController
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@RequestMapping("api")
@Validated
public class AsistenaciaRestControlador {

    @Autowired
    private IAsistenciaServicio servicio;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @GetMapping("/asistencias")
    public ResponseEntity<List<AsistenciaDTO>> obtenerAsistencias() {
        List<Asistencia> asistencias = servicio.obtenerAsistencias();
        List<AsistenciaDTO> listDTO = mapper.map(asistencias, new TypeToken<List<AsistenciaDTO>>() {
        }.getType());
        ResponseEntity<List<AsistenciaDTO>> respuesta = new ResponseEntity<>(listDTO, HttpStatus.OK);
        return respuesta;
    }

    @GetMapping("/asistencias/{per_id}/{cls_cod}")
    public ResponseEntity<AsistenciaDTO> obtenerAsistencia(@PathVariable String per_id, @PathVariable int cls_cod) {
        Asistencia asistenciaExistente = servicio.obtenerAsistencia(per_id, cls_cod);
        if (asistenciaExistente == null) {
            throw new NoExisteExcepcion();
        }
        AsistenciaDTO respuesta = mapper.map(asistenciaExistente, AsistenciaDTO.class);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/asistencias")
    public ResponseEntity<AsistenciaDTO> insertarAsistencia(@RequestParam AsistenciaDTO datosAsistencia) {
        Asistencia nuevamodelo = mapper.map(datosAsistencia, Asistencia.class);
        Asistencia nuevAsistencia = servicio.InsertarAsistencia(nuevamodelo);
        AsistenciaDTO respuesta = mapper.map(nuevAsistencia, AsistenciaDTO.class);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @DeleteMapping("/asistencias/{per_id}/{cls_cod}")
    public ResponseEntity<AsistenciaDTO> eliminarAsistencia(@PathVariable String per_id, @PathVariable int cls_cod) {
        Asistencia existente = servicio.obtenerAsistencia(per_id, cls_cod);
        if (existente != null) {
            Asistencia eliminada = servicio.eliminarAsitencia(per_id, cls_cod);
            AsistenciaDTO respuesta = mapper.map(eliminada, AsistenciaDTO.class);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }
        throw new NoExisteExcepcion();
    }

}
