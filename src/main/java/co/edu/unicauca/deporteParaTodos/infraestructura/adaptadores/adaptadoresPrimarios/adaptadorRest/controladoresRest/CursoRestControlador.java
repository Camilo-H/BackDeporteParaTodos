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
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICursoServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Curso;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.CursoDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.ImagenDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.InsercionFallidaExepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api")
@CrossOrigin(origins= {"*"}, maxAge = 4200, allowCredentials = "false")
@Validated
public class CursoRestControlador {
    
    @Autowired
    private ICursoServicio servicio;

    //Use mapper generico para conversiones directas en los tipos
    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    //Obtener lista de cursos
    @GetMapping("/cursos")
    public ResponseEntity<List<CursoDTO>> obtenerCursos() {
        List<Curso> resusltado = servicio.recuperarCursos();
        List<CursoDTO> listDTO = mapper.map(resusltado, new TypeToken<List<CursoDTO>>(){}.getType());
        return new ResponseEntity<List<CursoDTO>>(listDTO, HttpStatus.OK);
    }
    
    //Insertar un nuevo curso
    @PostMapping("/cursos")
    public ResponseEntity<CursoDTO> insertarCurso(@ModelAttribute CursoDTO datosCurso) {
        //TODO: process POST request
        try{
            Curso cursoModelo = mapper.map(datosCurso, Curso.class);
            if(datosCurso.getImagen() !=null){
                ImagenDTO imagenDTO = datosCurso.getImagen();
                Imagen imagenModelo = new Imagen();
                imagenModelo.setNombre(imagenDTO.getNombre());
                imagenModelo.setTipoArchivo(imagenDTO.getTipoArchivo());
                imagenModelo.setLongitud(imagenDTO.getLongitud());
                // Convertir los datos base64 de la imagen a byte[]
                byte[] datosImagen = Base64.getDecoder().decode(imagenDTO.getDatos());
                imagenModelo.setDatos(datosImagen);
                cursoModelo.setObjImagen(imagenModelo);
            }
            Curso cursoInsertado = servicio.insertarCurso(cursoModelo);
            CursoDTO respuestaDto = mapper.map(cursoInsertado, CursoDTO.class);
            return new ResponseEntity<>(respuestaDto, HttpStatus.CREATED);
        }catch(InsercionFallidaExepcion ex){
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Obtener un curso
    @GetMapping("/cursos/{nombre}")
    public ResponseEntity<CursoDTO> obtenerCurso(@PathVariable String nombre) {
        Curso curso = servicio.obtenerCurso(nombre);
        if(curso== null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CursoDTO cursoDTO = mapper.map(curso, CursoDTO.class);
        return new ResponseEntity<>(cursoDTO, HttpStatus.OK);
    }
    
    //Actualizar un curso
    @PutMapping("/cursos/{nombre}")
    public ResponseEntity<CursoDTO> actualizarCurso(@PathVariable String nombre, @Valid @RequestBody CursoDTO daCursoDTO) {
        //TODO: process PUT request
        Curso cursoactual = servicio.obtenerCurso(nombre);
        if(cursoactual == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return null;
    }

    //Eliminar un curso
    @DeleteMapping("/cursos/{nombre}")
    public ResponseEntity<CursoDTO> eliminarCurso(@PathVariable String nombre){
        Curso respuesta = servicio.obtenerCurso(nombre);
        if(respuesta == null){
            throw new NoExisteExcepcion("El curso con el nombre "+ nombre+" no esxiste");
        }
        Curso cursoEliminado = servicio.eliminarCurso(nombre);
        CursoDTO respuestaDto = mapper.map(cursoEliminado, CursoDTO.class);
        return new ResponseEntity<CursoDTO>(respuestaDto, HttpStatus.OK);
    }
    
}