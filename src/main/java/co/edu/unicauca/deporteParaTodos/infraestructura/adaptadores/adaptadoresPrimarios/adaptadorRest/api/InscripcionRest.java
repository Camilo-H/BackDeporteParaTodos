package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.InscripcionEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids.InscripcionId;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IInscripcionRepositorio;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class InscripcionRest {
    @Autowired
    IInscripcionRepositorio repositorio;
    
    /**
     * si no existe registra un inscripcion con fecha de desvinculacion nula y fecha de inscripcion actual.
     * si existe, cambia el valor de fecha desvinculaicon a null, para que sea una inscripcion activa nuevamente.
     * @param entidad
     * @return entidad guardada o actualizada
     */
    @PostMapping("/inscripcion")
    //TODO: crear dto
    public ResponseEntity<InscripcionEntidad> postMethodName(@RequestBody InscripcionEntidad entidad) {
        InscripcionId id = new InscripcionId(entidad.getCategoria(), entidad.getCurso(), entidad.getAnio(), entidad.getIterable(), entidad.getAlumnoId());
        boolean existe = repositorio.existsById(id);
        InscripcionEntidad entidadGuardada;
        if(existe){
            Optional<InscripcionEntidad> optional = repositorio.findById(id);
            entidadGuardada = optional.get();
            entidadGuardada.setFechaDesvinculacion(null);
            InscripcionEntidad actualizada = repositorio.save(entidadGuardada);
            return new ResponseEntity<>(actualizada, HttpStatus.CREATED);
        }
        entidad.setFechaInscripcion(Timestamp.from(Instant.now()));
        entidadGuardada = repositorio.save(entidad);
        return new ResponseEntity<>(entidadGuardada, HttpStatus.CREATED);
    }
    
    /**
     * Valida si una inscripcion esta activa basandose en su existencia y en las fechas de inscripcion y desvinculacion
     * @param entidad entidad a validar, puede contener las fechas en null
     * @return false si no existe o si su fecha de desvisculacion esta registrada antes de la fecha actual, true en caso de que exista y su fecha de desvisculacion es nula o superior a la actual
     */
    @GetMapping("/validarInscripcion")
    public ResponseEntity<Boolean> validarIncripcion(@RequestBody InscripcionEntidad entidad){
        boolean respuesta = repositorio.existeInscripcionActiva(entidad.getAlumnoId(), entidad.getCategoria(), entidad.getCurso(), entidad.getAnio(), entidad.getIterable());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    /**
     * cambia el valor de fecha desvinculacion a la fecha actual
     * @param entidad inscripcion puede tener las fechas nulas, lo relevante en este caso son los id
     * @return inscripcion actualizada
     */
    @PutMapping("/desvincularInscripcion")
    public ResponseEntity<InscripcionEntidad> eliminarInscripcion(@RequestBody InscripcionEntidad entidad){
        InscripcionId id = new InscripcionId(entidad.getCategoria(), entidad.getCurso(), entidad.getAnio(), entidad.getIterable(), entidad.getAlumnoId());
        Optional<InscripcionEntidad> opcional = repositorio.findById(id);
        if(opcional.isPresent()){
            InscripcionEntidad actualizarEntidad;
            actualizarEntidad = opcional.get();
            actualizarEntidad.setFechaDesvinculacion(Timestamp.from(Instant.now()));
            InscripcionEntidad actualizada = repositorio.save(actualizarEntidad);
            return new ResponseEntity<>(actualizada, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
