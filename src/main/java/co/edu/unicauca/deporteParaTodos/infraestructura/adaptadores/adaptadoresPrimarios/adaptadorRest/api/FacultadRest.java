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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IFacultadServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.FacultadDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.logs.PeticionLogger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class FacultadRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacultadRest.class);

    @Autowired
    private IFacultadServicio servicioFacultad;

    @Operation(summary = "Obtiene todas las facultades registradas en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de facultades"),
        @ApiResponse(responseCode = "404", description = "No existen facultades registradas")
    })
    @GetMapping("/facultades")
    public ResponseEntity<List<FacultadDto>> obtenerFacultades() {
        PeticionLogger.log(LOGGER, "GET", "/api/v2/facultades", "sin datos");
        List<FacultadDto> respuesta = servicioFacultad.obtenerFacultades();
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
