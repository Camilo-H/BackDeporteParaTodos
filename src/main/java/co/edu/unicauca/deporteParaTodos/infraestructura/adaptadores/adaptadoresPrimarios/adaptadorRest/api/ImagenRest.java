package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IImagenServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.ImagenDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ImagenEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IImagenRepositorio;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class ImagenRest {

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
        ImagenDto dto = servicio.insertarImagen(entidad);
        return new ResponseEntity<ImagenDto>(dto,HttpStatus.CREATED);
    }

    @GetMapping("/imagen")
    public ResponseEntity<ImagenDto> getObtenerImagen(@RequestParam int idImagen) {
        ImagenDto dto = servicio.obtenerImagen(idImagen);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    
    @GetMapping("/imagenStream")
    public ResponseEntity<byte[]> getMethodName(@RequestParam int idImagen) {
        ImagenDto dto = servicio.obtenerImagen(idImagen);
        byte[] datos = dto.getDatosBase64().getBytes();
        String tipoMime = dto.getTipoArchivo();
        if(tipoMime==null || tipoMime.isEmpty()){
            tipoMime = "image/jpeg";
        }
        MediaType mediaType = MediaType.IMAGE_JPEG;
        mediaType = MediaType.parseMediaType(tipoMime);

        return ResponseEntity.ok().contentType(mediaType).body(datos);
    }

    @GetMapping("/imagenes")
    public ResponseEntity<List<ImagenDto>> getMethodName() {
        List<ImagenDto> lista = servicio.obtenerImagenes();
        return new ResponseEntity<List<ImagenDto>>(lista, HttpStatus.OK);
    }
    
    
    
}
