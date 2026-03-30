package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IInstructorServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.InstructorDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.logs.PeticionLogger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class InstructoresRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstructoresRest.class);

    @Autowired
    private IInstructorServicio servicioInstructor;

    @Operation(summary = "Obtiene todos los instructores registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de instructores"),
            @ApiResponse(responseCode = "404", description = "No existen instructores registrados")
    })
    @GetMapping("/instructores")
    public ResponseEntity<List<InstructorDto>> getMethodName() {
        PeticionLogger.log(LOGGER, "GET", "/api/v2/instructores", "sin datos");
        List<InstructorDto> respuesta = servicioInstructor.obtenerInstructores();
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    // falta endpoint para obtener un instructor por id, falta endpoint para crear
    // un instructor, falta endpoint para actualizar un instructor, falta endpoint
    // para eliminar un instructor

    @Operation(summary = "Obtiene un instructor por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Instructor encontrado"),
            @ApiResponse(responseCode = "404", description = "No existe el instructor con ese identificador")
    })
    @GetMapping("/instructor")
    public ResponseEntity<InstructorDto> getInstructorById(
            @RequestParam(name = "idInstructor") @NotBlank String idInstructor) {
        PeticionLogger.log(LOGGER, "GET", "/api/v2/instructor", "idInstructor: " + idInstructor);
        InstructorDto respuesta = servicioInstructor.obtenerInstructor(idInstructor);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

}
