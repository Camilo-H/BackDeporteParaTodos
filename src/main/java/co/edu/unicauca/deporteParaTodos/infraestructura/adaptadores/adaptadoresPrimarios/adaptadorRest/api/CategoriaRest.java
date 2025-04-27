package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.v2DTO.V2CategoriaDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CategoriaCursoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICategoriaCursoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IImagenRepositorio;
import io.micrometer.core.ipc.http.HttpSender.Response;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class CategoriaRest {
    @Autowired
    private ICategoriaCursoRepositorio repositorioCategoria;

    @Autowired
    private IImagenRepositorio repositorioImagen;
    
    @GetMapping("/categorias")
    public Iterable<CategoriaCursoEntidad> obtenerCategorias(){
        return repositorioCategoria.findAll();
    }

    @GetMapping("/categorias2")
    public Iterable<CategoriaCursoEntidad> obtenerCategoriasExistentes(){
        return repositorioCategoria.findByEliminado(0);
    }

    @PostMapping("/categoria")
    public ResponseEntity<CategoriaCursoEntidad> postCategoria(@RequestBody V2CategoriaDTO dto) {
        CategoriaCursoEntidad entidad = new CategoriaCursoEntidad(dto.getTitulo(), dto.getDescripcion(), dto.getImagenId(), 0);
        CategoriaCursoEntidad respuesta = repositorioCategoria.save(entidad);
        if(repositorioCategoria.existsById(entidad.getTitulo())){
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    
    @PutMapping("/categoria")
    public ResponseEntity<V2CategoriaDTO> putCategoria(@RequestParam String titulo, @RequestBody V2CategoriaDTO dto) {
        boolean existe = repositorioCategoria.existsById(titulo);
        if(!existe){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CategoriaCursoEntidad entidad = new CategoriaCursoEntidad(titulo, dto.getDescripcion(), dto.getImagenId(), 0);
        CategoriaCursoEntidad entidadGuardada;
        entidadGuardada = repositorioCategoria.save(entidad);
        V2CategoriaDTO respuesta = new V2CategoriaDTO(entidad.getTitulo(), entidadGuardada.getDescripcion(), entidadGuardada.getCat_imagen());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/categoria")
    public ResponseEntity<Integer> deleteCategoria(@RequestParam String titulo){
        Optional<CategoriaCursoEntidad> op = repositorioCategoria.findById(titulo);
        if(op.isEmpty()){
            return new ResponseEntity<>(0,HttpStatus.NOT_FOUND);
        }
        CategoriaCursoEntidad entidad = op.get();
        if(entidad.getEliminado()==1){
            return new ResponseEntity<>(0, HttpStatus.OK);
        }
        //Eliminar imagen de la categoria -> las imagenes por ser multimedia si se borran no tiene dependencias mas abajo en la cascada
        if(entidad.getCat_imagen()!=null){
            repositorioImagen.deleteById(entidad.getCat_imagen());
        }
        //marcar eliminada categoria,
        String argTitulo = entidad.getTitulo();
        int filasAfectadas= repositorioCategoria.marcarComoEliminado(argTitulo);
        if(filasAfectadas==1){
            return new ResponseEntity<>(filasAfectadas, HttpStatus.OK);
        }
        return ResponseEntity.internalServerError().body(filasAfectadas);
    }
}
