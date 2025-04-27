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

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.CursoDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.v2DTO.V2CursoDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CategoriaCursoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CursoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICategoriaCursoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICursoRepositorio;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class CursoRest {
    @Autowired
    private ICursoRepositorio repositorio;

    @GetMapping("/cursos")
    public Iterable<CursoEntidad> obtenerCursos(){
        return repositorio.findAll();
    }

    @GetMapping("/cursosbycategoria")
    public ResponseEntity<List<V2CursoDTO>> obtenerCursosPorCategoria(@RequestParam String prmCategoria){
        List<CursoEntidad> entidades = repositorio.findByCategoriaCursoAndEliminado(prmCategoria, 0);
        List<V2CursoDTO> dtos = new ArrayList<>();
        for(CursoEntidad entidad : entidades){
            V2CursoDTO dto = V2CursoDTO.fromEntity(entidad);
            dtos.add(dto);
        }
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @GetMapping("/curso")
    public CursoEntidad obtenerCurso(@RequestParam String prmCategoria, @RequestParam String prmCurso) {
        return repositorio.findByCategoriaCursoAndNombre(prmCategoria, prmCurso);
    }

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