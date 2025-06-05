package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICategoriaCursoServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.CategoriaDto;
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
public class CategoriaRest {

    @Autowired
    private ICategoriaCursoServicio servicioCategoria;
    
    @Operation(summary = "Obtener todas las categorias disponibles en el sistema")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "listado de categorias"),
    })
    @GetMapping("/categorias2")
    public ResponseEntity<List<CategoriaDto>> obtenerCategoriasExistentes(){        

        List<CategoriaDto> listaDtos = servicioCategoria.recuperarCategoriasCurso();
        return new ResponseEntity<>(listaDtos, HttpStatus.OK);

    }

    @Operation(summary = "Obtener categoria por titulo, independientemente si eesta marcada como eliminada")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Elemento encontrado"),
        @ApiResponse(responseCode = "404", description = "Elemento no encontrado")
    })
    @GetMapping("/categoria")
    public ResponseEntity<CategoriaDto> obtenerCategoria(
            @Parameter(description = "identificador de la categoria, titulo")
            @RequestParam String titulo
        ) {
        CategoriaDto dto = servicioCategoria.obtenerCategoriaCursoPorId(titulo);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    

    @Operation(summary = "Inserta una categoria en el sistema, el id de la imagen debe corresponder a uno ya existente en el sistema")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "201", description = "Operacion exitosa"),
    })
    @PostMapping("/categoria")
    public ResponseEntity<CategoriaDto> postCategoria(@RequestBody @Valid CategoriaDto dto) {
        CategoriaDto respuesta = servicioCategoria.insertarCategoria(dto);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Actualiza una categoria en el sistema, el id de la imagen debe corresponder a uno ya existente en el sistema")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
    })
    @PutMapping("/categoria")
    public ResponseEntity<CategoriaDto> putCategoria(@RequestParam @NotBlank String titulo, @RequestBody @Valid CategoriaDto dto) {
        CategoriaDto respuesta = servicioCategoria.actualizarCategoria(titulo, dto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    /***
     * Para una categoria identificada con "titulo", cambia el estado para que no sea accesible la categoria en cuestion.
     * @param titulo identificador de la categoria.
     * @return cantidad de filas afectadas en la peticion, 1 representa exito en la operacion.
     */
    @Operation(summary = "Elimina una categoria")
    @DeleteMapping("/categoria")
    public ResponseEntity<CategoriaDto> deleteCategoria(@RequestParam String titulo){
        CategoriaDto categoria = servicioCategoria.eliminarCategoria(titulo);
        return new ResponseEntity<>(categoria, HttpStatus.OK);
    }
}
