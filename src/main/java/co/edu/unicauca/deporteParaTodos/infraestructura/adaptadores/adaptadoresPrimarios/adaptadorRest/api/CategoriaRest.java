package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.util.ArrayList;
import java.util.List;
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

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICategoriaCursoServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;
import co.edu.unicauca.deporteParaTodos.dominio.servicios.CategoriaCursoServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.v2DTO.V2CategoriaDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.CategoriaDto;
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

    private final CategoriaCursoServicio categoriaCursoServicio;
    //TODO: eliminar dependencia del repositorio cuando finalice la normalizacion
    @Autowired
    private ICategoriaCursoRepositorio repositorioCategoria;

    //TODO: eliminar depenciencia del repositorio cuando finalice
    @Autowired
    private IImagenRepositorio repositorioImagen;

    @Autowired
    private ICategoriaCursoServicio servicioCategoria;

    CategoriaRest(CategoriaCursoServicio categoriaCursoServicio) {
        this.categoriaCursoServicio = categoriaCursoServicio;
    }
    
    /**
     * Expone todas las categorias existentes en el sistema
     * TODO: No se debe usar para uso regular de los usuario puesto que no se filtra su contenido.
     * @return
     */
    @Deprecated
    @GetMapping("/categorias")
    public Iterable<CategoriaCursoEntidad> obtenerCategorias(){
        return repositorioCategoria.findAll();
    }
    
    /***
     * Expone todas las categorias existentes en el sistema
     * que se encuentren disponibles
     * usese para exponer estas entidades a los usuarios finales.
     * @return Lista de entidades formato DTO
     */
    @GetMapping("/categorias2")
    public ResponseEntity<List<CategoriaDto>> obtenerCategoriasExistentes(){        

        List<CategoriaDto> listaDtos = servicioCategoria.recuperarCategoriasCurso();
        return new ResponseEntity<>(listaDtos, HttpStatus.OK);

    }

    /***
     * Inserta una categoria en el sistema
     * La dependencia en la categoria asociada a la imagen debe ser gestionada previamente.
     * TODO: opcionalmente se puede crear una sobrecarga que gestione ambas cosas en una peticion.
     * @param dto informacion a guardar TODO: aplicar restricciones al dto para integridad de los datos.
     * @return Retorna la categoria guardada en caso de exito, estado conflict en caso de fallo.
     */
    @PostMapping("/categoria")
    public ResponseEntity<CategoriaDto> postCategoria(@RequestBody CategoriaDto dto) {
        CategoriaDto respuesta = servicioCategoria.insertarCategoria(dto);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }
    
    //TODO: revisar algoritmo
    /***
     * Actualiza la informacion de una cateogira idendificada con "titulo"
     * El uso de este metodo requiere la que el id de imagen anexado ya exista en el sistema
     * @param titulo identificador de la categoria
     * @param dto informacion actualizada de la categoria.
     * @return retorna un objeto dto con el contenido actualizado, codigo not found en caso de no encontrar el objetivo de actualizacion.
     */
    @PutMapping("/categoria")
    public ResponseEntity<CategoriaDto> putCategoria(@RequestParam String titulo, @RequestBody CategoriaDto dto) {
        CategoriaDto respuesta = servicioCategoria.actualizarCategoria(titulo, dto);
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
