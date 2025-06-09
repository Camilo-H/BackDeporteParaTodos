package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IEstadisticaServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.EstadisticaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class EstadisticasRest {

    @Autowired
    private IEstadisticaServicio servEstadistica;

    @Operation(summary = "Retorna una lista cuyos elementos estan etiquetados como categoria, junto a su informacion en atenciones brindadas y tiempo, solo la leyenda 1 se ocupa para el titulo de la categoria")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
    })
    @GetMapping("estadisticas/categorias")
    public ResponseEntity<List<EstadisticaDto>> getEstaditicasCategorias(
        @Parameter(description = "fecha inicial, use el formato YYYY-MM-DD ejemplo: 2025-01-01")
        @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @Parameter(description = "fecha final, use el formato YYYY-MM-DD ejemplo: 2025-01-02")
        @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        List<EstadisticaDto> estadisticas = servEstadistica.estadisticasCategorias(fechaInicio, fechaFin);
        return new ResponseEntity<>(estadisticas, HttpStatus.OK);
    }
    
    @Operation(summary = "Retorna una lista cuyos elementos estan etiquetados como cursos, junto a su informacion en atenciones brindadas y tiempo, leyenda 1 y 2 se usan para categoria y curso respectivamente")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
    })
    @GetMapping("estadisticas/cursos")
    public ResponseEntity<List<EstadisticaDto>> getEstadisticasCursos(
        @Parameter(description = "fecha inicial, use el formato YYYY-MM-DD ejemplo: 2025-01-01")
        @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @Parameter(description = "fecha final, use el formato YYYY-MM-DD ejemplo: 2025-01-02")
        @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        List<EstadisticaDto> estadisticas = servEstadistica.estadisticasCursos(fechaInicio, fechaFin);
        return new ResponseEntity<>(estadisticas, HttpStatus.OK);
    }
    
    @Operation(summary = "Retorna una lista cuyos elementos estan etiquetados como grupos, junto a su informacion en atenciones brindadas y tiempo, leyenda 1, 2, 3, 4 ocupan categoria, curso, grupo e iterable respectivamente")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
    })
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

        List<EstadisticaDto> estadisticas = servEstadistica.estadisticasGrupos(fechaInicio, fechaFin, categoria, curso, anio, iterable);
        return new ResponseEntity<>(estadisticas, HttpStatus.OK);
    }
}
