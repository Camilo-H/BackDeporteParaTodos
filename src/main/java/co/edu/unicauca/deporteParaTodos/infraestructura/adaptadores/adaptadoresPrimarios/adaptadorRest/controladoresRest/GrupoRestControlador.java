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
        List<Grupo> grupos = servicio.obtenerGrupos();
        List<GrupoDTO> listGrupoDTOs = mapper.map(grupos, new TypeToken<List<GrupoDTO>>() {
        }.getType());
        return new ResponseEntity<>(listGrupoDTOs, HttpStatus.OK);
    }

    @GetMapping("/grupos/{nombre}/{anio}/{iterable}")
    public ResponseEntity<GrupoDTO> obteneGrupo(@PathVariable String nombre, @PathVariable int anio,
            @PathVariable int iterable) {
        Grupo grupo = servicio.obtenerGrupoPorId(nombre, anio, iterable);
        if (grupo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        GrupoDTO grupoDTO = mapper.map(grupo, GrupoDTO.class);
        return new ResponseEntity<>(grupoDTO, HttpStatus.OK);
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
                grupo.setImagen(imagenModelo);
            }
            Grupo nuevoGrupo = servicio.insertarGrupo(grupo);
            GrupoDTO nuevoGrupoDTO = mapper.map(nuevoGrupo, GrupoDTO.class);
            return new ResponseEntity<>(nuevoGrupoDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/grupos/{nombre}/{anio}/{iterable}")
    public ResponseEntity<GrupoDTO> actualizarGrupo(@PathVariable String nombre, @PathVariable int anio,
            @PathVariable int iterable,
            @Valid @RequestBody GrupoDTO grupoDTO) {

        Grupo grupoExistente = servicio.obtenerGrupoPorId(nombre, anio, iterable);
        if (grupoExistente == null) {
            throw new NoExisteExcepcion("El grupo no el nombre " + nombre + "no existe en el sistema");
        }
        // Guardar los cambios en la base de datos a través del servicio
        Grupo grupoActualizado = servicio.actualizarGrupo(nombre, anio, iterable, grupoExistente);

        // Mapear la entidad actualizada de vuelta a un DTO (si usas ModelMapper)
        GrupoDTO grupoActualizadoDTO = mapper.map(grupoActualizado, GrupoDTO.class);

        // Devolver el grupo actualizado con un estado HTTP 200 OK
        return new ResponseEntity<>(grupoActualizadoDTO, HttpStatus.OK);
    }

    @DeleteMapping("/grupos/{nombre}/{anio}/{iterable}")
    public ResponseEntity<GrupoDTO> eliminarGrupo(@PathVariable String nombre, @PathVariable int anio,
            @PathVariable int iterable) {

        Grupo grupoExistente = servicio.obtenerGrupoPorId(nombre, anio, iterable);
        if (grupoExistente == null) {
            throw new NoExisteExcepcion("El grupo con el nombre " + nombre + " y anio " + anio + " no se encuentra.");
        }
        Grupo grupoEliminado = servicio.eliminarGrupo(nombre, anio, iterable);
        GrupoDTO grupoEliminadoDTO = mapper.map(grupoEliminado, GrupoDTO.class);
        return new ResponseEntity<GrupoDTO>(grupoEliminadoDTO, HttpStatus.OK);
    }

}
