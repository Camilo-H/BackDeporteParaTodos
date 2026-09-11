package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IEstadisticaServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.EstadisticaDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.logs.PeticionLogger;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/v2")
@Validated
public class EstadisticasRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstadisticasRest.class);

    @Autowired
    private IEstadisticaServicio servEstadistica;

    @Operation(summary = "Retorna una lista cuyos elementos estan etiquetados como categoria, junto a su informacion en atenciones brindadas y tiempo, solo la leyenda 1 se ocupa para el titulo de la categoria")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "404", description = "Identificador especificado no existe"),
    })
    @PreAuthorize("hasAuthority('Coordinador')")
    @GetMapping("estadisticas/categorias")
    public ResponseEntity<List<EstadisticaDto>> getEstaditicasCategorias(
        @Parameter(description = "fecha inicial, use el formato YYYY-MM-DD ejemplo: 2025-01-01")
        @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @Parameter(description = "fecha final, use el formato YYYY-MM-DD ejemplo: 2025-01-02")
        @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
        @RequestParam(required = false) String categoria
        ) {
        PeticionLogger.log(LOGGER, "GET", "/api/v2/estadisticas/categorias",
                "inicio=" + fechaInicio + ", fin=" + fechaFin + ", categoria=" + categoria);
        List<EstadisticaDto> estadisticas = servEstadistica.estadisticasCategorias(fechaInicio, fechaFin, categoria);
        return new ResponseEntity<>(estadisticas, HttpStatus.OK);
    }
    
    @Operation(summary = "Retorna una lista cuyos elementos estan etiquetados como cursos, junto a su informacion en atenciones brindadas y tiempo, leyenda 1 y 2 se usan para categoria y curso respectivamente")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "404", description = "Identificador especificado no existe"),
    })
    @PreAuthorize("hasAuthority('Coordinador')")
    @GetMapping("estadisticas/cursos")
    public ResponseEntity<List<EstadisticaDto>> getEstadisticasCursos(
        @Parameter(description = "fecha inicial, use el formato YYYY-MM-DD ejemplo: 2025-01-01")
        @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @Parameter(description = "fecha final, use el formato YYYY-MM-DD ejemplo: 2025-01-02")
        @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
        @RequestParam(required = false) String categoria,
        @RequestParam(required = false) String curso
        ) {
        PeticionLogger.log(LOGGER, "GET", "/api/v2/estadisticas/cursos",
                "inicio=" + fechaInicio + ", fin=" + fechaFin + ", categoria=" + categoria + ", curso=" + curso);
        List<EstadisticaDto> estadisticas = servEstadistica.estadisticasCursos(fechaInicio, fechaFin, categoria, curso);
        return new ResponseEntity<>(estadisticas, HttpStatus.OK);
    }
    
    @Operation(summary = "Retorna una lista cuyos elementos estan etiquetados como grupos, junto a su informacion en atenciones brindadas y tiempo, leyenda 1, 2, 3, 4 ocupan categoria, curso, grupo e iterable respectivamente")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "404", description = "Identificador especificado no existe"),
    })
    @PreAuthorize("hasAuthority('Coordinador')")
    @GetMapping("estadisticas/grupos")
    public ResponseEntity<List<EstadisticaDto>> getEstadisticasGrupos(
        @Parameter(description = "fecha inicial, use el formato YYYY-MM-DD ejemplo: 2025-01-01")
        @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @Parameter(description = "fecha final, use el formato YYYY-MM-DD ejemplo: 2025-01-02")
        @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
        @Parameter(description = "campo opcional, si es null obtendra de todas las categorias")
        @RequestParam(required = false) String categoria,
        @Parameter(description = "campo opcional, si es null obtendra de todos los cursos")
        @RequestParam(required = false) String curso,
        @Parameter(description = "campo opcional, si es null obtendra de todos los años")
        @RequestParam(required = false) Integer anio,
        @Parameter(description = "campo opcional, si es null obtendra de todas los iterables")
        @RequestParam(required = false) Integer iterable
        ) {
        PeticionLogger.log(LOGGER, "GET", "/api/v2/estadisticas/grupos",
                "inicio=" + fechaInicio + ", fin=" + fechaFin + ", categoria=" + categoria + ", curso=" + curso
                        + ", anio=" + anio + ", iterable=" + iterable);
        List<EstadisticaDto> estadisticas = servEstadistica.estadisticasGrupos(fechaInicio, fechaFin, categoria, curso, anio, iterable);
        return new ResponseEntity<>(estadisticas, HttpStatus.OK);
    }

    @Operation(summary = "Retorna una lista cuyos elementos estan etiquetados como id alumno, junto a su informacion en atenciones brindadas y tiempo, leyenda 1 ocupa id del alumno")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "404", description = "Identificador especificado no existe"),
    })
    @PreAuthorize("hasAuthority('Coordinador')")
    @GetMapping("estadisticas/alumnos")
    public ResponseEntity<List<EstadisticaDto>> getEtadisticasAlumnos(
        @Parameter(description = "fecha inicial, use el formato YYYY-MM-DD ejemplo: 2025-01-01")
        @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @Parameter(description = "fecha final, use el formato YYYY-MM-DD ejemplo: 2025-01-02")
        @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
        @Parameter(description = "campo opcional, si es null obtendra de todos los alumnos")
        @RequestParam(required = false) String alumno
    ) {
        PeticionLogger.log(LOGGER, "GET", "/api/v2/estadisticas/alumnos",
                "inicio=" + fechaInicio + ", fin=" + fechaFin + ", alumno=" + alumno);
        List<EstadisticaDto> estadisticas = servEstadistica.estadisticaAlumno(alumno, fechaInicio, fechaFin);
        return new ResponseEntity<>(estadisticas,HttpStatus.OK);
    }
    
    @Operation(summary = "Retorna una lista cuyos elementos estan etiquetados como id instructor, junto a su informacion en atenciones brindadas y tiempo, leyenda 1 ocupa id del instructor")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "404", description = "Identificador especificado no existe"),
    })
    @PreAuthorize("hasAuthority('Coordinador')")
    @GetMapping("estadisticas/instructores")
    public ResponseEntity<List<EstadisticaDto>> getEtadisticasInstructores(
        @Parameter(description = "fecha inicial, use el formato YYYY-MM-DD ejemplo: 2025-01-01")
        @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @Parameter(description = "fecha final, use el formato YYYY-MM-DD ejemplo: 2025-01-02")
        @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
        @Parameter(description = "campo opcional, si es null obtendra de todos los instructores")
        @RequestParam(required = false) String instructor
    ) {
        PeticionLogger.log(LOGGER, "GET", "/api/v2/estadisticas/instructores",
                "inicio=" + fechaInicio + ", fin=" + fechaFin + ", instructor=" + instructor);
        List<EstadisticaDto> estadisticas = servEstadistica.estadisticaInstructor(instructor, fechaInicio, fechaFin);
        return new ResponseEntity<>(estadisticas,HttpStatus.OK);
    }
}
