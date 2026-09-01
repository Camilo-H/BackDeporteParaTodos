package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IEscenarioServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Escenario;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.EscenarioDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.logs.PeticionLogger;
import co.edu.unicauca.deporteParaTodos.infraestructura.mappers.EscenarioMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v2")
@Validated
public class EscenarioRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EscenarioRest.class);

    @Autowired
    private IEscenarioServicio servicio;

    @Operation(summary = "Obtiene todos los escenarios activos del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de escenarios"),
    })
    @GetMapping("/escenarios")
    public ResponseEntity<List<EscenarioDto>> listarEscenarios() {
        PeticionLogger.log(LOGGER, "GET", "/api/v2/escenarios", "sin datos");
        List<EscenarioDto> respuesta = servicio.listarEscenarios()
                .stream().map(EscenarioMapper::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un escenario por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Escenario encontrado"),
        @ApiResponse(responseCode = "404", description = "Escenario no encontrado"),
    })
    @GetMapping("/escenario")
    public ResponseEntity<EscenarioDto> obtenerEscenario(@RequestParam Integer prmId) {
        PeticionLogger.log(LOGGER, "GET", "/api/v2/escenario", "prmId=" + prmId);
        Escenario escenario = servicio.obtenerEscenario(prmId);
        return new ResponseEntity<>(EscenarioMapper.toDto(escenario), HttpStatus.OK);
    }

    @Operation(summary = "Registra un nuevo escenario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Escenario registrado"),
        @ApiResponse(responseCode = "409", description = "Ya existe un escenario con ese nombre"),
    })
    @PostMapping("/escenario")
    public ResponseEntity<EscenarioDto> insertarEscenario(@RequestBody @Valid EscenarioDto dto) {
        PeticionLogger.log(LOGGER, "POST", "/api/v2/escenario", dto);
        Escenario guardado = servicio.insertarEscenario(EscenarioMapper.fromDto(dto));
        return new ResponseEntity<>(EscenarioMapper.toDto(guardado), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualiza los datos de un escenario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Escenario actualizado"),
        @ApiResponse(responseCode = "404", description = "Escenario no encontrado"),
        @ApiResponse(responseCode = "409", description = "El nombre ya pertenece a otro escenario"),
    })
    @PutMapping("/escenario")
    public ResponseEntity<EscenarioDto> actualizarEscenario(
            @RequestParam Integer prmId,
            @RequestBody @Valid EscenarioDto dto) {
        PeticionLogger.log(LOGGER, "PUT", "/api/v2/escenario", "prmId=" + prmId);
        Escenario actualizado = servicio.actualizarEscenario(prmId, EscenarioMapper.fromDto(dto));
        return new ResponseEntity<>(EscenarioMapper.toDto(actualizado), HttpStatus.OK);
    }

    @Operation(summary = "Eliminación lógica de un escenario (meta_eliminado=1)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Escenario eliminado lógicamente"),
        @ApiResponse(responseCode = "404", description = "Escenario no encontrado"),
        @ApiResponse(responseCode = "409", description = "El escenario ya estaba eliminado"),
    })
    @DeleteMapping("/escenario")
    public ResponseEntity<EscenarioDto> eliminarEscenario(@RequestParam Integer prmId) {
        PeticionLogger.log(LOGGER, "DELETE", "/api/v2/escenario", "prmId=" + prmId);
        Escenario eliminado = servicio.eliminarEscenario(prmId);
        return new ResponseEntity<>(EscenarioMapper.toDto(eliminado), HttpStatus.OK);
    }
}
