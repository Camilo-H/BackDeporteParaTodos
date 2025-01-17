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
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IPerfilServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Perfil;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.ImagenDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.PerfilDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Base64;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@RequestMapping("api")
@Validated
public class PerfilRestControlador {

    @Autowired
    private IPerfilServicio servicio;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @GetMapping("/perfiles")
    public ResponseEntity<List<PerfilDTO>> obtenerPerfiles() {
        List<Perfil> resultado = servicio.obtenerPerfiles();
        List<PerfilDTO> listDTO = mapper.map(resultado, new TypeToken<List<PerfilDTO>>() {
        }.getType());
        ResponseEntity<List<PerfilDTO>> respuesta = new ResponseEntity<>(listDTO, HttpStatus.OK);
        return respuesta;
    }

    @PostMapping("/perfiles/{id}")
    public ResponseEntity<PerfilDTO> isertarPerfil(@RequestBody PerfilDTO perfilDTO) {
        Perfil perfilModelo = mapper.map(perfilDTO, Perfil.class);
        if (perfilDTO.getPerf_imagen() != null) {
            ImagenDTO imagenDTO = perfilDTO.getPerf_imagen();
            Imagen imagenModelo = new Imagen();
            imagenModelo.setNombre(imagenDTO.getNombre());
            imagenModelo.setTipoArchivo(imagenDTO.getTipoArchivo());
            imagenModelo.setLongitud(imagenDTO.getLongitud());
            // Convertir los datos base64 de la imagen a byte[]
            byte[] datosImagen = Base64.getDecoder().decode(imagenDTO.getDatos());
            imagenModelo.setDatos(datosImagen);
            perfilModelo.setPerf_imagen(imagenModelo);
        }
        Perfil perfilInsertado = servicio.insertarPerfil(perfilModelo);
        PerfilDTO respuestaDTO = mapper.map(perfilInsertado, PerfilDTO.class);
        return new ResponseEntity<>(respuestaDTO, HttpStatus.CREATED);
    }

    @GetMapping("/perfiles/{id}")
    public ResponseEntity<PerfilDTO> obtenerPerfil(@RequestParam String id) {
        Perfil respuesta = servicio.obtenerPerfil(id);
        if (respuesta == null) {
            throw new NoExisteExcepcion("No existe el perfil con el identificador " + id);
        }
        PerfilDTO respuestaDto = mapper.map(respuesta, PerfilDTO.class);
        return new ResponseEntity<>(respuestaDto, HttpStatus.OK);
    }

    @PutMapping("/perfiles/{id}")
    public ResponseEntity<PerfilDTO> actualizarPerfil(@PathVariable String id,
            @Valid @RequestBody PerfilDTO datosPerfil) {
        Perfil perfilExistente = servicio.obtenerPerfil(id);
        if (perfilExistente == null) {
            throw new NoExisteExcepcion("No existe el perfil con el identificador " + id);
        }
        Perfil modeloPerfil = mapper.map(datosPerfil, Perfil.class);
        if (datosPerfil.getPerf_imagen() != null) {
            ImagenDTO imagenDTO = datosPerfil.getPerf_imagen();
            Imagen imagenModelo = new Imagen();
            imagenModelo.setNombre(imagenDTO.getNombre());
            imagenModelo.setTipoArchivo(imagenDTO.getTipoArchivo());
            imagenModelo.setLongitud(imagenDTO.getLongitud());
            // Convertir los datos base64 de la imagen a byte[]
            byte[] datosImagen = Base64.getDecoder().decode(imagenDTO.getDatos());
            imagenModelo.setDatos(datosImagen);
            modeloPerfil.setPerf_imagen(imagenModelo);
        }
        Perfil perfilActualizado = servicio.actualizarPerfil(id, modeloPerfil);
        PerfilDTO respuestaDto = mapper.map(perfilActualizado, PerfilDTO.class);
        return new ResponseEntity<>(respuestaDto, HttpStatus.OK);
    }

    @DeleteMapping("/perfiles/{id}")
    public ResponseEntity<PerfilDTO> borrarPerfil(@PathVariable String id) {
        Perfil respuesta = servicio.obtenerPerfil(id);
        if (respuesta == null) {
            throw new NoExisteExcepcion("No existe el perfil con el identificador " + id);
        }
        Perfil perfilEliminado = servicio.eliminarPerfil(id);
        PerfilDTO respuestaDto = mapper.map(perfilEliminado, PerfilDTO.class);
        return new ResponseEntity<>(respuestaDto, HttpStatus.OK);
    }
}
