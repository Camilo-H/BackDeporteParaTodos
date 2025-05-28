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
    
    /**
     * Expone todas las categorias existentes en el sistema
     * TODO: No se debe usar para uso regular de los usuario puesto que no se filtra su contenido.
     * @return
     */
    @GetMapping("/categorias")
    public Iterable<CategoriaCursoEntidad> obtenerCategorias(){
        return repositorioCategoria.findAll();
    }
    
    /***
     * Expone todas las categorias existentes en el sistema
     * que se encuentren disponibles
     * usese para exponer estas entidades a los usuarios finales.
     * @return TODO: eventualmente debe retornar una lista de DTO, no entities
     */
    @GetMapping("/categorias2")
    public Iterable<CategoriaCursoEntidad> obtenerCategoriasExistentes(){
        return repositorioCategoria.findByEliminado(0);
    }

    /***
     * Inserta una categoria en el sistema
     * La dependencia en la categoria asociada a la imagen debe ser gestionada previamente.
     * TODO: opcionalmente se puede crear una sobrecarga que gestione ambas cosas en una peticion.
     * @param dto informacion a guardar TODO: aplicar restricciones al dto para integridad de los datos.
     * @return Retorna la categoria guardada en caso de exito, estado conflict en caso de fallo.
     */
    @PostMapping("/categoria")
    public ResponseEntity<CategoriaCursoEntidad> postCategoria(@RequestBody V2CategoriaDTO dto) {
        CategoriaCursoEntidad entidad = new CategoriaCursoEntidad(dto.getTitulo(), dto.getDescripcion(), dto.getImagenId(), 0);
        CategoriaCursoEntidad respuesta = repositorioCategoria.save(entidad);
        if(repositorioCategoria.existsById(entidad.getTitulo())){
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    
    //TODO: revisar algoritmo
    /***
     * Actualiza la informacion de una cateogira idendificada con "titulo"
     * @param titulo identificador de la categoria
     * @param dto informacion actualizada de la categoria.
     * @return retorna un objeto dto con el contenido actualizado, codigo not found en caso de no encontrar el objetivo de actualizacion.
     */
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

    /***
     * Para una categoria identificada con "titulo", cambia el estado para que no sea accesible la categoria en cuestion.
     * @param titulo identificador de la categoria.
     * @return cantidad de filas afectadas en la peticion, 1 representa exito en la operacion.
     */
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
