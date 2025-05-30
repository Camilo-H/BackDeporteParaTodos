package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.controladoresRest;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICategoriaCursoServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.CategoriaDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.peticion.CategoriaInDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class CategoriaCursoRestControlador {

    @Autowired
    private ICategoriaCursoServicio servicio;

    // Use mapper generico para conversiones directas en los tipos
    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaDTO>> recuperarCategoriasCurso() {
        /*List<Categoria> resultado = servicio.recuperarCategoriasCurso();
        List<CategoriaDTO> listDTO = mapper.map(resultado, new TypeToken<List<CategoriaDTO>>() {
        }.getType());
        ResponseEntity<List<CategoriaDTO>> response;
        response = new ResponseEntity<List<CategoriaDTO>>(listDTO, HttpStatus.OK);
        return response;*/
        return null;
    }

    @PostMapping("/categoriass")
    public ResponseEntity<CategoriaDTO> insertarCategoria(@Valid @ModelAttribute CategoriaInDTO categoriaDTO) {
        CategoriaDTO respuestaDTO = servicio.registrarCategoria(categoriaDTO);
        return new ResponseEntity<>(respuestaDTO, HttpStatus.CREATED);
    }

    @GetMapping("/categorias/{titulo}")
    public ResponseEntity<CategoriaDTO> obtenerCategoriaCurso(@PathVariable String titulo) {
        Categoria respuesta = servicio.obtenerCategoriaCursoPorId(titulo);
        if (respuesta == null) {
            throw new NoExisteExcepcion("La categoría con el título " + titulo + " no existe.");
        }
        CategoriaDTO respuestaDTO = mapper.map(respuesta, CategoriaDTO.class);
        return new ResponseEntity<CategoriaDTO>(respuestaDTO, HttpStatus.OK);
    }

    @PutMapping("/categorias/{titulo}")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@PathVariable String titulo,
            @Valid @ModelAttribute CategoriaInDTO categoria) {
        CategoriaDTO categoriaActualizada = servicio.actualizarCategoria(titulo, categoria);
        return new ResponseEntity<CategoriaDTO>(categoriaActualizada, HttpStatus.OK);
    }

    @DeleteMapping("/categorias/{titulo}")
    public ResponseEntity<CategoriaDTO> deleteCategoria(@PathVariable String titulo) {
        CategoriaDTO categoriaEliminada = servicio.eliminarCategoria(titulo);
        return new ResponseEntity<CategoriaDTO>(categoriaEliminada, HttpStatus.OK);
    }

}
