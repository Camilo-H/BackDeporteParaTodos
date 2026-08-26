package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IInscripcionServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Inscripcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.InscripcionDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.logs.PeticionLogger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("api/v2")
@Validated
public class InscripcionRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(InscripcionRest.class);

    @Autowired
    private IInscripcionServicio servicio;

    @PostMapping("/inscripcion")
    public ResponseEntity<InscripcionDto> inscribir(@RequestBody InscripcionDto dto) {
        PeticionLogger.log(LOGGER, "POST", "/api/v2/inscripcion", dto);
        Inscripcion inscripcion = Inscripcion.fabricarDeDto(dto);
        Inscripcion resultado = servicio.inscribir(inscripcion);
        return new ResponseEntity<>(InscripcionDto.fabricarDeModelo(resultado), HttpStatus.CREATED);
    }

    @GetMapping("/validarInscripcion")
    public ResponseEntity<Boolean> validarInscripcion(
            @RequestParam String alumnoId,
            @RequestParam String categoria,
            @RequestParam String curso,
            @RequestParam int anio,
            @RequestParam int iterable) {
        PeticionLogger.log(LOGGER, "GET", "/api/v2/validarInscripcion", alumnoId);
        boolean resultado = servicio.validarInscripcion(alumnoId, categoria, curso, anio, iterable);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    @PutMapping("/desvincularInscripcion")
    public ResponseEntity<InscripcionDto> desvincularInscripcion(@RequestBody InscripcionDto dto) {
        PeticionLogger.log(LOGGER, "PUT", "/api/v2/desvincularInscripcion", dto);
        Inscripcion resultado = servicio.desvincularInscripcion(
                dto.getAlumnoId(), dto.getCategoria(), dto.getCurso(), dto.getAnio(), dto.getIterable());
        return new ResponseEntity<>(InscripcionDto.fabricarDeModelo(resultado), HttpStatus.OK);
    }
}
