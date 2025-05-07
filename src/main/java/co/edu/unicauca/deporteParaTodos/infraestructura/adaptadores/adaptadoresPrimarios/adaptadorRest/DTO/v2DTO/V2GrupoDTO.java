package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.v2DTO;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class V2GrupoDTO {
    private String cat_titulo;
    private String cur_nombre;
    private int anio;
    private int iterable;
    private int imagen;
    private String idInstructor;
    private int cupos;
    private String estado;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaCreacion;
    private String fechaFinalizacion;
}
