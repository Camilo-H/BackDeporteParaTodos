package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.controladoresRest;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICategoriaCursoServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.CategoriaDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.ImagenDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.mappers.MapperImagen;
import jakarta.validation.Valid;

import java.util.Base64;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
        List<Categoria> resultado = servicio.recuperarCategoriasCurso();
        List<CategoriaDTO> listDTO = mapper.map(resultado, new TypeToken<List<CategoriaDTO>>() {
        }.getType());
        ResponseEntity<List<CategoriaDTO>> response;
        response = new ResponseEntity<List<CategoriaDTO>>(listDTO, HttpStatus.OK);
        return response;
    }

    // @RequestPart("categoriaDTO") CategoriaDTO categoriaDTO,
    // @RequestPart("imagen") MultipartFile imagen

    // @RequestBody CategoriaDTO categoriaDTO, @ModelAttribute MultipartFile imagen
    @PostMapping("/categorias")
    public ResponseEntity<CategoriaDTO> insertarCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        try {
            // Convertir CategoriaDTO a modelo Categoria usando ModelMapper
            Categoria categoriaModelo = mapper.map(categoriaDTO, Categoria.class);
            // Verificar si CategoriaDTO incluye la imagen
            if (categoriaDTO.getImagen() != null) {
                Imagen imagenModelo = new Imagen();
                ImagenDTO imagenDTO = categoriaDTO.getImagen();

                MultipartFile im= mapper.map(categoriaDTO.getImagen(), MultipartFile.class);
                imagenDTO = MapperImagen.multiparfileToImagenDTO(im);
                
                imagenModelo = mapper.map(imagenDTO, Imagen.class);
                categoriaModelo.setImagen(imagenModelo);
                Categoria categoriaInsertada = servicio.insertarCategoria(categoriaModelo);
                CategoriaDTO respuestaDTO = mapper.map(categoriaInsertada, CategoriaDTO.class);
                return new ResponseEntity<>(respuestaDTO, HttpStatus.CREATED);
            }

            Categoria categoriaInsertada = servicio.insertarCategoria(categoriaModelo);
            CategoriaDTO respuestaDTO = mapper.map(categoriaInsertada, CategoriaDTO.class);
            return new ResponseEntity<>(respuestaDTO, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/categorias/{titulo}")
    public ResponseEntity<CategoriaDTO> obtenerCategoriaCurso(@PathVariable String titulo) {
        System.out.println("Me llego peticion de eliminar con el parámetro "+titulo);
        Categoria respuesta = servicio.obtenerCategoriaCursoPorId(titulo);

        if (respuesta == null) {
            throw new NoExisteExcepcion("La categoría con el título " + titulo + " no existe.");
        }
        CategoriaDTO respuestaDTO = mapper.map(respuesta, CategoriaDTO.class);
        return new ResponseEntity<CategoriaDTO>(respuestaDTO, HttpStatus.OK);
    }

    @PutMapping("/categorias/{titulo}")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@PathVariable String titulo,
            @Valid @RequestBody CategoriaDTO categoria) {
        Categoria categoriaModelo = mapper.map(categoria, Categoria.class);
        Categoria categoriaActualizada = servicio.actualizarCategoria(titulo, categoriaModelo);

        if (categoriaActualizada == null) {
            // response = new ResponseEntity<CategoriaDTO>(respuestaDTO,
            // HttpStatus.NOT_MODIFIED);
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        CategoriaDTO respuestaDTO = mapper.map(categoriaActualizada, CategoriaDTO.class);
        return new ResponseEntity<CategoriaDTO>(respuestaDTO, HttpStatus.OK);
    }

    @DeleteMapping("/categorias/{titulo}")
    public ResponseEntity<CategoriaDTO> deleteCategoria(@PathVariable String titulo) {
        System.out.println("Me llego peticion de eliminar con el parámetro "+titulo);
        Categoria respuesta = servicio.obtenerCategoriaCursoPorId(titulo);
        if (respuesta == null) {
            throw new NoExisteExcepcion("La categoría con el título " + titulo + " no existe.");
        }
        Categoria categoriaEliminada = servicio.eliminarCategoria(titulo);
        CategoriaDTO categoriaEliminadaDTO = mapper.map(categoriaEliminada, CategoriaDTO.class);
        return new ResponseEntity<CategoriaDTO>(categoriaEliminadaDTO, HttpStatus.OK);
    }

    /*if (imagen != null && !imagen.isEmpty()) {
                Imagen imagenModelo = new Imagen();
                ImagenDTO imagenDTO = categoriaDTO.getImagen();
                imagenDTO = MapperImagen.multiparfileToImagenDTO(imagen);
                imagenModelo = mapper.map(imagenDTO, Imagen.class);
                categoriaModelo.setImagen(imagenModelo);
                Categoria categoriaInsertada = servicio.insertarCategoria(categoriaModelo);
                CategoriaDTO respuestaDTO = mapper.map(categoriaInsertada, CategoriaDTO.class);
                return new ResponseEntity<>(respuestaDTO, HttpStatus.CREATED);
            } */
}
