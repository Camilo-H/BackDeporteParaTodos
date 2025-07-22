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

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IGrupoServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.GrupoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class GrupoRest {

    @Autowired
    private IGrupoServicio servicio;

    @Operation(summary = "Obtiene todos los grupos del sistema sin discriminar su estado eliminado")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Grupos recuperados"),
    })
    @GetMapping("/grupos")
    public ResponseEntity<List<GrupoDto>> obtenerCursos(){
        List<GrupoDto> dtos = servicio.obtenerTodosGrupos();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
    
    @Operation(summary = "Obtiene todos los grupos disponibles del sistema")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Grupos recuperados"),
    })
    @GetMapping("gruposNoEliminados")
    public ResponseEntity<List<GrupoDto>> obtenerCursosNoeliminados(){
        List<GrupoDto> dtos = servicio.obtenerGruposDisponibles();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
    
    @Operation(summary = "Obtiene todos los grupos disponibles del sistema para un curso")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Grupos recuperados"),
    })
    @GetMapping("gruposCurso")
    public ResponseEntity<List<GrupoDto>> obtenerGruposDe(@RequestParam String prmCategoria, @RequestParam String prmCurso){
        List<GrupoDto> dtos = servicio.obtenerGruposDeCurso(prmCategoria, prmCurso);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene todos los grupos del sistema disponibles a la inscripcion")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Grupos recuperados"),
    })
    @GetMapping("/gruposInscripcion")
    public ResponseEntity<List<GrupoDto>> obtenerGruposInscripcion() {
        List<GrupoDto> dtos = servicio.obtenerGruposInscripcionDisponible();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
    
    @Operation(summary = "Obtiene todos los grupos del sistema asociados a un instructor")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Grupos recuperados"),
    })
    @GetMapping("/gruposInstructor")
    public ResponseEntity<List<GrupoDto>> obtnerGruposInstructor(@RequestParam String idInstructor) {
        List<GrupoDto> dtos = servicio.obtenerGruposInstructor(idInstructor);
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    /**
     * lo valores de anio e iterable no son tenidos en cuenta para la insercion
     * @param dto
     * @return
     */
    @Operation(summary = "Inserta un registro en el sistema, los valores de anio e iterable son calculados internamete por el servidor, reportes de error por json malformados pueden ser causados por fechas no formateadas adecuadamente")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "201", description = "Grupo insertado"),
    })
    @PostMapping("/grupo")
    public ResponseEntity<GrupoDto> postGrupo(@RequestBody @Valid GrupoDto dto) {
        GrupoDto guardado = servicio.insertarGrupo(dto);
        return new ResponseEntity<>(guardado, HttpStatus.CREATED);
    }

    @Operation(summary = "obtiene un grupo del sistema")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "grupo encontrado"),
    })
    @GetMapping("/grupo")
    public ResponseEntity<GrupoDto> obtenerGrupo(@RequestParam String categoria, @RequestParam String curso, @RequestParam Integer anio, @RequestParam Integer iterable) {
        GrupoDto dto = servicio.obtenerGrupo(categoria, curso, anio, iterable);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
    
}
