package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IImagenServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.ImagenDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.logs.PeticionLogger;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("api/v2")
@Validated
public class ImagenRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImagenRest.class);

    @Autowired
    private IImagenServicio servicio;

    /***
     * en este endpoint es necesario usar @ModelAtribute para que se puedan recibir los datos por formdata y se mapee el file como Multipartfile
     * @param entidad
     * @return
     * @throws IOException 
     */
    @PostMapping("/imagenMultipart")
    public ResponseEntity<ImagenDto> postInsertImagen(@ModelAttribute ImagenDto entidad) throws IOException {
        PeticionLogger.log(LOGGER, "POST", "/api/v2/imagenMultipart", entidad);
        ImagenDto dto = servicio.insertarImagen(entidad);
        return new ResponseEntity<ImagenDto>(dto,HttpStatus.CREATED);
    }

    @GetMapping("/imagen")
    public ResponseEntity<ImagenDto> getObtenerImagen(@RequestParam int idImagen) {
        PeticionLogger.log(LOGGER, "GET", "/api/v2/imagen", "idImagen=" + idImagen);
        ImagenDto dto = servicio.obtenerImagen(idImagen);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    
    @GetMapping("/imagenStream")
    public ResponseEntity<byte[]> getMethodName(@RequestParam int idImagen) {
        PeticionLogger.log(LOGGER, "GET", "/api/v2/imagenStream", "idImagen=" + idImagen);
        ImagenDto dto = servicio.obtenerImagen(idImagen);
        byte[] datos = Base64.getDecoder().decode(dto.getDatosBase64());
        String tipoMime = dto.getTipoArchivo();
        if(tipoMime==null || tipoMime.isEmpty()){
            tipoMime = "image/jpeg";
        }
        MediaType mediaType = MediaType.IMAGE_JPEG;
        mediaType = MediaType.parseMediaType(tipoMime);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .contentLength(datos.length)
                .body(datos);
    }

    @GetMapping("/imagenes")
    public ResponseEntity<List<ImagenDto>> getMethodName() {
        PeticionLogger.log(LOGGER, "GET", "/api/v2/imagenes", "sin datos");
        List<ImagenDto> lista = servicio.obtenerImagenes();
        return new ResponseEntity<List<ImagenDto>>(lista, HttpStatus.OK);
    }
    
    
    
}
