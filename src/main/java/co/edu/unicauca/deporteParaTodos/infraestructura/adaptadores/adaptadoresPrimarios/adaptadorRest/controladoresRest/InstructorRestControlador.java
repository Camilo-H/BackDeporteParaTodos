package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.controladoresRest;

import java.util.Base64;
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
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IInstructorServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Instructor;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.ImagenDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.InstructorDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@RequestMapping("api")
@Validated
public class InstructorRestControlador {

    @Autowired
    private IInstructorServicio servicio;

    // Use mapper generico para conversiones directas en los tipos
    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @GetMapping("/instructores")
    public ResponseEntity<List<InstructorDTO>> obtenerInstructores() {
        List<Instructor> resultado = servicio.obtenerInstructores();
        List<InstructorDTO> listDTO = mapper.map(resultado, new TypeToken<List<InstructorDTO>>() {
        }.getType());
        ResponseEntity<List<InstructorDTO>> respuesta = new ResponseEntity<List<InstructorDTO>>(listDTO, HttpStatus.OK);
        return respuesta;
    }

    @PostMapping("/instructores")
    public ResponseEntity<InstructorDTO> insertarInstructor(@Valid @RequestBody InstructorDTO datosInstructor) {
        Instructor modeloInstructor = mapper.map(datosInstructor, Instructor.class);
        if (datosInstructor.getPerfil().getPerf_imagen() != null) {
            Imagen imagen = new Imagen();
            ImagenDTO imagenDto = datosInstructor.getPerfil().getPerf_imagen();
            imagen.setNombre(imagenDto.getNombre());
            imagen.setTipoArchivo(imagenDto.getTipoArchivo());
            imagen.setLongitud(imagenDto.getLongitud());
            byte[] datosImagen = Base64.getDecoder().decode(imagenDto.getDatos());
            imagen.setDatos(datosImagen);
            modeloInstructor.getPerfil().setPerf_imagen(imagen);
        }
        Instructor nuevoInstructor = servicio.insertarInstructor(modeloInstructor);
        InstructorDTO nuevoInstructorDTO = mapper.map(nuevoInstructor, InstructorDTO.class);
        return new ResponseEntity<>(nuevoInstructorDTO, HttpStatus.CREATED);
    }

    @GetMapping("/instructores/{id}")
    public ResponseEntity<InstructorDTO> obtenerInstructor(@PathVariable String id) {
        Instructor instructorExistente = servicio.obtenerInstructor(id);
        if (instructorExistente == null) {
            throw new NoExisteExcepcion("No existe el instructor con el identificador " + id);
        }
        InstructorDTO respuestaDTO = mapper.map(instructorExistente, InstructorDTO.class);
        return new ResponseEntity<InstructorDTO>(respuestaDTO, HttpStatus.OK);
    }

    @PutMapping("/instructores/{id}")
    public ResponseEntity<InstructorDTO> actualizarInstructor(@PathVariable String id,
            @Valid @RequestBody InstructorDTO datosInstructorDto) {
        Instructor instrucotrExistente = servicio.obtenerInstructor(id);
        if (instrucotrExistente == null) {
            throw new NoExisteExcepcion("No existe el instructor con el identificador " + id);
        }

        Instructor modeloInstructor = mapper.map(datosInstructorDto, Instructor.class);
        modeloInstructor.getPerfil().setPerf_nombre(datosInstructorDto.getPerfil().getPerf_nombre());
        modeloInstructor.getPerfil().setPerf_correo(datosInstructorDto.getPerfil().getPerf_correo());
        modeloInstructor.getPerfil().setPerf_tipo(datosInstructorDto.getPerfil().getPerf_tipo());
        modeloInstructor.getPerfil().setPerf_Sexo(datosInstructorDto.getPerfil().getPerf_Sexo());
        if (datosInstructorDto.getPerfil().getPerf_imagen() != null) {
            ImagenDTO imagenDTO = datosInstructorDto.getPerfil().getPerf_imagen();
            Imagen imagen = new Imagen();
            imagen.setNombre(imagenDTO.getNombre());
            imagen.setTipoArchivo(imagenDTO.getTipoArchivo());
            imagen.setLongitud(imagenDTO.getLongitud());
            byte[] datosImagen = Base64.getDecoder().decode(imagenDTO.getDatos());
            imagen.setDatos(datosImagen);
            modeloInstructor.getPerfil().setPerf_imagen(imagen);
        }
        Instructor insActualizado = servicio.actualizarInstructor(id, modeloInstructor);
        InstructorDTO respuesta = mapper.map(insActualizado, InstructorDTO.class);

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/instructores/{id}")
    public ResponseEntity<InstructorDTO> eliminarInstructor(@PathVariable String id) {
        Instructor instrucotrExistente = servicio.obtenerInstructor(id);
        if (instrucotrExistente == null) {
            throw new NoExisteExcepcion("No existe el instructor con el identificador " + id);
        }
        Instructor instructorEliminar = servicio.eliminarInstructor(id);
        InstructorDTO respuestaDto = mapper.map(instructorEliminar, InstructorDTO.class);
        return new ResponseEntity<>(respuestaDto, HttpStatus.OK);
    }

}
