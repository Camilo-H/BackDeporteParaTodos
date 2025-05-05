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

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.GrupoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IGrupoRepositorio;

@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class GrupoRest {
    @Autowired
    private IGrupoRepositorio repositorio;

    @GetMapping("/grupos")
    public Iterable<GrupoEntidad> obtenerCursos(){
        return repositorio.findAll();
    }

    @GetMapping("gruposNoEliminados")
    public List<GrupoEntidad> obtenerCursosNoeliminados(){
        return repositorio.findByEliminado(0);
    }
    
    @GetMapping("gruposCurso")
    public List<GrupoEntidad> obtenerGruposDe(@RequestParam String prmCategoria, @RequestParam String prmCurso){
        return repositorio.findByCategoriaAndCursoAndEliminado(prmCategoria, prmCurso, 0);
    }

    @GetMapping("/gruposInscripcion")
    public List<GrupoEntidad> obtenerGruposInscripcion() {
        return repositorio.obtenerGruposConInscripcionDisponibleNativo();
    }
    
    @GetMapping("/gruposInstructor")
    public ResponseEntity<List<GrupoEntidad>> obtnerGruposInscripcion(@RequestParam String idInstructor) {
        List<GrupoEntidad> resultado = repositorio.findByIdInstructor(idInstructor);
        return new ResponseEntity<>(resultado,HttpStatus.OK);
    }
    
}
