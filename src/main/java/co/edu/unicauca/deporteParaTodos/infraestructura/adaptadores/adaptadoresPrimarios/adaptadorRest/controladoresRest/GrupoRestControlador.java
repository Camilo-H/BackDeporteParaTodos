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
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IGrupoServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Grupo;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.GrupoDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.ImagenDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Base64;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;

@Hidden
@RestController
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@RequestMapping("api")
@Validated
public class GrupoRestControlador {

    @Autowired
    private IGrupoServicio servicio;

    // Use mapper generico para conversiones directas en los tipos
    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @GetMapping("/grupos")
    public ResponseEntity<List<GrupoDTO>> obtenerGrupos() {
        
        return null;
    }

    @GetMapping("/grupos/{nombre}/{anio}/{iterable}")
    public ResponseEntity<GrupoDTO> obteneGrupo(@PathVariable String nombre, @PathVariable int anio,
            @PathVariable int iterable) {
      
        return null;
    }

    @PostMapping("/grupos")
    public ResponseEntity<GrupoDTO> insertarGrupo(@Valid @RequestBody GrupoDTO grupoDTO) {
        Grupo grupo = mapper.map(grupoDTO, Grupo.class);
        try {
            if (grupoDTO.getImagen() != null) {
                ImagenDTO imagenDTO = grupoDTO.getImagen();
                Imagen imagenModelo = new Imagen();
                imagenModelo.setNombre(imagenDTO.getNombre());
                imagenModelo.setTipoArchivo(imagenDTO.getTipoArchivo());
                imagenModelo.setLongitud(imagenDTO.getLongitud());

                byte[] datosImagen = Base64.getDecoder().decode(imagenDTO.getDatos());
                imagenModelo.setDatos(datosImagen);
                //grupo.setImagen(imagenModelo);
            }
            //Grupo nuevoGrupo = servicio.insertarGrupo(grupo);
            //GrupoDTO nuevoGrupoDTO = mapper.map(nuevoGrupo, GrupoDTO.class);
            return null;
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/grupos/{nombre}/{anio}/{iterable}")
    public ResponseEntity<GrupoDTO> actualizarGrupo(@PathVariable String nombre, @PathVariable int anio,
            @PathVariable int iterable,
            @Valid @RequestBody GrupoDTO grupoDTO) {

       

        // Mapear la entidad actualizada de vuelta a un DTO (si usas ModelMapper)
      

        // Devolver el grupo actualizado con un estado HTTP 200 OK
        return null;
    }

    @DeleteMapping("/grupos/{nombre}/{anio}/{iterable}")
    public ResponseEntity<GrupoDTO> eliminarGrupo(@PathVariable String nombre, @PathVariable int anio,
            @PathVariable int iterable) {

       
       
        return null;
    }

}
