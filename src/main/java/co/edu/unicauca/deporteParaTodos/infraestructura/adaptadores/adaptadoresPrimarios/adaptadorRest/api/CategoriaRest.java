package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.v2DTO.V2CategoriaDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CategoriaCursoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICategoriaCursoRepositorio;
import io.micrometer.core.ipc.http.HttpSender.Response;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class CategoriaRest {
    @Autowired
    private ICategoriaCursoRepositorio repositorio;
    
    @GetMapping("/categorias")
    public Iterable<CategoriaCursoEntidad> obtenerCategorias(){
        return repositorio.findAll();
    }

    @GetMapping("/categorias2")
    public Iterable<CategoriaCursoEntidad> obtenerCategoriasExistentes(){
        return repositorio.findByEliminado(0);
    }

    @PostMapping("/categoria")
    public ResponseEntity<CategoriaCursoEntidad> postCategoria(@RequestBody V2CategoriaDTO dto) {
        CategoriaCursoEntidad entidad = new CategoriaCursoEntidad(dto.getTitulo(), dto.getDescripcion(), dto.getImagenId(), 0);
        CategoriaCursoEntidad respuesta = repositorio.save(entidad);
        if(repositorio.existsById(entidad.getTitulo())){
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    
}
