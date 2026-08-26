package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IDeporteServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.DeporteDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.logs.PeticionLogger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v2")
@Validated
public class DeporteRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeporteRest.class);

    @Autowired
    private IDeporteServicio servicioDeporte;

    @Operation(summary = "Obtiene todos los deportes registrados en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de deportes"),
        @ApiResponse(responseCode = "404", description = "No existen deportes registrados")
    })
    @GetMapping("/deportes")
    public ResponseEntity<List<DeporteDto>> obtenerDeportes() {
        PeticionLogger.log(LOGGER, "GET", "/api/v2/deportes", "sin datos");
        List<DeporteDto> respuesta = servicioDeporte.listaDeportes();
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/deportes")

    public ResponseEntity<DeporteDto> insertarDeporte(@RequestBody @Valid DeporteDto dto) {
       PeticionLogger.log(LOGGER, "POST", "/api/v2/deportes", dto);
       DeporteDto dtoGuardado = servicioDeporte.insertarDeporte(dto);
       return new ResponseEntity<>(dtoGuardado, HttpStatus.CREATED);
    }
    
}
