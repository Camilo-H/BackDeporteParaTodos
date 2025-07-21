package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICursoServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.CursoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class CursoRest {

    @Autowired
    private ICursoServicio servicio;

    @Operation(summary = "Obtiene todos los cursos del sistema sin restricciones")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "listado de cursos en formato dto"),
    })
    @GetMapping("/cursos")
    public ResponseEntity<List<CursoDto>> obtenerCursos(){
        List<CursoDto> respuesta = servicio.recuperarCursos();
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene todos los cursos disponibles del sistema para una categoria")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "listado de cursos en formato dto"),
    })
    @GetMapping("/cursosbycategoria")
    public ResponseEntity<List<CursoDto>> obtenerCursosPorCategoria(
        @Parameter(description = "Identificador de una categoria del sistema")
        @RequestParam String prmCategoria
        ){
        List<CursoDto> respuesta = servicio.cursosDeCategoria(prmCategoria);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un curso del sistema")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "curso encontrado"),
    })
    @GetMapping("/curso")
    public ResponseEntity<CursoDto> obtenerCurso(
        @Parameter(description = "Identificador de una categoria del sistema")
        @RequestParam String prmCategoria, 
        @Parameter(description = "Identificador de un curso en el sistema")
        @RequestParam String prmCurso) {
        CursoDto dto = servicio.obtenerCurso(prmCategoria, prmCurso);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "registrar curso en el sistema, no manipula los id")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "curso registrado"),
    })
    @PostMapping("/curso")
    public ResponseEntity<CursoDto> postAgregarCurso(@RequestBody @Valid CursoDto dto) {
        CursoDto dtoGuardado = servicio.insertarCurso(dto);
        return new ResponseEntity<>(dtoGuardado,HttpStatus.CREATED);
    }
    
    @Operation(summary = "actualiza curso en el sistema")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "curso actualizado, no manipula los id"),
    })
    @PutMapping("curso")
    public ResponseEntity<CursoDto> actualizarCurso(@RequestParam @NotBlank String categoria, @RequestParam @NotBlank String curso, @RequestBody @Valid CursoDto dto) {
        //TODO: process PUT request
        CursoDto dtoActualizado = servicio.actualizarCurso(categoria, curso, dto);
        return new ResponseEntity<>(dtoActualizado, HttpStatus.OK);
    }
}