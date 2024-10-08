package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.controladoresRest;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IImagenServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.ImagenDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.mappers.MapperImagen;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
//Notacion a nivel de clase para validacion de los argumentos de los endpoints
@Validated
@RequestMapping("api")
public class ImagenRestControlador {
    
    @Autowired
    private IImagenServicio servicio;

    
    //Use mapper generico para conversiones directas en los tipos
    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    /***
     * Obtiene todas las imagenes en la base de datos
     * @return Lista obtenida o json error 
     */
    @GetMapping("imagenes")
    public ResponseEntity<List<ImagenDTO>> getImagenes() {
        List<Imagen> resultado = servicio.obtenerImagenes();
        //se mapean los tipos de datos de Imagen a ImagenDTO para el retorno.
        List<ImagenDTO> listDTO = mapper.map(resultado, new TypeToken<List<ImagenDTO>>(){}.getType());
        //objeto de retorno
        ResponseEntity<List<ImagenDTO>> response;
        response = new ResponseEntity<List<ImagenDTO>>(listDTO,HttpStatus.OK);
        return response;
    }

    @GetMapping("imagen/{id}")
    public ResponseEntity<ImagenDTO> getImagen(@PathVariable Integer id){
        Imagen respuesta = servicio.obtenerImagen(id);
        ResponseEntity<ImagenDTO> response;
        if(respuesta==null){
            response = new ResponseEntity<ImagenDTO>(new ImagenDTO(),HttpStatus.NO_CONTENT);
            return response;
        }
        //mapeo mediante ModelMapper
        ImagenDTO respuestaDTO = mapper.map(respuesta, ImagenDTO.class);
        response = new ResponseEntity<ImagenDTO>(respuestaDTO, HttpStatus.OK);
        return response;
    }
    /***
     * Recibe un archivo imagen sin ser codificado, que es recibido como Multipartfile
     * @param imagen archivo
     * @return
     */
    @PostMapping("imagenfile")
    public ResponseEntity<ImagenDTO> postImagen(@ModelAttribute MultipartFile imagen) {
        Imagen imagenModelo = new Imagen();
        ImagenDTO imagenDTO = new ImagenDTO();
        ResponseEntity<ImagenDTO> response = null;

        //Convertir Multiparfil a tipo de dato ImagenDTO, Mapeo manual
        imagenDTO = MapperImagen.multiparfileToImagenDTO(imagen);
        //Convertir ImagenDTO a Imagen para usar el servicio, mendiante ModelMapper
        imagenModelo = mapper.map(imagenDTO, Imagen.class);
        Imagen respuesta = servicio.insertarImagen(imagenModelo);
        //Convertir respuesta a ImagenDTO para responder el endpoint, uso de ModelMapper
        ImagenDTO respuestaDTO = mapper.map(respuesta, ImagenDTO.class);
        if(respuesta==null){
            response = new ResponseEntity<ImagenDTO>(respuestaDTO, HttpStatus.NOT_MODIFIED);
            return response;
        }
        response = new ResponseEntity<ImagenDTO>(respuestaDTO,HttpStatus.CREATED);
        return response;
    }

    @DeleteMapping("imagen/{id}")
    public ResponseEntity<ImagenDTO> deleteImagen(@PathVariable Integer id){
        Imagen imagenEliminada = null;
        ResponseEntity<ImagenDTO> response = null;
        imagenEliminada = servicio.eliminarImagen(id);
        if(imagenEliminada==null){
            response = new ResponseEntity<>(null,HttpStatus.NOT_MODIFIED);
            return response;
        }
        ImagenDTO imagenEliminadaDTO = mapper.map(imagenEliminada, ImagenDTO.class);
        response = new ResponseEntity<>(imagenEliminadaDTO, HttpStatus.OK);
        return response;
    }

}
