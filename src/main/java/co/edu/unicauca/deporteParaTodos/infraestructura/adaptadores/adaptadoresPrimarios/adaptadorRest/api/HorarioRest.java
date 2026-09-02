package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IHorarioServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Horario;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.HorarioDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.logs.PeticionLogger;
import co.edu.unicauca.deporteParaTodos.infraestructura.mappers.HorarioMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v2")
@Validated
public class HorarioRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HorarioRest.class);

    @Autowired
    private IHorarioServicio servicio;

    @Operation(summary = "Lista los horarios activos de un grupo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de horarios"),
    })
    @GetMapping("/horarios")
    public ResponseEntity<List<HorarioDto>> listarHorariosPorGrupo(
            @RequestParam String categoria,
            @RequestParam String curso,
            @RequestParam int anio,
            @RequestParam int iterable) {
        PeticionLogger.log(LOGGER, "GET", "/api/v2/horarios",
                "categoria=" + categoria + ", curso=" + curso + ", anio=" + anio + ", iterable=" + iterable);
        List<Horario> horarios = servicio.listarHorariosPorGrupo(categoria, curso, anio, iterable);
        List<HorarioDto> respuesta = horarios.stream()
                .map(HorarioMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un horario por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Horario encontrado"),
        @ApiResponse(responseCode = "404", description = "Horario no encontrado"),
    })
    @GetMapping("/horario")
    public ResponseEntity<HorarioDto> obtenerHorario(@RequestParam Integer prmId) {
        PeticionLogger.log(LOGGER, "GET", "/api/v2/horario", "prmId=" + prmId);
        Horario horario = servicio.obtenerHorario(prmId);
        return new ResponseEntity<>(HorarioMapper.toDto(horario), HttpStatus.OK);
    }

    @Operation(summary = "Registra un nuevo horario para un grupo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Horario registrado"),
    })
    @PostMapping("/horario")
    public ResponseEntity<HorarioDto> insertarHorario(@RequestBody @Valid HorarioDto dto) {
        PeticionLogger.log(LOGGER, "POST", "/api/v2/horario", dto);
        Horario guardado = servicio.insertarHorario(HorarioMapper.fromDto(dto));
        return new ResponseEntity<>(HorarioMapper.toDto(guardado), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualiza los datos de un horario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Horario actualizado"),
        @ApiResponse(responseCode = "404", description = "Horario no encontrado"),
    })
    @PutMapping("/horario")
    public ResponseEntity<HorarioDto> actualizarHorario(
            @RequestParam Integer prmId,
            @RequestBody @Valid HorarioDto dto) {
        PeticionLogger.log(LOGGER, "PUT", "/api/v2/horario", "prmId=" + prmId);
        Horario actualizado = servicio.actualizarHorario(prmId, HorarioMapper.fromDto(dto));
        return new ResponseEntity<>(HorarioMapper.toDto(actualizado), HttpStatus.OK);
    }

    @Operation(summary = "Eliminación lógica de un horario (meta_eliminado=1)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Horario eliminado lógicamente"),
        @ApiResponse(responseCode = "404", description = "Horario no encontrado"),
        @ApiResponse(responseCode = "409", description = "El horario ya estaba eliminado"),
    })
    @DeleteMapping("/horario")
    public ResponseEntity<HorarioDto> eliminarHorario(@RequestParam Integer prmId) {
        PeticionLogger.log(LOGGER, "DELETE", "/api/v2/horario", "prmId=" + prmId);
        Horario eliminado = servicio.eliminarHorario(prmId);
        return new ResponseEntity<>(HorarioMapper.toDto(eliminado), HttpStatus.OK);
    }
}
