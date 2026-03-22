package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ClaseEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IClaseRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.logs.PeticionLogger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class ClaseRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClaseRest.class);

    @Autowired
    private IClaseRepositorio repositorio;

    @GetMapping("/clasesGrupo")
    public List<ClaseEntidad> getClasesGrupo(@RequestParam String categoria, @RequestParam String curso, @RequestParam Integer anio, @RequestParam Integer iterable) {
        PeticionLogger.log(LOGGER, "GET", "/api/v2/clasesGrupo",
                "categoria=" + categoria + ", curso=" + curso + ", anio=" + anio + ", iterable=" + iterable);
        return repositorio.findByIdGrupoCategoriaAndIdGrupoCursoAndIdGrupoAnioAndIdGrupoIterableAndEliminado(categoria,curso,anio,iterable,0);
    }

    @PostMapping("/claseGrupo")
    public ResponseEntity<ClaseEntidad> postClase(@RequestBody ClaseEntidad entidad) {
        PeticionLogger.log(LOGGER, "POST", "/api/v2/claseGrupo", entidad);
        ClaseEntidad respuesta;
        if(entidad.getCodigo()!=null){
            if(repositorio.existsById(entidad.getCodigo())){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        
        respuesta = repositorio.save(entidad);
        
        return new ResponseEntity<>(respuesta,HttpStatus.CREATED);
        
    }

    /***
     * Cambia el estado eliminado de la clase a 1, para marcar como eliminada
     * @param id
     * @return
     */
    @DeleteMapping("/clase")
    public ResponseEntity<Integer> deleteClase(@RequestParam Integer id){
        PeticionLogger.log(LOGGER, "DELETE", "/api/v2/clase", "id=" + id);
        //TODO: tal vez un codigo mas apropiado sea 409 para marcar conflicto, discutir
        //se verifica que exista el elemento a manipular
        if(repositorio.existsById(id)==false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //No se debe hacer delete sobre la clase, lo apropiado es realizar el cambio de estado la metadato meta_eliminado, de 0 a 1
        //no es relevante el body, el codigo es lo importante en este espacio, al manejar con entidades y excepciones, puede que si se vuelva relevante el body
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
}
