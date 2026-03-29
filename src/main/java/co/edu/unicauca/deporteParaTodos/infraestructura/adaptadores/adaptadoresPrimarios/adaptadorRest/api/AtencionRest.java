package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IAsistenciaServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.AtencionDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.logs.PeticionLogger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class AtencionRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AtencionRest.class);

    @Autowired
    private IAsistenciaServicio servicioAsistencia;

    @Operation(summary = "Obtiene las atenciones registradas para una clase especifica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de atenciones por clase"),
        @ApiResponse(responseCode = "404", description = "No existen atenciones para la clase consultada")
    })
    @GetMapping("/atencionesporclase")
    public ResponseEntity<List<AtencionDto>> obtenerAtencionesPorClase(
            @Parameter(description = "Codigo de la clase a consultar")
            @RequestParam("claseid") @NotNull Integer claseId) {
        PeticionLogger.log(LOGGER, "GET", "/api/v2/atencionesporclase", "claseid=" + claseId);
        List<AtencionDto> respuesta = servicioAsistencia.obtenerAtencionesPorClase(claseId);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @Operation(summary = "Registra la asistencia de una lista de alumnos para una clase especifica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Asistencias procesadas correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Clase o alumno no encontrado")
    })
    @PostMapping("/atenciones")
    public ResponseEntity<Void> registrarAtencionesPorClase(
            @Parameter(description = "Codigo de la clase para las atenciones")
            @RequestParam("idClase") @NotNull Integer claseId,
            @RequestBody @Valid List<AtencionDto> atenciones) {
        PeticionLogger.log(LOGGER, "POST", "/api/v2/atenciones", "idClase=" + claseId + ", atenciones=" + atenciones);
        servicioAsistencia.registrarAtencionesPorClase(atenciones, claseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
