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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IAlumnoServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.AlumnoDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.logs.PeticionLogger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class AlumnoRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlumnoRest.class);

    @Autowired
    private IAlumnoServicio servicioAlumno;

    @Operation(summary = "Obtiene los alumnos inscritos en un grupo especifico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de alumnos del grupo"),
        @ApiResponse(responseCode = "404", description = "No existen alumnos para el grupo consultado")
    })
    @GetMapping("/alumnosGrupo")
    public ResponseEntity<List<AlumnoDto>> obtenerAlumnosGrupo(
            @Parameter(description = "Categoria del grupo")
            @RequestParam @NotBlank String categoria,
            @Parameter(description = "Curso del grupo")
            @RequestParam @NotBlank String curso,
            @Parameter(description = "Anio del grupo")
            @RequestParam @NotNull Integer anio,
            @Parameter(description = "Iterable del grupo")
            @RequestParam @NotNull Integer iterable) {
        PeticionLogger.log(LOGGER, "GET", "/api/v2/alumnosGrupo",
                "categoria=" + categoria + ", curso=" + curso + ", anio=" + anio + ", iterable=" + iterable);
        List<AlumnoDto> respuesta = servicioAlumno.obtenerAlumnosGrupo(categoria, curso, anio, iterable);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
