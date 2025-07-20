package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace.DynatraceProperties.V2;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICursoServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.CursoDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.v2DTO.V2CursoDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.CursoDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CategoriaCursoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CursoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICategoriaCursoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICursoRepositorio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class CursoRest {

    @Autowired
    private ICursoRepositorio repositorio;

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

    @Operation(summary = "registrar curso en el sistema")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "curso encontrado"),
    })
    @PostMapping("/curso")
    public ResponseEntity<V2CursoDTO> postAgregarCurso(@RequestBody V2CursoDTO dto) {
        CursoEntidad entidad = new CursoEntidad(
            dto.getNombre(),
            dto.getDeporte(),
            dto.getCategoriaCurso(),
            dto.getDescripcion(),
            dto.getIdImagen(),
            0
        );
        CursoEntidad entidadGuardada=null;
        entidadGuardada = repositorio.save(entidad);
        V2CursoDTO retorno = V2CursoDTO.fromEntity(entidadGuardada);
        return new ResponseEntity<>(retorno,HttpStatus.CREATED);
    }
    
    
}