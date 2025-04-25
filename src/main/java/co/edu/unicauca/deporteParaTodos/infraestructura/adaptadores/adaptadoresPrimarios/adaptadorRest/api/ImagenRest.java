package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.core.util.ContentTypeUtil;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.v2DTO.V2ImagenDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ImagenEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IImagenRepositorio;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class ImagenRest {
    @Autowired
    private IImagenRepositorio repositorio;

    /***
     * en este endpoint es necesario usar @ModelAtribute para que se puedan recibir los datos por formdata y se mapee el file como Multipartfile
     * @param entidad
     * @return
     * @throws IOException 
     */
    @PostMapping("/imagenMultipart")
    public ResponseEntity<V2ImagenDTO> postInsertImagen(@ModelAttribute V2ImagenDTO entidad) throws IOException {
        System.out.println("existe"+entidad.toString());
        //contorlar la excepcion sobre los bytes
        ImagenEntidad entiti = new ImagenEntidad(null, entidad.getNombre(), entidad.getTipoArchivo(), entidad.getLongitud(), entidad.getDatosMultipartFile().getBytes(), 0);
        ImagenEntidad respuesta;
        respuesta = repositorio.save(entiti);
        V2ImagenDTO respuestaDto = V2ImagenDTO.fabricaFromImagenEntidad(respuesta);
        return new ResponseEntity<V2ImagenDTO>(respuestaDto,HttpStatus.CREATED);
    }

    @GetMapping("/imagen")
    public ResponseEntity<V2ImagenDTO> getObtenerImagen(@RequestParam int idImagen) {
        Optional<ImagenEntidad> opcional = repositorio.findById(idImagen);
        if(opcional.isPresent()){
            ImagenEntidad imagen = opcional.get();
            V2ImagenDTO dto = V2ImagenDTO.fabricaFromImagenEntidad(imagen);
            if(dto == null){
                return new ResponseEntity<>(HttpStatusCode.valueOf(500));
            }
            return new ResponseEntity<>(dto,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/imagenStream")
    public ResponseEntity<byte[]> getMethodName(@RequestParam int idImagen) {
        Optional<ImagenEntidad> op;
        op = repositorio.findById(idImagen);
        if(op.isEmpty() || op.get().getDatos()==null){
            return ResponseEntity.notFound().build();
        }
        byte[] datos = op.get().getDatos();
        String tipoMime = op.get().getTipoArchivo();
        if(tipoMime==null || tipoMime.isEmpty()){
            tipoMime = "image/jpeg";
        }
        MediaType mediaType = MediaType.IMAGE_JPEG;
        mediaType = MediaType.parseMediaType(tipoMime);

        return ResponseEntity.ok().contentType(mediaType).body(datos);
    }
    
    
}
