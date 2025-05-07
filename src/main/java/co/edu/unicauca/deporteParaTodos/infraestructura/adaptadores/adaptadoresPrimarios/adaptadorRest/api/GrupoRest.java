package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.v2DTO.V2GrupoDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.v2DTO.V2InstructorDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.GrupoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICategoriaCursoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IGrupoRepositorio;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class GrupoRest {
    @Autowired
    private IGrupoRepositorio repositorio;

    @Autowired 
    private ICategoriaCursoRepositorio repoCategoria;

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

    @PostMapping("/grupo")
    public ResponseEntity<GrupoEntidad> postGrupo(@RequestBody V2GrupoDTO dto) {
        List<GrupoEntidad> entidades = repositorio.findByCategoriaAndCursoAndEliminado(dto.getCat_titulo(), dto.getCur_nombre(), 0);
        int pivote = 0;
        for (GrupoEntidad grupoEntidad : entidades) {
            int temporal = grupoEntidad.getIterable();
            if(pivote < temporal){
                pivote = temporal;
            }
        }
        GrupoEntidad entidad = new GrupoEntidad();
        entidad.setCategoria(dto.getCat_titulo());
        entidad.setCurso(dto.getCur_nombre());
        entidad.setAnio(dto.getAnio());
        entidad.setIterable(pivote+1);
        entidad.setCupos(dto.getCupos());
        entidad.setEliminado(0);
        entidad.setFechaCreacion(Date.valueOf(dto.getFechaCreacion()));
        entidad.setIdInstructor(dto.getIdInstructor());
        entidad.setImagenGrupo(dto.getImagen());
        
        repositorio.save(entidad);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    
}
